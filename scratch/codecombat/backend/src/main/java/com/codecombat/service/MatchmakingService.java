package com.codecombat.service;

import com.codecombat.dto.MatchResponseDto;
import com.codecombat.dto.MatchmakingRequest;
import com.codecombat.exception.BadRequestException;
import com.codecombat.model.*;
import com.codecombat.repository.UserRepository;
import com.codecombat.websocket.WebSocketDispatcher;
import com.codecombat.websocket.WsEvent;
import com.codecombat.websocket.WsEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchmakingService {

    private static final Logger log = LoggerFactory.getLogger(MatchmakingService.class);

    private static final String MM_QUEUE_KEY = "matchmaking:queue";

    private final UserRepository userRepository;
    private final ProblemService problemService;
    private final MatchService matchService;
    private final WebSocketDispatcher wsDispatcher;
    private final StringRedisTemplate redisTemplate;

    // In-memory queue fallback for development resilience
    private final Map<Long, QueueTicket> memoryQueue = new ConcurrentHashMap<>();

    @Value("${codecombat.matchmaking.rating-window-initial:50}")
    private int initialWindow;

    @Value("${codecombat.matchmaking.rating-window-step-10s:100}")
    private int step10sWindow;

    @Value("${codecombat.matchmaking.rating-window-step-20s:200}")
    private int step20sWindow;

    @Value("${codecombat.matchmaking.rating-window-step-30s:500}")
    private int step30sWindow;

    @Value("${codecombat.matchmaking.queue-timeout-seconds:120}")
    private int queueTimeoutSeconds;

    public static class QueueTicket {
        public Long userId;
        public String username;
        public int rating;
        public long enqueueTimeEpoch;
        public Difficulty preferredDifficulty;
        public String preferredTopic;
        public MatchMode mode;

        public QueueTicket() {}

        public QueueTicket(Long userId, String username, int rating, Difficulty preferredDifficulty, String preferredTopic, MatchMode mode) {
            this.userId = userId;
            this.username = username;
            this.rating = rating;
            this.enqueueTimeEpoch = Instant.now().getEpochSecond();
            this.preferredDifficulty = preferredDifficulty;
            this.preferredTopic = preferredTopic;
            this.mode = mode != null ? mode : MatchMode.SCORE;
        }

        public int getCurrentAllowedDelta() {
            long elapsedSeconds = Instant.now().getEpochSecond() - enqueueTimeEpoch;
            if (elapsedSeconds < 10) return 50;
            if (elapsedSeconds < 20) return 100;
            if (elapsedSeconds < 30) return 200;
            return 500;
        }
    }

    public MatchmakingService(UserRepository userRepository,
                              ProblemService problemService,
                              MatchService matchService,
                              WebSocketDispatcher wsDispatcher,
                              StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.problemService = problemService;
        this.matchService = matchService;
        this.wsDispatcher = wsDispatcher;
        this.redisTemplate = redisTemplate;
    }

    public synchronized void enqueuePlayer(Long userId, MatchmakingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Check if user is already searching
        if (memoryQueue.containsKey(userId)) {
            log.info("User {} is already in matchmaking queue.", user.getUsername());
            return;
        }

        QueueTicket ticket = new QueueTicket(
                user.getId(),
                user.getUsername(),
                user.getRating(),
                request != null ? request.getPreferredDifficulty() : null,
                request != null ? request.getPreferredTopic() : null,
                request != null ? request.getMode() : MatchMode.SCORE
        );

        memoryQueue.put(userId, ticket);

        try {
            if (redisTemplate != null) {
                redisTemplate.opsForZSet().add(MM_QUEUE_KEY, userId.toString(), ticket.rating);
            }
        } catch (Exception ex) {
            log.debug("Redis queue add fallback: {}", ex.getMessage());
        }

        log.info("Player {} (Rating {}) joined matchmaking queue. Queue size: {}", user.getUsername(), user.getRating(), memoryQueue.size());

        // Attempt immediate matching
        processQueue();
    }

    public synchronized void cancelSearch(Long userId) {
        memoryQueue.remove(userId);
        try {
            if (redisTemplate != null) {
                redisTemplate.opsForZSet().remove(MM_QUEUE_KEY, userId.toString());
            }
        } catch (Exception ignored) {}
        log.info("Player {} cancelled matchmaking search.", userId);
    }

    public boolean isUserInQueue(Long userId) {
        return memoryQueue.containsKey(userId);
    }

    @Scheduled(fixedDelayString = "${codecombat.matchmaking.poll-interval-ms:1000}")
    public synchronized void processQueue() {
        if (memoryQueue.size() < 2) return;

        long nowEpoch = Instant.now().getEpochSecond();
        // Remove stale tickets
        memoryQueue.entrySet().removeIf(entry -> (nowEpoch - entry.getValue().enqueueTimeEpoch) > queueTimeoutSeconds);

        List<QueueTicket> tickets = new ArrayList<>(memoryQueue.values());
        // Sort by enqueue time (FIFO fairness)
        tickets.sort(Comparator.comparingLong(t -> t.enqueueTimeEpoch));

        Set<Long> matchedInThisPass = new HashSet<>();

        for (int i = 0; i < tickets.size(); i++) {
            QueueTicket t1 = tickets.get(i);
            if (matchedInThisPass.contains(t1.userId)) continue;

            QueueTicket bestMatch = null;
            int smallestDelta = Integer.MAX_VALUE;

            for (int j = i + 1; j < tickets.size(); j++) {
                QueueTicket t2 = tickets.get(j);
                if (matchedInThisPass.contains(t2.userId)) continue;
                if (t1.userId.equals(t2.userId)) continue;

                int ratingDiff = Math.abs(t1.rating - t2.rating);
                int maxAllowed = Math.max(t1.getCurrentAllowedDelta(), t2.getCurrentAllowedDelta());

                if (ratingDiff <= maxAllowed && ratingDiff < smallestDelta) {
                    smallestDelta = ratingDiff;
                    bestMatch = t2;
                }
            }

            if (bestMatch != null) {
                matchedInThisPass.add(t1.userId);
                matchedInThisPass.add(bestMatch.userId);

                // Remove from queue
                memoryQueue.remove(t1.userId);
                memoryQueue.remove(bestMatch.userId);

                createAndStartMatch(t1, bestMatch);
            }
        }
    }

    private void createAndStartMatch(QueueTicket t1, QueueTicket t2) {
        log.info("Matched {} (Rating {}) with {} (Rating {})", t1.username, t1.rating, t2.username, t2.rating);

        int avgRating = (t1.rating + t2.rating) / 2;
        Difficulty preferredDiff = t1.preferredDifficulty != null ? t1.preferredDifficulty : t2.preferredDifficulty;
        String preferredTopic = t1.preferredTopic != null ? t1.preferredTopic : t2.preferredTopic;
        MatchMode mode = t1.mode != null ? t1.mode : MatchMode.SCORE;

        Problem problem = problemService.selectProblemForMatch(t1.userId, t2.userId, preferredDiff, preferredTopic, avgRating);

        Match match = matchService.createMatch(t1.userId, t2.userId, problem, mode, 900);

        MatchResponseDto matchDto = MatchResponseDto.fromEntity(match, null);

        // Broadcast to both players
        wsDispatcher.sendToUser(t1.username, "matchmaking", WsEvent.of(WsEventType.MATCH_FOUND, match.getId(), matchDto));
        wsDispatcher.sendToUser(t2.username, "matchmaking", WsEvent.of(WsEventType.MATCH_FOUND, match.getId(), matchDto));

        // Start countdown
        matchService.startCountdown(match.getId());
    }
}
