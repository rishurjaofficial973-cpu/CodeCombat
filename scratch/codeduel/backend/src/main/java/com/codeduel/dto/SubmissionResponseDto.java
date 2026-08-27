package com.codeduel.dto;

import com.codeduel.model.Submission;
import com.codeduel.model.SubmissionResult;
import com.codeduel.model.SubmissionStatus;

import java.util.ArrayList;
import java.util.List;

public class SubmissionResponseDto {
    private Long id;
    private SubmissionStatus status;
    private SubmissionResult result;
    private Long executionTimeMs;
    private Double memoryUsageMb;
    private Integer testsPassed;
    private Integer totalTests;
    private Double efficiencyScore;
    private Integer score;
    private String compilerOutput;
    private String errorDetails;
    private String estimatedTimeComplexity;
    private String estimatedSpaceComplexity;
    private String optimizationTip;
    private List<TestCaseResultDto> testCaseResults = new ArrayList<>();

    public SubmissionResponseDto() {}

    public static SubmissionResponseDto fromEntity(Submission s) {
        if (s == null) return null;
        SubmissionResponseDto dto = new SubmissionResponseDto();
        dto.setId(s.getId());
        dto.setStatus(s.getStatus());
        dto.setResult(s.getResult());
        dto.setExecutionTimeMs(s.getExecutionTimeMs());
        dto.setMemoryUsageMb(s.getMemoryUsageMb());
        dto.setTestsPassed(s.getTestsPassed());
        dto.setTotalTests(s.getTotalTests());
        dto.setEfficiencyScore(s.getEfficiencyScore());
        dto.setCompilerOutput(s.getCompilerOutput());
        dto.setErrorDetails(s.getErrorDetails());
        dto.setEstimatedTimeComplexity(s.getEstimatedTimeComplexity());
        dto.setEstimatedSpaceComplexity(s.getEstimatedSpaceComplexity());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus status) { this.status = status; }

    public SubmissionResult getResult() { return result; }
    public void setResult(SubmissionResult result) { this.result = result; }

    public Long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(Long executionTimeMs) { this.executionTimeMs = executionTimeMs; }

    public Double getMemoryUsageMb() { return memoryUsageMb; }
    public void setMemoryUsageMb(Double memoryUsageMb) { this.memoryUsageMb = memoryUsageMb; }

    public Integer getTestsPassed() { return testsPassed; }
    public void setTestsPassed(Integer testsPassed) { this.testsPassed = testsPassed; }

    public Integer getTotalTests() { return totalTests; }
    public void setTotalTests(Integer totalTests) { this.totalTests = totalTests; }

    public Double getEfficiencyScore() { return efficiencyScore; }
    public void setEfficiencyScore(Double efficiencyScore) { this.efficiencyScore = efficiencyScore; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getCompilerOutput() { return compilerOutput; }
    public void setCompilerOutput(String compilerOutput) { this.compilerOutput = compilerOutput; }

    public String getErrorDetails() { return errorDetails; }
    public void setErrorDetails(String errorDetails) { this.errorDetails = errorDetails; }

    public String getEstimatedTimeComplexity() { return estimatedTimeComplexity; }
    public void setEstimatedTimeComplexity(String estimatedTimeComplexity) { this.estimatedTimeComplexity = estimatedTimeComplexity; }

    public String getEstimatedSpaceComplexity() { return estimatedSpaceComplexity; }
    public void setEstimatedSpaceComplexity(String estimatedSpaceComplexity) { this.estimatedSpaceComplexity = estimatedSpaceComplexity; }

    public String getOptimizationTip() { return optimizationTip; }
    public void setOptimizationTip(String optimizationTip) { this.optimizationTip = optimizationTip; }

    public List<TestCaseResultDto> getTestCaseResults() { return testCaseResults; }
    public void setTestCaseResults(List<TestCaseResultDto> testCaseResults) { this.testCaseResults = testCaseResults; }
}
