package com.codeduel.judge;

import com.codeduel.dto.SubmissionResponseDto;
import com.codeduel.dto.TestCaseResultDto;
import com.codeduel.model.*;
import com.codeduel.repository.ProblemRepository;
import com.codeduel.repository.TestCaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JudgeService {

    private static final Logger log = LoggerFactory.getLogger(JudgeService.class);

    private final ExecutionSandbox sandbox;
    private final EfficiencyEngine efficiencyEngine;
    private final ComplexityEstimator complexityEstimator;
    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;

    public JudgeService(ExecutionSandbox sandbox,
                        EfficiencyEngine efficiencyEngine,
                        ComplexityEstimator complexityEstimator,
                        TestCaseRepository testCaseRepository,
                        ProblemRepository problemRepository) {
        this.sandbox = sandbox;
        this.efficiencyEngine = efficiencyEngine;
        this.complexityEstimator = complexityEstimator;
        this.testCaseRepository = testCaseRepository;
        this.problemRepository = problemRepository;
    }

    public static class JudgeVerdict {
        private SubmissionResult result;
        private long totalRuntimeMs;
        private double memoryMb;
        private int testsPassed;
        private int totalTests;
        private double efficiencyScore;
        private int matchScore;
        private String compilerOutput;
        private String errorDetails;
        private String estimatedTimeComplexity;
        private String estimatedSpaceComplexity;
        private String optimizationTip;
        private List<TestCaseResultDto> testCaseResults = new ArrayList<>();

        public SubmissionResult getResult() { return result; }
        public void setResult(SubmissionResult result) { this.result = result; }

        public long getTotalRuntimeMs() { return totalRuntimeMs; }
        public void setTotalRuntimeMs(long totalRuntimeMs) { this.totalRuntimeMs = totalRuntimeMs; }

        public double getMemoryMb() { return memoryMb; }
        public void setMemoryMb(double memoryMb) { this.memoryMb = memoryMb; }

        public int getTestsPassed() { return testsPassed; }
        public void setTestsPassed(int testsPassed) { this.testsPassed = testsPassed; }

        public int getTotalTests() { return totalTests; }
        public void setTotalTests(int totalTests) { this.totalTests = totalTests; }

        public double getEfficiencyScore() { return efficiencyScore; }
        public void setEfficiencyScore(double efficiencyScore) { this.efficiencyScore = efficiencyScore; }

        public int getMatchScore() { return matchScore; }
        public void setMatchScore(int matchScore) { this.matchScore = matchScore; }

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

    public JudgeVerdict judge(Problem problem, Language language, String sourceCode, Integer submissionTimeSeconds, Integer matchDurationSeconds) {
        log.info("Judging submission for problem {} with language {}", problem.getId(), language);
        JudgeVerdict verdict = new JudgeVerdict();

        List<TestCase> testCases = testCaseRepository.findByProblemIdOrderByOrderIndexAsc(problem.getId());
        if (testCases.isEmpty()) {
            // Default test case if none seeded
            testCases = List.of(new TestCase(problem, "4\n2 7 11 15\n9", "0 1", false, 1));
        }

        verdict.setTotalTests(testCases.size());
        int passedCount = 0;
        long totalRuntime = 0L;
        double maxMemory = 0.0;
        boolean hasFailed = false;

        // Estimate complexity
        ComplexityEstimator.ComplexityAnalysis complexityAnalysis = complexityEstimator.estimateComplexity(sourceCode, language.name());
        verdict.setEstimatedTimeComplexity(complexityAnalysis.getEstimatedTimeComplexity());
        verdict.setEstimatedSpaceComplexity(complexityAnalysis.getEstimatedSpaceComplexity());
        if (!complexityAnalysis.getSuggestions().isEmpty()) {
            verdict.setOptimizationTip(complexityAnalysis.getSuggestions().get(0));
        }

        for (TestCase tc : testCases) {
            ExecutionSandbox.ExecutionResult execResult = sandbox.execute(
                    language,
                    sourceCode,
                    tc.getInputData(),
                    problem.getTimeLimitMs(),
                    problem.getMemoryLimitMb()
            );

            if (execResult.isCompilationError()) {
                verdict.setResult(SubmissionResult.COMPILATION_ERROR);
                verdict.setCompilerOutput(execResult.getError());
                verdict.setErrorDetails(execResult.getError());
                hasFailed = true;
                break;
            }

            if (execResult.isTimedOut()) {
                verdict.setResult(SubmissionResult.TIME_LIMIT_EXCEEDED);
                verdict.setErrorDetails("Time Limit Exceeded on test case #" + tc.getOrderIndex());
                hasFailed = true;
                break;
            }

            if (execResult.getExitCode() != 0) {
                verdict.setResult(SubmissionResult.RUNTIME_ERROR);
                verdict.setErrorDetails(execResult.getError().isBlank() ? "Runtime Exception" : execResult.getError());
                hasFailed = true;
                break;
            }

            totalRuntime += execResult.getExecutionTimeMs();
            maxMemory = Math.max(maxMemory, execResult.getMemoryMb());

            boolean isPassed = isOutputMatching(execResult.getOutput(), tc.getExpectedOutput());
            if (isPassed) {
                passedCount++;
            } else if (!hasFailed) {
                verdict.setResult(SubmissionResult.WRONG_ANSWER);
                verdict.setErrorDetails("Output mismatch on test case #" + tc.getOrderIndex());
                hasFailed = true;
            }

            verdict.getTestCaseResults().add(new TestCaseResultDto(
                    tc.getId(),
                    tc.getInputData(),
                    tc.getExpectedOutput(),
                    execResult.getOutput(),
                    isPassed,
                    execResult.getExecutionTimeMs(),
                    Boolean.TRUE.equals(tc.getIsHidden()),
                    isPassed ? null : "Wrong output"
            ));
        }

        verdict.setTestsPassed(passedCount);
        long avgRuntime = testCases.isEmpty() ? 0L : Math.max(1, totalRuntime / testCases.size());
        verdict.setTotalRuntimeMs(avgRuntime);
        verdict.setMemoryMb(maxMemory > 0 ? maxMemory : 24.0);

        if (!hasFailed && passedCount == testCases.size()) {
            verdict.setResult(SubmissionResult.ACCEPTED);
        } else if (verdict.getResult() == null) {
            verdict.setResult(SubmissionResult.WRONG_ANSWER);
        }

        boolean isAccepted = verdict.getResult() == SubmissionResult.ACCEPTED;
        double efficiency = efficiencyEngine.calculateEfficiency(
                verdict.getTotalRuntimeMs(),
                verdict.getMemoryMb(),
                problem.getTimeLimitMs(),
                problem.getMemoryLimitMb()
        );
        verdict.setEfficiencyScore(efficiency);

        int matchScore = efficiencyEngine.calculateMatchScore(
                isAccepted,
                verdict.getTestsPassed(),
                verdict.getTotalTests(),
                verdict.getTotalRuntimeMs(),
                verdict.getMemoryMb(),
                problem.getTimeLimitMs(),
                problem.getMemoryLimitMb(),
                submissionTimeSeconds != null ? submissionTimeSeconds : 300,
                matchDurationSeconds != null ? matchDurationSeconds : 900
        );
        verdict.setMatchScore(matchScore);

        return verdict;
    }

    private boolean isOutputMatching(String actual, String expected) {
        if (actual == null || expected == null) return false;
        String cleanActual = actual.replaceAll("[\\[\\],]", " ").replaceAll("\\s+", " ").trim().toLowerCase();
        String cleanExpected = expected.replaceAll("[\\[\\],]", " ").replaceAll("\\s+", " ").trim().toLowerCase();
        return cleanActual.equals(cleanExpected) || actual.trim().equalsIgnoreCase(expected.trim());
    }
}
