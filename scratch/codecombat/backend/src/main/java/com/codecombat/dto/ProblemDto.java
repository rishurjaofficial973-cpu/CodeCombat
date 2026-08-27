package com.codecombat.dto;

import com.codecombat.model.Difficulty;
import com.codecombat.model.Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemDto {
    private String id;
    private String title;
    private String slug;
    private Difficulty difficulty;
    private String topics;
    private String patterns;
    private String description;
    private String constraints;
    private String examples;
    private String hints;
    private String editorial;
    private String inputFormat;
    private String outputFormat;
    private Integer timeLimitMs;
    private Integer memoryLimitMb;
    private String expectedTimeComplexity;
    private String expectedSpaceComplexity;
    private String source;
    private String externalUrl;
    private Map<String, String> starterCodes = new HashMap<>();
    private List<TestCaseDto> publicTestCases = new ArrayList<>();
    private Boolean isSolvedByMe = false;
    private Long myBestRuntimeMs;
    private Double avgRuntimeMs;
    private Double avgMemoryMb;
    private Integer totalSubmissions;
    private Integer acceptedSubmissions;

    public ProblemDto() {}

    public static ProblemDto fromEntity(Problem p, boolean includePublicTestCases) {
        if (p == null) return null;
        ProblemDto dto = new ProblemDto();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setSlug(p.getSlug());
        dto.setDifficulty(p.getDifficulty());
        dto.setTopics(p.getTopics());
        dto.setPatterns(p.getPatterns());
        dto.setDescription(p.getDescription());
        dto.setConstraints(p.getConstraints());
        dto.setExamples(p.getExamples());
        dto.setHints(p.getHints());
        dto.setEditorial(p.getEditorial());
        dto.setInputFormat(p.getInputFormat());
        dto.setOutputFormat(p.getOutputFormat());
        dto.setTimeLimitMs(p.getTimeLimitMs());
        dto.setMemoryLimitMb(p.getMemoryLimitMb());
        dto.setExpectedTimeComplexity(p.getExpectedTimeComplexity());
        dto.setExpectedSpaceComplexity(p.getExpectedSpaceComplexity());
        dto.setSource(p.getSource());
        dto.setExternalUrl(p.getExternalUrl());
        dto.setAvgRuntimeMs(p.getAvgRuntimeMs());
        dto.setAvgMemoryMb(p.getAvgMemoryMb());
        dto.setTotalSubmissions(p.getTotalSubmissions());
        dto.setAcceptedSubmissions(p.getAcceptedSubmissions());

        Map<String, String> starters = new HashMap<>();
        starters.put("JAVA", p.getStarterCodeJava());
        starters.put("PYTHON", p.getStarterCodePython());
        starters.put("CPP", p.getStarterCodeCpp());
        starters.put("JAVASCRIPT", p.getStarterCodeJs());
        dto.setStarterCodes(starters);

        if (includePublicTestCases && p.getTestCases() != null) {
            p.getTestCases().stream()
                .filter(tc -> !Boolean.TRUE.equals(tc.getIsHidden()))
                .forEach(tc -> dto.getPublicTestCases().add(TestCaseDto.fromEntity(tc)));
        }

        return dto;
    }

    // Getters and Setters
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

    public Map<String, String> getStarterCodes() { return starterCodes; }
    public void setStarterCodes(Map<String, String> starterCodes) { this.starterCodes = starterCodes; }

    public List<TestCaseDto> getPublicTestCases() { return publicTestCases; }
    public void setPublicTestCases(List<TestCaseDto> publicTestCases) { this.publicTestCases = publicTestCases; }

    public Boolean getIsSolvedByMe() { return isSolvedByMe; }
    public void setIsSolvedByMe(Boolean isSolvedByMe) { this.isSolvedByMe = isSolvedByMe; }

    public Long getMyBestRuntimeMs() { return myBestRuntimeMs; }
    public void setMyBestRuntimeMs(Long myBestRuntimeMs) { this.myBestRuntimeMs = myBestRuntimeMs; }

    public Double getAvgRuntimeMs() { return avgRuntimeMs; }
    public void setAvgRuntimeMs(Double avgRuntimeMs) { this.avgRuntimeMs = avgRuntimeMs; }

    public Double getAvgMemoryMb() { return avgMemoryMb; }
    public void setAvgMemoryMb(Double avgMemoryMb) { this.avgMemoryMb = avgMemoryMb; }

    public Integer getTotalSubmissions() { return totalSubmissions; }
    public void setTotalSubmissions(Integer totalSubmissions) { this.totalSubmissions = totalSubmissions; }

    public Integer getAcceptedSubmissions() { return acceptedSubmissions; }
    public void setAcceptedSubmissions(Integer acceptedSubmissions) { this.acceptedSubmissions = acceptedSubmissions; }
}
