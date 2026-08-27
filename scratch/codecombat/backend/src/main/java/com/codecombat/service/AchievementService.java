package com.codecombat.service;

import com.codecombat.model.*;
import com.codecombat.repository.AchievementRepository;
import com.codecombat.repository.UserAchievementRepository;
import com.codecombat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {

    private static final Logger log = LoggerFactory.getLogger(AchievementService.class);

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public AchievementService(AchievementRepository achievementRepository,
                              UserAchievementRepository userAchievementRepository,
                              UserRepository userRepository,
                              NotificationService notificationService) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void evaluatePostMatchAchievements(User user, MatchPlayer player, MatchPlayer opponent, boolean isWinner) {
        if (user == null || player == null) return;

        // 1. First Win
        if (isWinner && user.getWins() == 1) {
            unlockAchievement(user, "FIRST_WIN");
        }

        // 2. Wins milestones
        if (user.getWins() >= 10) unlockAchievement(user, "WINS_10");
        if (user.getWins() >= 50) unlockAchievement(user, "WINS_50");
        if (user.getWins() >= 100) unlockAchievement(user, "WINS_100");

        // 3. Streaks
        if (user.getWinStreak() >= 5) unlockAchievement(user, "STREAK_5");
        if (user.getWinStreak() >= 10) unlockAchievement(user, "STREAK_10");

        // 4. Beat higher rated player (100+ rating gap)
        if (isWinner && opponent != null && opponent.getRatingBefore() != null && player.getRatingBefore() != null) {
            if (opponent.getRatingBefore() - player.getRatingBefore() >= 100) {
                unlockAchievement(user, "BEAT_HIGHER_RATED");
            }
        }

        // 5. Perfect Efficiency (95+)
        if (player.getEfficiencyScore() != null && player.getEfficiencyScore() >= 95.0) {
            unlockAchievement(user, "PERFECT_EFFICIENCY");
        }

        // 6. Speed Demon (< 120s solve time)
        if (isWinner && player.getSubmissionTimeSeconds() != null && player.getSubmissionTimeSeconds() <= 120) {
            unlockAchievement(user, "SPEED_DEMON");
        }
    }

    private void unlockAchievement(User user, String achievementId) {
        if (userAchievementRepository.existsByUserIdAndAchievementId(user.getId(), achievementId)) {
            return;
        }

        Achievement achievement = achievementRepository.findById(achievementId).orElse(null);
        if (achievement != null) {
            UserAchievement ua = new UserAchievement(user, achievement);
            userAchievementRepository.save(ua);

            notificationService.createNotification(
                    user.getId(),
                    "🏆 Achievement Unlocked: " + achievement.getTitle(),
                    achievement.getDescription(),
                    NotificationType.ACHIEVEMENT_UNLOCKED,
                    "/profile"
            );
            log.info("Unlocked achievement {} for user {}", achievementId, user.getUsername());
        }
    }
}
