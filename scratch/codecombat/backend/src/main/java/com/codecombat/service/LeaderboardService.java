package com.codecombat.service;

import com.codecombat.dto.LeaderboardEntryDto;
import com.codecombat.model.User;
import com.codecombat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderboardService {

    private static final Logger log = LoggerFactory.getLogger(LeaderboardService.class);
    private static final String LEADERBOARD_KEY = "leaderboard:global";

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public LeaderboardService(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public void updateUserRating(Long userId, int rating) {
        try {
            if (redisTemplate != null) {
                redisTemplate.opsForZSet().add(LEADERBOARD_KEY, userId.toString(), rating);
            }
        } catch (Exception ex) {
            log.warn("Could not update rating in Redis (falling back to MySQL): {}", ex.getMessage());
        }
    }

    public Integer getUserRank(Long userId) {
        if (userId == null) return null;
        try {
            if (redisTemplate != null) {
                Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, userId.toString());
                if (rank != null) {
                    return (int) (rank + 1);
                }
            }
        } catch (Exception ex) {
            log.debug("Redis rank lookup fallback to MySQL: {}", ex.getMessage());
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return 1;
        return userRepository.countRankByRating(user.getRating());
    }

    public List<LeaderboardEntryDto> getTopLeaderboard(int limit) {
        List<User> topUsers = userRepository.findAllByOrderByRatingDesc(PageRequest.of(0, Math.min(100, limit))).getContent();
        List<LeaderboardEntryDto> entries = new ArrayList<>();

        int rank = 1;
        for (User u : topUsers) {
            entries.add(new LeaderboardEntryDto(
                    rank++,
                    u.getId(),
                    u.getUsername(),
                    u.getRating(),
                    u.getWins(),
                    u.getLosses(),
                    u.getDraws(),
                    u.getMatchesPlayed(),
                    u.getWinStreak()
            ));
        }

        return entries;
    }
}
