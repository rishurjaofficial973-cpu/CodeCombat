package com.codecombat.dto;

import com.codecombat.model.MatchPlayer;
import com.codecombat.model.PlayerMatchStatus;

public class MatchPlayerDto {
    private Long userId;
    private String username;
    private Integer ratingBefore;
    private Integer ratingAfter;
    private Integer ratingChange;
    private PlayerMatchStatus status;
    private Integer score;
    private Double efficiencyScore;
    private Long executionTimeMs;
    private Double memoryUsageMb;
    private Integer submissionTimeSeconds;
    private Integer testsPassed;
    private Integer totalTests;

    public MatchPlayerDto() {}

    public static MatchPlayerDto fromEntity(MatchPlayer mp) {
        if (mp == null) return null;
        MatchPlayerDto dto = new MatchPlayerDto();
        dto.setUserId(mp.getUser().getId());
        dto.setUsername(mp.getUser().getUsername());
        dto.setRatingBefore(mp.getRatingBefore());
        dto.setRatingAfter(mp.getRatingAfter());
        dto.setRatingChange(mp.getRatingChange());
        dto.setStatus(mp.getStatus());
        dto.setScore(mp.getScore());
        dto.setEfficiencyScore(mp.getEfficiencyScore());
        dto.setExecutionTimeMs(mp.getExecutionTimeMs());
        dto.setMemoryUsageMb(mp.getMemoryUsageMb());
        dto.setSubmissionTimeSeconds(mp.getSubmissionTimeSeconds());
        dto.setTestsPassed(mp.getTestsPassed());
        dto.setTotalTests(mp.getTotalTests());
        return dto;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getRatingBefore() { return ratingBefore; }
    public void setRatingBefore(Integer ratingBefore) { this.ratingBefore = ratingBefore; }

    public Integer getRatingAfter() { return ratingAfter; }
    public void setRatingAfter(Integer ratingAfter) { this.ratingAfter = ratingAfter; }

    public Integer getRatingChange() { return ratingChange; }
    public void setRatingChange(Integer ratingChange) { this.ratingChange = ratingChange; }

    public PlayerMatchStatus getStatus() { return status; }
    public void setStatus(PlayerMatchStatus status) { this.status = status; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Double getEfficiencyScore() { return efficiencyScore; }
    public void setEfficiencyScore(Double efficiencyScore) { this.efficiencyScore = efficiencyScore; }

    public Long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(Long executionTimeMs) { this.executionTimeMs = executionTimeMs; }

    public Double getMemoryUsageMb() { return memoryUsageMb; }
    public void setMemoryUsageMb(Double memoryUsageMb) { this.memoryUsageMb = memoryUsageMb; }

    public Integer getSubmissionTimeSeconds() { return submissionTimeSeconds; }
    public void setSubmissionTimeSeconds(Integer submissionTimeSeconds) { this.submissionTimeSeconds = submissionTimeSeconds; }

    public Integer getTestsPassed() { return testsPassed; }
    public void setTestsPassed(Integer testsPassed) { this.testsPassed = testsPassed; }

    public Integer getTotalTests() { return totalTests; }
    public void setTotalTests(Integer totalTests) { this.totalTests = totalTests; }
}
