package com.codecombat.dto;

import java.util.ArrayList;
import java.util.List;

public class PostMatchAnalysisDto {
    private String problemId;
    private String problemTitle;
    private String expectedTimeComplexity;
    private String expectedSpaceComplexity;

    // My performance
    private Long myRuntimeMs;
    private Double myMemoryMb;
    private Double myEfficiencyScore;
    private Integer myScore;
    private String myEstimatedTimeComplexity;
    private String myEstimatedSpaceComplexity;

    // Opponent performance
    private Long opponentRuntimeMs;
    private Double opponentMemoryMb;
    private Double opponentEfficiencyScore;
    private Integer opponentScore;
    private String opponentEstimatedTimeComplexity;
    private String opponentEstimatedSpaceComplexity;

    // Global benchmarks
    private Double avgProblemRuntimeMs;
    private Double avgProblemMemoryMb;
    private Double myRuntimePercentile; // e.g. 84.5% better than
    private Double myMemoryPercentile;

    private List<String> optimizationTips = new ArrayList<>();

    public PostMatchAnalysisDto() {}

    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }

    public String getProblemTitle() { return problemTitle; }
    public void setProblemTitle(String problemTitle) { this.problemTitle = problemTitle; }

    public String getExpectedTimeComplexity() { return expectedTimeComplexity; }
    public void setExpectedTimeComplexity(String expectedTimeComplexity) { this.expectedTimeComplexity = expectedTimeComplexity; }

    public String getExpectedSpaceComplexity() { return expectedSpaceComplexity; }
    public void setExpectedSpaceComplexity(String expectedSpaceComplexity) { this.expectedSpaceComplexity = expectedSpaceComplexity; }

    public Long getMyRuntimeMs() { return myRuntimeMs; }
    public void setMyRuntimeMs(Long myRuntimeMs) { this.myRuntimeMs = myRuntimeMs; }

    public Double getMyMemoryMb() { return myMemoryMb; }
    public void setMyMemoryMb(Double myMemoryMb) { this.myMemoryMb = myMemoryMb; }

    public Double getMyEfficiencyScore() { return myEfficiencyScore; }
    public void setMyEfficiencyScore(Double myEfficiencyScore) { this.myEfficiencyScore = myEfficiencyScore; }

    public Integer getMyScore() { return myScore; }
    public void setMyScore(Integer myScore) { this.myScore = myScore; }

    public String getMyEstimatedTimeComplexity() { return myEstimatedTimeComplexity; }
    public void setMyEstimatedTimeComplexity(String myEstimatedTimeComplexity) { this.myEstimatedTimeComplexity = myEstimatedTimeComplexity; }

    public String getMyEstimatedSpaceComplexity() { return myEstimatedSpaceComplexity; }
    public void setMyEstimatedSpaceComplexity(String myEstimatedSpaceComplexity) { this.myEstimatedSpaceComplexity = myEstimatedSpaceComplexity; }

    public Long getOpponentRuntimeMs() { return opponentRuntimeMs; }
    public void setOpponentRuntimeMs(Long opponentRuntimeMs) { this.opponentRuntimeMs = opponentRuntimeMs; }

    public Double getOpponentMemoryMb() { return opponentMemoryMb; }
    public void setOpponentMemoryMb(Double opponentMemoryMb) { this.opponentMemoryMb = opponentMemoryMb; }

    public Double getOpponentEfficiencyScore() { return opponentEfficiencyScore; }
    public void setOpponentEfficiencyScore(Double opponentEfficiencyScore) { this.opponentEfficiencyScore = opponentEfficiencyScore; }

    public Integer getOpponentScore() { return opponentScore; }
    public void setOpponentScore(Integer opponentScore) { this.opponentScore = opponentScore; }

    public String getOpponentEstimatedTimeComplexity() { return opponentEstimatedTimeComplexity; }
    public void setOpponentEstimatedTimeComplexity(String opponentEstimatedTimeComplexity) { this.opponentEstimatedTimeComplexity = opponentEstimatedTimeComplexity; }

    public String getOpponentEstimatedSpaceComplexity() { return opponentEstimatedSpaceComplexity; }
    public void setOpponentEstimatedSpaceComplexity(String opponentEstimatedSpaceComplexity) { this.opponentEstimatedSpaceComplexity = opponentEstimatedSpaceComplexity; }

    public Double getAvgProblemRuntimeMs() { return avgProblemRuntimeMs; }
    public void setAvgProblemRuntimeMs(Double avgProblemRuntimeMs) { this.avgProblemRuntimeMs = avgProblemRuntimeMs; }

    public Double getAvgProblemMemoryMb() { return avgProblemMemoryMb; }
    public void setAvgProblemMemoryMb(Double avgProblemMemoryMb) { this.avgProblemMemoryMb = avgProblemMemoryMb; }

    public Double getMyRuntimePercentile() { return myRuntimePercentile; }
    public void setMyRuntimePercentile(Double myRuntimePercentile) { this.myRuntimePercentile = myRuntimePercentile; }

    public Double getMyMemoryPercentile() { return myMemoryPercentile; }
    public void setMyMemoryPercentile(Double myMemoryPercentile) { this.myMemoryPercentile = myMemoryPercentile; }

    public List<String> getOptimizationTips() { return optimizationTips; }
    public void setOptimizationTips(List<String> optimizationTips) { this.optimizationTips = optimizationTips; }
}
