package com.codeduel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions", indexes = {
    @Index(name = "idx_sub_user", columnList = "user_id"),
    @Index(name = "idx_sub_problem", columnList = "problem_id"),
    @Index(name = "idx_sub_match", columnList = "match_id")
})
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Language language;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String sourceCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SubmissionStatus status = SubmissionStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private SubmissionResult result;

    private Long executionTimeMs = 0L;
    private Double memoryUsageMb = 0.0;

    @Column(nullable = false)
    private Integer testsPassed = 0;

    @Column(nullable = false)
    private Integer totalTests = 0;

    private Double efficiencyScore = 0.0;

    @Column(length = 50)
    private String estimatedTimeComplexity;

    @Column(length = 50)
    private String estimatedSpaceComplexity;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String compilerOutput;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String errorDetails;

    @Column(nullable = false)
    private Boolean isPractice = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    public Submission() {}

    public Submission(User user, Match match, Problem problem, Language language, String sourceCode, Boolean isPractice) {
        this.user = user;
        this.match = match;
        this.problem = problem;
        this.language = language;
        this.sourceCode = sourceCode;
        this.isPractice = isPractice != null ? isPractice : false;
        this.status = SubmissionStatus.PENDING;
        this.submittedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }

    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

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

    public String getEstimatedTimeComplexity() { return estimatedTimeComplexity; }
    public void setEstimatedTimeComplexity(String estimatedTimeComplexity) { this.estimatedTimeComplexity = estimatedTimeComplexity; }

    public String getEstimatedSpaceComplexity() { return estimatedSpaceComplexity; }
    public void setEstimatedSpaceComplexity(String estimatedSpaceComplexity) { this.estimatedSpaceComplexity = estimatedSpaceComplexity; }

    public String getCompilerOutput() { return compilerOutput; }
    public void setCompilerOutput(String compilerOutput) { this.compilerOutput = compilerOutput; }

    public String getErrorDetails() { return errorDetails; }
    public void setErrorDetails(String errorDetails) { this.errorDetails = errorDetails; }

    public Boolean getIsPractice() { return isPractice; }
    public void setIsPractice(Boolean isPractice) { this.isPractice = isPractice; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
