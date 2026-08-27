package com.codeduel.dto;

import com.codeduel.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubmissionRequestDto {
    private String matchId; // null for practice

    @NotBlank
    private String problemId;

    @NotNull
    private Language language;

    @NotBlank
    private String sourceCode;

    private Boolean isPractice = false;

    public SubmissionRequestDto() {}

    public SubmissionRequestDto(String matchId, String problemId, Language language, String sourceCode, Boolean isPractice) {
        this.matchId = matchId;
        this.problemId = problemId;
        this.language = language;
        this.sourceCode = sourceCode;
        this.isPractice = isPractice != null ? isPractice : false;
    }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }

    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    public Boolean getIsPractice() { return isPractice; }
    public void setIsPractice(Boolean isPractice) { this.isPractice = isPractice; }
}
