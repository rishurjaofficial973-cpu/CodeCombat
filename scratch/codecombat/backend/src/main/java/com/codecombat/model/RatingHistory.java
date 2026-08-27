package com.codecombat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rating_history", indexes = {
    @Index(name = "idx_rh_user_created", columnList = "user_id, createdAt")
})
public class RatingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @Column(nullable = false)
    private Integer oldRating;

    @Column(nullable = false)
    private Integer newRating;

    @Column(nullable = false)
    private Integer ratingChange;

    private Integer opponentRating;
    private String opponentUsername;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public RatingHistory() {}

    public RatingHistory(User user, Match match, Integer oldRating, Integer newRating, Integer ratingChange, Integer opponentRating, String opponentUsername) {
        this.user = user;
        this.match = match;
        this.oldRating = oldRating;
        this.newRating = newRating;
        this.ratingChange = ratingChange;
        this.opponentRating = opponentRating;
        this.opponentUsername = opponentUsername;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public Integer getOldRating() { return oldRating; }
    public void setOldRating(Integer oldRating) { this.oldRating = oldRating; }

    public Integer getNewRating() { return newRating; }
    public void setNewRating(Integer newRating) { this.newRating = newRating; }

    public Integer getRatingChange() { return ratingChange; }
    public void setRatingChange(Integer ratingChange) { this.ratingChange = ratingChange; }

    public Integer getOpponentRating() { return opponentRating; }
    public void setOpponentRating(Integer opponentRating) { this.opponentRating = opponentRating; }

    public String getOpponentUsername() { return opponentUsername; }
    public void setOpponentUsername(String opponentUsername) { this.opponentUsername = opponentUsername; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
