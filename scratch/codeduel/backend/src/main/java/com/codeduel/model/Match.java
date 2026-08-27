package com.codeduel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches", indexes = {
    @Index(name = "idx_match_status", columnList = "status"),
    @Index(name = "idx_match_created", columnList = "createdAt")
})
public class Match {

    @Id
    @Column(length = 64, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MatchStatus status = MatchStatus.WAITING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MatchMode mode = MatchMode.SCORE;

    private LocalDateTime countdownStartTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Integer timeLimitSeconds = 900; // 15 minutes

    private Long winnerId;

    @Column(nullable = false)
    private Boolean isDraw = false;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MatchPlayer> matchPlayers = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Match() {}

    public Match(String id, Problem problem, MatchMode mode, Integer timeLimitSeconds) {
        this.id = id;
        this.problem = problem;
        this.mode = mode != null ? mode : MatchMode.SCORE;
        this.timeLimitSeconds = timeLimitSeconds != null ? timeLimitSeconds : 900;
        this.status = MatchStatus.MATCHED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }

    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }

    public MatchMode getMode() { return mode; }
    public void setMode(MatchMode mode) { this.mode = mode; }

    public LocalDateTime getCountdownStartTime() { return countdownStartTime; }
    public void setCountdownStartTime(LocalDateTime countdownStartTime) { this.countdownStartTime = countdownStartTime; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Integer getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }

    public Long getWinnerId() { return winnerId; }
    public void setWinnerId(Long winnerId) { this.winnerId = winnerId; }

    public Boolean getIsDraw() { return isDraw; }
    public void setIsDraw(Boolean isDraw) { this.isDraw = isDraw; }

    public List<MatchPlayer> getMatchPlayers() { return matchPlayers; }
    public void setMatchPlayers(List<MatchPlayer> matchPlayers) { this.matchPlayers = matchPlayers; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
