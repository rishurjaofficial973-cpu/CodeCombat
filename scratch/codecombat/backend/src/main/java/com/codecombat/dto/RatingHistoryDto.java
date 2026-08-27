package com.codecombat.dto;

import com.codecombat.model.RatingHistory;

import java.time.LocalDateTime;

public class RatingHistoryDto {
    private Long id;
    private Integer oldRating;
    private Integer newRating;
    private Integer ratingChange;
    private Integer opponentRating;
    private String opponentUsername;
    private LocalDateTime createdAt;

    public RatingHistoryDto() {}

    public static RatingHistoryDto fromEntity(RatingHistory rh) {
        if (rh == null) return null;
        RatingHistoryDto dto = new RatingHistoryDto();
        dto.setId(rh.getId());
        dto.setOldRating(rh.getOldRating());
        dto.setNewRating(rh.getNewRating());
        dto.setRatingChange(rh.getRatingChange());
        dto.setOpponentRating(rh.getOpponentRating());
        dto.setOpponentUsername(rh.getOpponentUsername());
        dto.setCreatedAt(rh.getCreatedAt());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
