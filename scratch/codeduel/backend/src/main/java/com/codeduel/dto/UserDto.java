package com.codeduel.dto;

import com.codeduel.model.Role;
import com.codeduel.model.User;

import java.time.LocalDateTime;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Integer rating;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer matchesPlayed;
    private Integer winStreak;
    private Integer bestWinStreak;
    private Integer globalRank;
    private Double winRate;
    private Long totalScore;
    private LocalDateTime createdAt;

    public UserDto() {}

    public static UserDto fromEntity(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setRating(user.getRating());
        dto.setWins(user.getWins());
        dto.setLosses(user.getLosses());
        dto.setDraws(user.getDraws());
        dto.setMatchesPlayed(user.getMatchesPlayed());
        dto.setWinStreak(user.getWinStreak());
        dto.setBestWinStreak(user.getBestWinStreak());
        dto.setGlobalRank(user.getGlobalRank());
        dto.setWinRate(user.getWinRate());
        dto.setTotalScore(user.getTotalScore());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public Integer getWins() { return wins; }
    public void setWins(Integer wins) { this.wins = wins; }

    public Integer getLosses() { return losses; }
    public void setLosses(Integer losses) { this.losses = losses; }

    public Integer getDraws() { return draws; }
    public void setDraws(Integer draws) { this.draws = draws; }

    public Integer getMatchesPlayed() { return matchesPlayed; }
    public void setMatchesPlayed(Integer matchesPlayed) { this.matchesPlayed = matchesPlayed; }

    public Integer getWinStreak() { return winStreak; }
    public void setWinStreak(Integer winStreak) { this.winStreak = winStreak; }

    public Integer getBestWinStreak() { return bestWinStreak; }
    public void setBestWinStreak(Integer bestWinStreak) { this.bestWinStreak = bestWinStreak; }

    public Integer getGlobalRank() { return globalRank; }
    public void setGlobalRank(Integer globalRank) { this.globalRank = globalRank; }

    public Double getWinRate() { return winRate; }
    public void setWinRate(Double winRate) { this.winRate = winRate; }

    public Long getTotalScore() { return totalScore; }
    public void setTotalScore(Long totalScore) { this.totalScore = totalScore; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
