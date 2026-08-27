package com.codecombat.dto;

import com.codecombat.model.TestCase;

public class TestCaseDto {
    private Long id;
    private String inputData;
    private String expectedOutput;
    private Boolean isHidden;
    private String explanation;
    private Integer orderIndex;

    public TestCaseDto() {}

    public static TestCaseDto fromEntity(TestCase tc) {
        if (tc == null) return null;
        TestCaseDto dto = new TestCaseDto();
        dto.setId(tc.getId());
        dto.setInputData(tc.getInputData());
        dto.setExpectedOutput(tc.getExpectedOutput());
        dto.setIsHidden(tc.getIsHidden());
        dto.setExplanation(tc.getExplanation());
        dto.setOrderIndex(tc.getOrderIndex());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }

    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }

    public Boolean getIsHidden() { return isHidden; }
    public void setIsHidden(Boolean isHidden) { this.isHidden = isHidden; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}
