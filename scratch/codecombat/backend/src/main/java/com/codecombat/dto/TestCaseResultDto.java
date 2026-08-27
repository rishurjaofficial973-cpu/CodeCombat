package com.codecombat.dto;

public class TestCaseResultDto {
    private Long testCaseId;
    private String input;
    private String expected;
    private String actual;
    private Boolean passed;
    private Long executionTimeMs;
    private Boolean isHidden;
    private String error;

    public TestCaseResultDto() {}

    public TestCaseResultDto(Long testCaseId, String input, String expected, String actual, Boolean passed, Long executionTimeMs, Boolean isHidden, String error) {
        this.testCaseId = testCaseId;
        this.input = isHidden ? "[Hidden]" : input;
        this.expected = isHidden ? "[Hidden]" : expected;
        this.actual = isHidden && !Boolean.TRUE.equals(passed) ? "[Hidden Output]" : actual;
        this.passed = passed;
        this.executionTimeMs = executionTimeMs;
        this.isHidden = isHidden;
        this.error = error;
    }

    public Long getTestCaseId() { return testCaseId; }
    public void setTestCaseId(Long testCaseId) { this.testCaseId = testCaseId; }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String getExpected() { return expected; }
    public void setExpected(String expected) { this.expected = expected; }

    public String getActual() { return actual; }
    public void setActual(String actual) { this.actual = actual; }

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }

    public Long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(Long executionTimeMs) { this.executionTimeMs = executionTimeMs; }

    public Boolean getIsHidden() { return isHidden; }
    public void setIsHidden(Boolean isHidden) { this.isHidden = isHidden; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
