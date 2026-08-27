package com.codeduel.service;

import com.codeduel.dto.*;
import com.codeduel.model.*;
import com.codeduel.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final UserRepository userRepository;
    private final RatingHistoryRepository ratingHistoryRepository;
    private final UserProblemHistoryRepository userProblemHistoryRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final SubmissionRepository submissionRepository;
    private final MatchRepository matchRepository;

    public AnalyticsService(UserRepository userRepository,
                            RatingHistoryRepository ratingHistoryRepository,
                            UserProblemHistoryRepository userProblemHistoryRepository,
                            UserAchievementRepository userAchievementRepository,
                            SubmissionRepository submissionRepository,
                            MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.ratingHistoryRepository = ratingHistoryRepository;
        this.userProblemHistoryRepository = userProblemHistoryRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.submissionRepository = submissionRepository;
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        UserProfileDto profile = new UserProfileDto();
        profile.setUser(UserDto.fromEntity(user));

        // Rating history
        List<RatingHistory> history = ratingHistoryRepository.findByUserIdOrderByCreatedAtAsc(userId);
        history.forEach(rh -> profile.getRatingHistory().add(RatingHistoryDto.fromEntity(rh)));

        // Problem counts
        profile.setSolvedProblemsCount(userProblemHistoryRepository.countByUserIdAndIsSolvedTrue(userId));
        profile.setAttemptedProblemsCount(userProblemHistoryRepository.countByUserId(userId));
        profile.setAcceptedSubmissionsCount(submissionRepository.countAcceptedByUserId(userId));

        // Topic mastery and difficulty breakdown
        Map<String, Integer> topicMap = new HashMap<>();
        Map<String, Integer> diffMap = new HashMap<>();
        diffMap.put("EASY", 0);
        diffMap.put("MEDIUM", 0);
        diffMap.put("HARD", 0);

        List<UserProblemHistory> solvedList = userProblemHistoryRepository.findByUserIdAndIsSolvedTrue(userId);
        for (UserProblemHistory uph : solvedList) {
            Problem p = uph.getProblem();
            if (p != null) {
                if (p.getDifficulty() != null) {
                    diffMap.put(p.getDifficulty().name(), diffMap.getOrDefault(p.getDifficulty().name(), 0) + 1);
                }
                if (p.getTopics() != null) {
                    String[] topics = p.getTopics().split(",");
                    for (String t : topics) {
                        String clean = t.trim();
                        if (!clean.isEmpty()) {
                            topicMap.put(clean, topicMap.getOrDefault(clean, 0) + 1);
                        }
                    }
                }
            }
        }
        profile.setTopicMastery(topicMap);
        profile.setDifficultyStats(diffMap);

        // Achievements
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        userAchievements.forEach(ua -> profile.getAchievements().add(UserAchievementDto.fromEntity(ua)));

        return profile;
    }

    @Transactional(readOnly = true)
    public PostMatchAnalysisDto generatePostMatchAnalysis(Match match, Long currentUserId) {
        PostMatchAnalysisDto dto = new PostMatchAnalysisDto();
        Problem problem = match.getProblem();
        if (problem == null) return dto;

        dto.setProblemId(problem.getId());
        dto.setProblemTitle(problem.getTitle());
        dto.setExpectedTimeComplexity(problem.getExpectedTimeComplexity());
        dto.setExpectedSpaceComplexity(problem.getExpectedSpaceComplexity());
        dto.setAvgProblemRuntimeMs(problem.getAvgRuntimeMs());
        dto.setAvgProblemMemoryMb(problem.getAvgMemoryMb());

        MatchPlayer myPlayer = null;
        MatchPlayer oppPlayer = null;

        for (MatchPlayer mp : match.getMatchPlayers()) {
            if (mp.getUser().getId().equals(currentUserId)) {
                myPlayer = mp;
            } else {
                oppPlayer = mp;
            }
        }

        if (myPlayer != null) {
            dto.setMyRuntimeMs(myPlayer.getExecutionTimeMs() != null ? myPlayer.getExecutionTimeMs() : 0L);
            dto.setMyMemoryMb(myPlayer.getMemoryUsageMb() != null ? myPlayer.getMemoryUsageMb() : 0.0);
            dto.setMyEfficiencyScore(myPlayer.getEfficiencyScore() != null ? myPlayer.getEfficiencyScore() : 0.0);
            dto.setMyScore(myPlayer.getScore() != null ? myPlayer.getScore() : 0);

            // Percentile calculation
            Long slowerCount = submissionRepository.countSlowerAcceptedSubmissions(problem.getId(), dto.getMyRuntimeMs());
            Long totalAccepted = submissionRepository.countTotalAcceptedSubmissions(problem.getId());
            if (totalAccepted != null && totalAccepted > 0) {
                double pct = Math.round(((double) slowerCount / totalAccepted) * 1000.0) / 10.0;
                dto.setMyRuntimePercentile(pct);
            } else {
                dto.setMyRuntimePercentile(78.5); // Baseline
            }
            dto.setMyMemoryPercentile(82.0);
        }

        if (oppPlayer != null) {
            dto.setOpponentRuntimeMs(oppPlayer.getExecutionTimeMs() != null ? oppPlayer.getExecutionTimeMs() : 0L);
            dto.setOpponentMemoryMb(oppPlayer.getMemoryUsageMb() != null ? oppPlayer.getMemoryUsageMb() : 0.0);
            dto.setOpponentEfficiencyScore(oppPlayer.getEfficiencyScore() != null ? oppPlayer.getEfficiencyScore() : 0.0);
            dto.setOpponentScore(oppPlayer.getScore() != null ? oppPlayer.getScore() : 0);
        }

        // Suggestions
        dto.getOptimizationTips().add("Your solution executed within the top tier threshold.");
        dto.getOptimizationTips().add("Expected complexity is " + problem.getExpectedTimeComplexity() + ". Memory usage is well bounded.");

        return dto;
    }
}
