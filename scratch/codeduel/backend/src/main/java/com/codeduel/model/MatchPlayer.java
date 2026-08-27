package com.codeduel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_players", indexes = {
    @Index(name = "idx_mp_match_user", columnList = "match_id, user_id")
})
public class MatchPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "match_id", nullable = false)
    @JsonIgnore
    private Match match;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PlayerMatchStatus status = PlayerMatchStatus.CODING;

    @Column(nullable = false)
    private Integer score = 0;

    private Double efficiencyScore = 0.0;
    private Long executionTimeMs;
    private Double memoryUsageMb;
    private Integer submissionTimeSeconds;

    private Integer testsPassed = 0;
    private Integer totalTests = 0;

    private Integer ratingBefore;
    private Integer ratingAfter;
    private Integer ratingChange = 0;

    private LocalDateTime disconnectedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public MatchPlayer() {}

    public MatchPlayer(Match match, User user, Integer ratingBefore) {
        this.match = match;
        this.user = user;
        this.ratingBefore = ratingBefore;
        this.status = PlayerMatchStatus.CODING;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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

    public Integer getRatingBefore() { return ratingBefore; }
    public void setRatingBefore(Integer ratingBefore) { this.ratingBefore = ratingBefore; }

    public Integer getRatingAfter() { return ratingAfter; }
    public void setRatingAfter(Integer ratingAfter) { this.ratingAfter = ratingAfter; }

    public Integer getRatingChange() { return ratingChange; }
    public void setRatingChange(Integer ratingChange) { this.ratingChange = ratingChange; }

    public LocalDateTime getDisconnectedAt() { return disconnectedAt; }
    public void setDisconnectedAt(LocalDateTime disconnectedAt) { this.disconnectedAt = disconnectedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
