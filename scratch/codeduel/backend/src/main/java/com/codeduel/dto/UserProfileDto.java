package com.codeduel.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileDto {
    private UserDto user;
    private List<RatingHistoryDto> ratingHistory = new ArrayList<>();
    private Long solvedProblemsCount = 0L;
    private Long attemptedProblemsCount = 0L;
    private Long acceptedSubmissionsCount = 0L;
    private Map<String, Integer> topicMastery = new HashMap<>(); // topic -> count solved
    private Map<String, Integer> difficultyStats = new HashMap<>(); // EASY/MEDIUM/HARD -> count solved
    private List<UserAchievementDto> achievements = new ArrayList<>();

    public UserProfileDto() {}

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public List<RatingHistoryDto> getRatingHistory() { return ratingHistory; }
    public void setRatingHistory(List<RatingHistoryDto> ratingHistory) { this.ratingHistory = ratingHistory; }

    public Long getSolvedProblemsCount() { return solvedProblemsCount; }
    public void setSolvedProblemsCount(Long solvedProblemsCount) { this.solvedProblemsCount = solvedProblemsCount; }

    public Long getAttemptedProblemsCount() { return attemptedProblemsCount; }
    public void setAttemptedProblemsCount(Long attemptedProblemsCount) { this.attemptedProblemsCount = attemptedProblemsCount; }

    public Long getAcceptedSubmissionsCount() { return acceptedSubmissionsCount; }
    public void setAcceptedSubmissionsCount(Long acceptedSubmissionsCount) { this.acceptedSubmissionsCount = acceptedSubmissionsCount; }

    public Map<String, Integer> getTopicMastery() { return topicMastery; }
    public void setTopicMastery(Map<String, Integer> topicMastery) { this.topicMastery = topicMastery; }

    public Map<String, Integer> getDifficultyStats() { return difficultyStats; }
    public void setDifficultyStats(Map<String, Integer> difficultyStats) { this.difficultyStats = difficultyStats; }

    public List<UserAchievementDto> getAchievements() { return achievements; }
    public void setAchievements(List<UserAchievementDto> achievements) { this.achievements = achievements; }
}
