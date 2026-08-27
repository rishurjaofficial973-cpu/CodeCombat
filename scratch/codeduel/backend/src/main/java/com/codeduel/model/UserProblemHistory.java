package com.codeduel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_problem_history", indexes = {
    @Index(name = "idx_uph_user_prob", columnList = "user_id, problem_id", unique = true),
    @Index(name = "idx_uph_user_solved", columnList = "user_id, isSolved")
})
public class UserProblemHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @Column(nullable = false)
    private Boolean isSolved = false;

    private Long bestRuntimeMs;
    private Double bestMemoryMb;

    @Column(nullable = false)
    private Integer attemptsCount = 1;

    @Column(nullable = false)
    private LocalDateTime lastAttemptedAt = LocalDateTime.now();

    private LocalDateTime solvedAt;

    public UserProblemHistory() {}

    public UserProblemHistory(User user, Problem problem, Match match, Boolean isSolved) {
        this.user = user;
        this.problem = problem;
        this.match = match;
        this.isSolved = isSolved != null ? isSolved : false;
        this.attemptsCount = 1;
        this.lastAttemptedAt = LocalDateTime.now();
        if (Boolean.TRUE.equals(this.isSolved)) {
            this.solvedAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public Boolean getIsSolved() { return isSolved; }
    public void setIsSolved(Boolean isSolved) { this.isSolved = isSolved; }

    public Long getBestRuntimeMs() { return bestRuntimeMs; }
    public void setBestRuntimeMs(Long bestRuntimeMs) { this.bestRuntimeMs = bestRuntimeMs; }

    public Double getBestMemoryMb() { return bestMemoryMb; }
    public void setBestMemoryMb(Double bestMemoryMb) { this.bestMemoryMb = bestMemoryMb; }

    public Integer getAttemptsCount() { return attemptsCount; }
    public void setAttemptsCount(Integer attemptsCount) { this.attemptsCount = attemptsCount; }

    public LocalDateTime getLastAttemptedAt() { return lastAttemptedAt; }
    public void setLastAttemptedAt(LocalDateTime lastAttemptedAt) { this.lastAttemptedAt = lastAttemptedAt; }

    public LocalDateTime getSolvedAt() { return solvedAt; }
    public void setSolvedAt(LocalDateTime solvedAt) { this.solvedAt = solvedAt; }
}
