package com.codecombat.dto;

import com.codecombat.model.Match;
import com.codecombat.model.MatchMode;
import com.codecombat.model.MatchStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class MatchResponseDto {
    private String id;
    private ProblemDto problem;
    private MatchStatus status;
    private MatchMode mode;
    private Integer timeLimitSeconds;
    private Long remainingSeconds;
    private Long countdownRemainingSeconds;
    private LocalDateTime countdownStartTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long winnerId;
    private Boolean isDraw;
    private List<MatchPlayerDto> players = new ArrayList<>();

    public MatchResponseDto() {}

    public static MatchResponseDto fromEntity(Match match, Long currentUserId) {
        if (match == null) return null;
        MatchResponseDto dto = new MatchResponseDto();
        dto.setId(match.getId());
        dto.setStatus(match.getStatus());
        dto.setMode(match.getMode());
        dto.setTimeLimitSeconds(match.getTimeLimitSeconds());
        dto.setCountdownStartTime(match.getCountdownStartTime());
        dto.setStartTime(match.getStartTime());
        dto.setEndTime(match.getEndTime());
        dto.setWinnerId(match.getWinnerId());
        dto.setIsDraw(match.getIsDraw());

        // Calculate remaining seconds
        if (match.getStatus() == MatchStatus.COUNTDOWN && match.getCountdownStartTime() != null) {
            long elapsed = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - match.getCountdownStartTime().toEpochSecond(ZoneOffset.UTC);
            dto.setCountdownRemainingSeconds(Math.max(0, 3 - elapsed));
        } else if (match.getStatus() == MatchStatus.ACTIVE && match.getStartTime() != null) {
            long elapsed = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - match.getStartTime().toEpochSecond(ZoneOffset.UTC);
            dto.setRemainingSeconds(Math.max(0, match.getTimeLimitSeconds() - elapsed));
        } else if (match.getStatus() == MatchStatus.COMPLETED || match.getStatus() == MatchStatus.EXPIRED) {
            dto.setRemainingSeconds(0L);
        } else {
            dto.setRemainingSeconds((long) match.getTimeLimitSeconds());
        }

        if (match.getProblem() != null) {
            dto.setProblem(ProblemDto.fromEntity(match.getProblem(), true));
        }

        if (match.getMatchPlayers() != null) {
            match.getMatchPlayers().forEach(mp -> dto.getPlayers().add(MatchPlayerDto.fromEntity(mp)));
        }

        return dto;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ProblemDto getProblem() { return problem; }
    public void setProblem(ProblemDto problem) { this.problem = problem; }

    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }

    public MatchMode getMode() { return mode; }
    public void setMode(MatchMode mode) { this.mode = mode; }

    public Integer getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }

    public Long getRemainingSeconds() { return remainingSeconds; }
    public void setRemainingSeconds(Long remainingSeconds) { this.remainingSeconds = remainingSeconds; }

    public Long getCountdownRemainingSeconds() { return countdownRemainingSeconds; }
    public void setCountdownRemainingSeconds(Long countdownRemainingSeconds) { this.countdownRemainingSeconds = countdownRemainingSeconds; }

    public LocalDateTime getCountdownStartTime() { return countdownStartTime; }
    public void setCountdownStartTime(LocalDateTime countdownStartTime) { this.countdownStartTime = countdownStartTime; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Long getWinnerId() { return winnerId; }
    public void setWinnerId(Long winnerId) { this.winnerId = winnerId; }

    public Boolean getIsDraw() { return isDraw; }
    public void setIsDraw(Boolean isDraw) { this.isDraw = isDraw; }

    public List<MatchPlayerDto> getPlayers() { return players; }
    public void setPlayers(List<MatchPlayerDto> players) { this.players = players; }
}
