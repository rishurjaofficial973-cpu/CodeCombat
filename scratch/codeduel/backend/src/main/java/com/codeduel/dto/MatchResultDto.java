package com.codeduel.dto;

import java.util.ArrayList;
import java.util.List;

public class MatchResultDto {
    private String matchId;
    private String problemId;
    private String problemTitle;
    private Long winnerId;
    private String winnerUsername;
    private Boolean isDraw;
    private List<MatchPlayerDto> players = new ArrayList<>();
    private PostMatchAnalysisDto analysis;

    public MatchResultDto() {}

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }

    public String getProblemTitle() { return problemTitle; }
    public void setProblemTitle(String problemTitle) { this.problemTitle = problemTitle; }

    public Long getWinnerId() { return winnerId; }
    public void setWinnerId(Long winnerId) { this.winnerId = winnerId; }

    public String getWinnerUsername() { return winnerUsername; }
    public void setWinnerUsername(String winnerUsername) { this.winnerUsername = winnerUsername; }

    public Boolean getIsDraw() { return isDraw; }
    public void setIsDraw(Boolean isDraw) { this.isDraw = isDraw; }

    public List<MatchPlayerDto> getPlayers() { return players; }
    public void setPlayers(List<MatchPlayerDto> players) { this.players = players; }

    public PostMatchAnalysisDto getAnalysis() { return analysis; }
    public void setAnalysis(PostMatchAnalysisDto analysis) { this.analysis = analysis; }
}
