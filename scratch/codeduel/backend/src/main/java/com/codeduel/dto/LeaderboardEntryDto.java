package com.codeduel.dto;

public class LeaderboardEntryDto {
    private Integer rank;
    private Long userId;
    private String username;
    private Integer rating;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer matchesPlayed;
    private Double winRate;
    private Integer winStreak;

    public LeaderboardEntryDto() {}

    public LeaderboardEntryDto(Integer rank, Long userId, String username, Integer rating, Integer wins, Integer losses, Integer draws, Integer matchesPlayed, Integer winStreak) {
        this.rank = rank;
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.matchesPlayed = matchesPlayed;
        this.winStreak = winStreak;
        if (matchesPlayed != null && matchesPlayed > 0) {
            this.winRate = Math.round(((double) wins / matchesPlayed) * 1000.0) / 10.0;
        } else {
            this.winRate = 0.0;
        }
    }

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

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

    public Double getWinRate() { return winRate; }
    public void setWinRate(Double winRate) { this.winRate = winRate; }

    public Integer getWinStreak() { return winStreak; }
    public void setWinStreak(Integer winStreak) { this.winStreak = winStreak; }
}
