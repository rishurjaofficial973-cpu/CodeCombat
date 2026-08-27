package com.codeduel.service;

import com.codeduel.dto.*;
import com.codeduel.exception.BadRequestException;
import com.codeduel.exception.ResourceNotFoundException;
import com.codeduel.judge.JudgeService;
import com.codeduel.model.*;
import com.codeduel.repository.*;
import com.codeduel.websocket.WebSocketDispatcher;
import com.codeduel.websocket.WsEvent;
import com.codeduel.websocket.WsEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MatchService {

    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    private final MatchRepository matchRepository;
    private final MatchPlayerRepository matchPlayerRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final JudgeService judgeService;
    private final EloRatingService eloRatingService;
    private final AnalyticsService analyticsService;
    private final AchievementService achievementService;
    private final WebSocketDispatcher wsDispatcher;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    public MatchService(MatchRepository matchRepository,
                        MatchPlayerRepository matchPlayerRepository,
                        UserRepository userRepository,
                        ProblemRepository problemRepository,
                        SubmissionRepository submissionRepository,
                        UserProblemHistoryRepository userProblemHistoryRepository,
                        JudgeService judgeService,
                        EloRatingService eloRatingService,
                        AnalyticsService analyticsService,
                        AchievementService achievementService,
                        WebSocketDispatcher wsDispatcher) {
        this.matchRepository = matchRepository;
        this.matchPlayerRepository = matchPlayerRepository;
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.userProblemHistoryRepository = userProblemHistoryRepository;
        this.judgeService = judgeService;
        this.eloRatingService = eloRatingService;
        this.analyticsService = analyticsService;
        this.achievementService = achievementService;
        this.wsDispatcher = wsDispatcher;
    }

    @Transactional
    public Match createMatch(Long p1Id, Long p2Id, Problem problem, MatchMode mode, int timeLimitSeconds) {
        User u1 = userRepository.findById(p1Id).orElseThrow(() -> new ResourceNotFoundException("User 1 not found"));
        User u2 = userRepository.findById(p2Id).orElseThrow(() -> new ResourceNotFoundException("User 2 not found"));

        String matchId = "match_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        Match match = new Match(matchId, problem, mode, timeLimitSeconds);
        match = matchRepository.save(match);

        MatchPlayer mp1 = new MatchPlayer(match, u1, u1.getRating());
        MatchPlayer mp2 = new MatchPlayer(match, u2, u2.getRating());

        matchPlayerRepository.save(mp1);
        matchPlayerRepository.save(mp2);

        match.getMatchPlayers().add(mp1);
        match.getMatchPlayers().add(mp2);

        return match;
    }

    @Transactional
    public void startCountdown(String matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null) return;

        match.setStatus(MatchStatus.COUNTDOWN);
        match.setCountdownStartTime(LocalDateTime.now());
        matchRepository.save(match);

        wsDispatcher.broadcastMatchEvent(matchId, WsEvent.of(WsEventType.COUNTDOWN, matchId, 3));

        // Schedule start after 3 seconds
        scheduler.schedule(() -> {
            try {
                startActiveMatch(matchId);
            } catch (Exception e) {
                log.error("Error starting match {}", matchId, e);
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Transactional
    public void startActiveMatch(String matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null || match.getStatus() == MatchStatus.COMPLETED) return;

        match.setStatus(MatchStatus.ACTIVE);
        match.setStartTime(LocalDateTime.now());
        match.setEndTime(LocalDateTime.now().plusSeconds(match.getTimeLimitSeconds()));
        matchRepository.save(match);

        log.info("Match {} is now ACTIVE with problem {}", matchId, match.getProblem().getId());
        wsDispatcher.broadcastMatchEvent(matchId, WsEvent.of(WsEventType.MATCH_START, matchId, MatchResponseDto.fromEntity(match, null)));
    }

    @Transactional(readOnly = true)
    public MatchResponseDto getMatchDto(String matchId, Long currentUserId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        return MatchResponseDto.fromEntity(match, currentUserId);
    }

    @Transactional
    public void recordPlayerCoding(String matchId, Long userId, String username) {
        wsDispatcher.broadcastMatchEvent(matchId, WsEvent.of(WsEventType.PLAYER_CODING, matchId, userId, username, "Coding"));
    }

    @Transactional
    public SubmissionResponseDto submitCode(SubmissionRequestDto req, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Problem problem = problemRepository.findById(req.getProblemId())
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found"));

        boolean isPractice = Boolean.TRUE.equals(req.getIsPractice()) || req.getMatchId() == null;
        Match match = null;
        MatchPlayer player = null;
        Integer submissionTimeSeconds = null;

        if (!isPractice) {
            match = matchRepository.findById(req.getMatchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + req.getMatchId()));

            if (match.getStatus() != MatchStatus.ACTIVE) {
                throw new BadRequestException("Match is not in active state (current status: " + match.getStatus() + ")");
            }

            player = matchPlayerRepository.findByMatchIdAndUserId(match.getId(), user.getId())
                    .orElseThrow(() -> new BadRequestException("Player is not part of this match"));

            // Calculate elapsed time
            long elapsed = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - match.getStartTime().toEpochSecond(ZoneOffset.UTC);
            submissionTimeSeconds = (int) Math.max(1, elapsed);

            // Notify opponent that code is running
            player.setStatus(PlayerMatchStatus.RUNNING);
            matchPlayerRepository.save(player);
            wsDispatcher.broadcastMatchEvent(match.getId(), WsEvent.of(WsEventType.PLAYER_RUNNING, match.getId(), user.getId(), user.getUsername(), "Running tests..."));
        }

        // Judge submission
        JudgeService.JudgeVerdict verdict = judgeService.judge(
                problem,
                req.getLanguage(),
                req.getSourceCode(),
                submissionTimeSeconds,
                match != null ? match.getTimeLimitSeconds() : 900
        );

        // Save submission
        Submission submission = new Submission(user, match, problem, req.getLanguage(), req.getSourceCode(), isPractice);
        submission.setStatus(SubmissionStatus.COMPLETED);
        submission.setResult(verdict.getResult());
        submission.setExecutionTimeMs(verdict.getTotalRuntimeMs());
        submission.setMemoryUsageMb(verdict.getMemoryMb());
        submission.setTestsPassed(verdict.getTestsPassed());
        submission.setTotalTests(verdict.getTotalTests());
        submission.setEfficiencyScore(verdict.getEfficiencyScore());
        submission.setCompilerOutput(verdict.getCompilerOutput());
        submission.setErrorDetails(verdict.getErrorDetails());
        submission.setEstimatedTimeComplexity(verdict.getEstimatedTimeComplexity());
        submission.setEstimatedSpaceComplexity(verdict.getEstimatedSpaceComplexity());
        submission = submissionRepository.save(submission);

        // Update UserProblemHistory
        updateProblemHistory(user, problem, match, verdict.getResult() == SubmissionResult.ACCEPTED, verdict.getTotalRuntimeMs(), verdict.getMemoryMb());

        // Update problem submission count stats
        problem.setTotalSubmissions(problem.getTotalSubmissions() + 1);
        if (verdict.getResult() == SubmissionResult.ACCEPTED) {
            problem.setAcceptedSubmissions(problem.getAcceptedSubmissions() + 1);
        }
        problemRepository.save(problem);

        if (!isPractice && player != null) {
            player.setSubmissionTimeSeconds(submissionTimeSeconds);
            player.setExecutionTimeMs(verdict.getTotalRuntimeMs());
            player.setMemoryUsageMb(verdict.getMemoryMb());
            player.setEfficiencyScore(verdict.getEfficiencyScore());
            player.setScore(verdict.getMatchScore());
            player.setTestsPassed(verdict.getTestsPassed());
            player.setTotalTests(verdict.getTotalTests());

            PlayerMatchStatus matchStatus = switch (verdict.getResult()) {
                case ACCEPTED -> PlayerMatchStatus.ACCEPTED;
                case WRONG_ANSWER -> PlayerMatchStatus.WRONG_ANSWER;
                case TIME_LIMIT_EXCEEDED -> PlayerMatchStatus.TIME_LIMIT_EXCEEDED;
                case MEMORY_LIMIT_EXCEEDED -> PlayerMatchStatus.MEMORY_LIMIT_EXCEEDED;
                case COMPILATION_ERROR -> PlayerMatchStatus.COMPILATION_ERROR;
                case RUNTIME_ERROR -> PlayerMatchStatus.RUNTIME_ERROR;
            };
            player.setStatus(matchStatus);
            matchPlayerRepository.save(player);

            // Broadcast limited status to opponent (without exposing code or hidden test data)
            WsEventType eventType = verdict.getResult() == SubmissionResult.ACCEPTED ? WsEventType.PLAYER_ACCEPTED : WsEventType.PLAYER_WRONG;
            Map<String, Object> telemetry = new HashMap<>();
            telemetry.put("userId", user.getId());
            telemetry.put("username", user.getUsername());
            telemetry.put("status", matchStatus.name());
            telemetry.put("testsPassed", verdict.getTestsPassed());
            telemetry.put("totalTests", verdict.getTotalTests());
            wsDispatcher.broadcastMatchEvent(match.getId(), WsEvent.of(eventType, match.getId(), user.getId(), user.getUsername(), telemetry));

            // Check match conclusion condition
            checkAndConcludeMatch(match);
        }

        // Return detailed response to the submitter
        SubmissionResponseDto response = SubmissionResponseDto.fromEntity(submission);
        response.setScore(verdict.getMatchScore());
        response.setOptimizationTip(verdict.getOptimizationTip());
        response.setTestCaseResults(verdict.getTestCaseResults());
        return response;
    }

    private void updateProblemHistory(User user, Problem problem, Match match, boolean isSolved, long runtimeMs, double memoryMb) {
        Optional<UserProblemHistory> existing = userProblemHistoryRepository.findByUserIdAndProblemId(user.getId(), problem.getId());
        if (existing.isPresent()) {
            UserProblemHistory history = existing.get();
            history.setAttemptsCount(history.getAttemptsCount() + 1);
            history.setLastAttemptedAt(LocalDateTime.now());
            if (isSolved) {
                history.setIsSolved(true);
                history.setSolvedAt(LocalDateTime.now());
                if (history.getBestRuntimeMs() == null || runtimeMs < history.getBestRuntimeMs()) {
                    history.setBestRuntimeMs(runtimeMs);
                }
                if (history.getBestMemoryMb() == null || memoryMb < history.getBestMemoryMb()) {
                    history.setBestMemoryMb(memoryMb);
                }
            }
            userProblemHistoryRepository.save(history);
        } else {
            UserProblemHistory history = new UserProblemHistory(user, problem, match, isSolved);
            if (isSolved) {
                history.setBestRuntimeMs(runtimeMs);
                history.setBestMemoryMb(memoryMb);
            }
            userProblemHistoryRepository.save(history);
        }
    }

    @Transactional
    public void checkAndConcludeMatch(Match match) {
        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(match.getId());
        if (players.size() < 2) return;

        MatchPlayer p1 = players.get(0);
        MatchPlayer p2 = players.get(1);

        boolean p1Accepted = p1.getStatus() == PlayerMatchStatus.ACCEPTED;
        boolean p2Accepted = p2.getStatus() == PlayerMatchStatus.ACCEPTED;

        // Sudden Death Mode: First accepted solution wins immediately
        if (match.getMode() == MatchMode.SUDDEN_DEATH) {
            if (p1Accepted) {
                finalizeMatch(match.getId(), p1.getUser().getId(), false);
                return;
            } else if (p2Accepted) {
                finalizeMatch(match.getId(), p2.getUser().getId(), false);
                return;
            }
        }

        // Classic Mode: First accepted solution wins
        if (match.getMode() == MatchMode.CLASSIC) {
            if (p1Accepted && !p2Accepted) {
                finalizeMatch(match.getId(), p1.getUser().getId(), false);
                return;
            } else if (p2Accepted && !p1Accepted) {
                finalizeMatch(match.getId(), p2.getUser().getId(), false);
                return;
            }
        }

        // Score Mode: When both have finished submissions, or both accepted
        boolean p1Done = isTerminalPlayerStatus(p1.getStatus());
        boolean p2Done = isTerminalPlayerStatus(p2.getStatus());

        if (p1Done && p2Done) {
            finalizeMatch(match.getId(), null, false);
        }
    }

    private boolean isTerminalPlayerStatus(PlayerMatchStatus s) {
        return s == PlayerMatchStatus.ACCEPTED || s == PlayerMatchStatus.WRONG_ANSWER ||
                s == PlayerMatchStatus.TIME_LIMIT_EXCEEDED || s == PlayerMatchStatus.MEMORY_LIMIT_EXCEEDED ||
                s == PlayerMatchStatus.FORFEITED;
    }

    @Transactional
    public synchronized MatchResultDto finalizeMatch(String matchId, Long forcedWinnerId, boolean forcedDraw) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null || match.getStatus() == MatchStatus.COMPLETED) {
            return getMatchResult(matchId, null);
        }

        match.setStatus(MatchStatus.COMPLETED);
        match.setEndTime(LocalDateTime.now());

        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(match.getId());
        if (players.size() < 2) {
            matchRepository.save(match);
            return null;
        }

        MatchPlayer mp1 = players.get(0);
        MatchPlayer mp2 = players.get(1);
        User u1 = mp1.getUser();
        User u2 = mp2.getUser();

        Long winnerId = forcedWinnerId;
        boolean isDraw = forcedDraw;

        if (winnerId == null && !isDraw) {
            boolean p1Acc = mp1.getStatus() == PlayerMatchStatus.ACCEPTED;
            boolean p2Acc = mp2.getStatus() == PlayerMatchStatus.ACCEPTED;

            if (p1Acc && !p2Acc) {
                winnerId = u1.getId();
            } else if (p2Acc && !p1Acc) {
                winnerId = u2.getId();
            } else if (p1Acc && p2Acc) {
                // Both accepted -> compare efficiency & speed
                if (match.getMode() == MatchMode.SCORE) {
                    if (mp1.getScore() > mp2.getScore()) {
                        winnerId = u1.getId();
                    } else if (mp2.getScore() > mp1.getScore()) {
                        winnerId = u2.getId();
                    } else {
                        // Efficiency tie-breaker
                        if (mp1.getEfficiencyScore() > mp2.getEfficiencyScore()) {
                            winnerId = u1.getId();
                        } else if (mp2.getEfficiencyScore() > mp1.getEfficiencyScore()) {
                            winnerId = u2.getId();
                        } else {
                            // Submission speed tie-breaker
                            int t1 = mp1.getSubmissionTimeSeconds() != null ? mp1.getSubmissionTimeSeconds() : 9999;
                            int t2 = mp2.getSubmissionTimeSeconds() != null ? mp2.getSubmissionTimeSeconds() : 9999;
                            if (t1 < t2) winnerId = u1.getId();
                            else if (t2 < t1) winnerId = u2.getId();
                            else isDraw = true;
                        }
                    }
                } else {
                    // Classic / Sudden Death: earliest submission
                    int t1 = mp1.getSubmissionTimeSeconds() != null ? mp1.getSubmissionTimeSeconds() : 9999;
                    int t2 = mp2.getSubmissionTimeSeconds() != null ? mp2.getSubmissionTimeSeconds() : 9999;
                    if (t1 < t2) winnerId = u1.getId();
                    else if (t2 < t1) winnerId = u2.getId();
                    else isDraw = true;
                }
            } else {
                // Neither accepted -> compare tests passed or draw
                if (mp1.getTestsPassed() > mp2.getTestsPassed()) {
                    winnerId = u1.getId();
                } else if (mp2.getTestsPassed() > mp1.getTestsPassed()) {
                    winnerId = u2.getId();
                } else {
                    isDraw = true;
                }
            }
        }

        match.setWinnerId(winnerId);
        match.setIsDraw(isDraw);
        matchRepository.save(match);

        // Apply Elo ratings
        EloRatingService.RatingDelta delta = eloRatingService.applyMatchRating(u1, u2, match, winnerId, isDraw);

        mp1.setRatingAfter(delta.player1NewRating);
        mp1.setRatingChange(delta.player1Change);

        mp2.setRatingAfter(delta.player2NewRating);
        mp2.setRatingChange(delta.player2Change);

        matchPlayerRepository.save(mp1);
        matchPlayerRepository.save(mp2);

        // Post match achievements
        boolean p1Won = winnerId != null && winnerId.equals(u1.getId());
        boolean p2Won = winnerId != null && winnerId.equals(u2.getId());
        achievementService.evaluatePostMatchAchievements(u1, mp1, mp2, p1Won);
        achievementService.evaluatePostMatchAchievements(u2, mp2, mp1, p2Won);

        // Broadcast MATCH_FINISHED
        MatchResultDto resultDto = buildMatchResultDto(match, mp1, mp2, winnerId, isDraw, u1.getId());
        wsDispatcher.broadcastMatchEvent(match.getId(), WsEvent.of(WsEventType.MATCH_FINISHED, match.getId(), resultDto));

        log.info("Match {} completed. Winner: {}, IsDraw: {}", match.getId(), winnerId, isDraw);
        return resultDto;
    }

    @Transactional(readOnly = true)
    public MatchResultDto getMatchResult(String matchId, Long currentUserId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        List<MatchPlayer> players = matchPlayerRepository.findByMatchId(match.getId());
        MatchPlayer mp1 = players.size() > 0 ? players.get(0) : null;
        MatchPlayer mp2 = players.size() > 1 ? players.get(1) : null;
        return buildMatchResultDto(match, mp1, mp2, match.getWinnerId(), Boolean.TRUE.equals(match.getIsDraw()), currentUserId);
    }

    private MatchResultDto buildMatchResultDto(Match match, MatchPlayer mp1, MatchPlayer mp2, Long winnerId, boolean isDraw, Long currentUserId) {
        MatchResultDto dto = new MatchResultDto();
        dto.setMatchId(match.getId());
        if (match.getProblem() != null) {
            dto.setProblemId(match.getProblem().getId());
            dto.setProblemTitle(match.getProblem().getTitle());
        }
        dto.setWinnerId(winnerId);
        dto.setIsDraw(isDraw);

        if (winnerId != null) {
            if (mp1 != null && mp1.getUser().getId().equals(winnerId)) {
                dto.setWinnerUsername(mp1.getUser().getUsername());
            } else if (mp2 != null && mp2.getUser().getId().equals(winnerId)) {
                dto.setWinnerUsername(mp2.getUser().getUsername());
            }
        }

        if (mp1 != null) dto.getPlayers().add(MatchPlayerDto.fromEntity(mp1));
        if (mp2 != null) dto.getPlayers().add(MatchPlayerDto.fromEntity(mp2));

        if (currentUserId != null) {
            dto.setAnalysis(analyticsService.generatePostMatchAnalysis(match, currentUserId));
        }

        return dto;
    }

    @Transactional
    public void handleDisconnection(String matchId, Long userId) {
        MatchPlayer player = matchPlayerRepository.findByMatchIdAndUserId(matchId, userId).orElse(null);
        if (player != null && player.getStatus() != PlayerMatchStatus.ACCEPTED) {
            player.setStatus(PlayerMatchStatus.DISCONNECTED);
            player.setDisconnectedAt(LocalDateTime.now());
            matchPlayerRepository.save(player);

            wsDispatcher.broadcastMatchEvent(matchId, WsEvent.of(WsEventType.PLAYER_DISCONNECTED, matchId, userId, player.getUser().getUsername(), "Player disconnected"));
        }
    }

    @Transactional
    public void handleReconnection(String matchId, Long userId) {
        MatchPlayer player = matchPlayerRepository.findByMatchIdAndUserId(matchId, userId).orElse(null);
        if (player != null && player.getStatus() == PlayerMatchStatus.DISCONNECTED) {
            player.setStatus(PlayerMatchStatus.CODING);
            player.setDisconnectedAt(null);
            matchPlayerRepository.save(player);

            wsDispatcher.broadcastMatchEvent(matchId, WsEvent.of(WsEventType.PLAYER_RECONNECTED, matchId, userId, player.getUser().getUsername(), "Player reconnected"));
        }
    }

    @Transactional(readOnly = true)
    public Page<MatchResponseDto> getMatchHistory(Long userId, Pageable pageable) {
        return matchRepository.findMatchesByUserId(userId, pageable)
                .map(m -> MatchResponseDto.fromEntity(m, userId));
    }

    @Scheduled(fixedRate = 5000)
    public void checkExpiredMatches() {
        LocalDateTime now = LocalDateTime.now();
        List<Match> expired = matchRepository.findExpiredActiveMatches(now);
        for (Match match : expired) {
            log.info("Match {} reached time limit. Finalizing...", match.getId());
            try {
                finalizeMatch(match.getId(), null, false);
            } catch (Exception e) {
                log.error("Error finalizing expired match {}", match.getId(), e);
            }
        }
    }
}
