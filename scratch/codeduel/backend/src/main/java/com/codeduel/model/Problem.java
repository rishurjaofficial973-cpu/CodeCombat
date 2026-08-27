package com.codeduel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems", indexes = {
    @Index(name = "idx_problem_difficulty", columnList = "difficulty"),
    @Index(name = "idx_problem_slug", columnList = "slug", unique = true)
})
public class Problem {

    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id; // e.g. "CD-0001"

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 200)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty = Difficulty.MEDIUM;

    @Column(length = 500)
    private String topics; // comma separated: "Array,Hash Table"

    @Column(length = 500)
    private String patterns; // comma separated: "Two Pointers,Sliding Window"

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String constraints;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String examples; // JSON format

    @Lob
    @Column(columnDefinition = "TEXT")
    private String hints; // JSON or markdown formatted hints

    @Lob
    @Column(columnDefinition = "TEXT")
    private String editorial; // Detailed editorial walkthrough

    @Lob
    @Column(columnDefinition = "TEXT")
    private String inputFormat;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String outputFormat;

    @Column(nullable = false)
    private Integer timeLimitMs = 2000;

    @Column(nullable = false)
    private Integer memoryLimitMb = 256;

    @Column(length = 50)
    private String expectedTimeComplexity = "O(n)";

    @Column(length = 50)
    private String expectedSpaceComplexity = "O(1)";

    @Column(length = 100)
    private String source = "CodeDuel";

    @Column(length = 500)
    private String externalUrl;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String starterCodeJava;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String starterCodePython;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String starterCodeCpp;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String starterCodeJs;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String solutionExplanation;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TestCase> testCases = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isActive = true;

    private Integer totalSubmissions = 0;
    private Integer acceptedSubmissions = 0;
    private Double avgRuntimeMs = 120.0;
    private Double avgMemoryMb = 28.0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Problem() {}

    public Problem(String id, String title, String slug, Difficulty difficulty, String topics, String description) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.difficulty = difficulty;
        this.topics = topics;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public String getTopics() { return topics; }
    public void setTopics(String topics) { this.topics = topics; }

    public String getPatterns() { return patterns; }
    public void setPatterns(String patterns) { this.patterns = patterns; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getConstraints() { return constraints; }
    public void setConstraints(String constraints) { this.constraints = constraints; }

    public String getExamples() { return examples; }
    public void setExamples(String examples) { this.examples = examples; }

    public String getHints() { return hints; }
    public void setHints(String hints) { this.hints = hints; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public String getInputFormat() { return inputFormat; }
    public void setInputFormat(String inputFormat) { this.inputFormat = inputFormat; }

    public String getOutputFormat() { return outputFormat; }
    public void setOutputFormat(String outputFormat) { this.outputFormat = outputFormat; }

    public Integer getTimeLimitMs() { return timeLimitMs; }
    public void setTimeLimitMs(Integer timeLimitMs) { this.timeLimitMs = timeLimitMs; }

    public Integer getMemoryLimitMb() { return memoryLimitMb; }
    public void setMemoryLimitMb(Integer memoryLimitMb) { this.memoryLimitMb = memoryLimitMb; }

    public String getExpectedTimeComplexity() { return expectedTimeComplexity; }
    public void setExpectedTimeComplexity(String expectedTimeComplexity) { this.expectedTimeComplexity = expectedTimeComplexity; }

    public String getExpectedSpaceComplexity() { return expectedSpaceComplexity; }
    public void setExpectedSpaceComplexity(String expectedSpaceComplexity) { this.expectedSpaceComplexity = expectedSpaceComplexity; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getExternalUrl() { return externalUrl; }
    public void setExternalUrl(String externalUrl) { this.externalUrl = externalUrl; }

    public String getStarterCodeJava() { return starterCodeJava; }
    public void setStarterCodeJava(String starterCodeJava) { this.starterCodeJava = starterCodeJava; }

    public String getStarterCodePython() { return starterCodePython; }
    public void setStarterCodePython(String starterCodePython) { this.starterCodePython = starterCodePython; }

    public String getStarterCodeCpp() { return starterCodeCpp; }
    public void setStarterCodeCpp(String starterCodeCpp) { this.starterCodeCpp = starterCodeCpp; }

    public String getStarterCodeJs() { return starterCodeJs; }
    public void setStarterCodeJs(String starterCodeJs) { this.starterCodeJs = starterCodeJs; }

    public String getSolutionExplanation() { return solutionExplanation; }
    public void setSolutionExplanation(String solutionExplanation) { this.solutionExplanation = solutionExplanation; }

    public List<TestCase> getTestCases() { return testCases; }
    public void setTestCases(List<TestCase> testCases) { this.testCases = testCases; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Integer getTotalSubmissions() { return totalSubmissions; }
    public void setTotalSubmissions(Integer totalSubmissions) { this.totalSubmissions = totalSubmissions; }

    public Integer getAcceptedSubmissions() { return acceptedSubmissions; }
    public void setAcceptedSubmissions(Integer acceptedSubmissions) { this.acceptedSubmissions = acceptedSubmissions; }

    public Double getAvgRuntimeMs() { return avgRuntimeMs; }
    public void setAvgRuntimeMs(Double avgRuntimeMs) { this.avgRuntimeMs = avgRuntimeMs; }

    public Double getAvgMemoryMb() { return avgMemoryMb; }
    public void setAvgMemoryMb(Double avgMemoryMb) { this.avgMemoryMb = avgMemoryMb; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
