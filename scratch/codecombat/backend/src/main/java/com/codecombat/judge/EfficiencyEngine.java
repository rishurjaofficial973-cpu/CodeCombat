package com.codecombat.judge;

import org.springframework.stereotype.Component;

@Component
public class EfficiencyEngine {

    /**
     * Calculates efficiency score from 0.0 to 100.0
     * Execution Performance = 70%
     * Memory Efficiency = 30%
     *
     * Applies logarithmic normalization and jitter damping
     * so that micro-variations (e.g. 102ms vs 105ms) do not unfairly penalize players.
     */
    public double calculateEfficiency(long runtimeMs, double memoryMb, int timeLimitMs, int memoryLimitMb) {
        if (runtimeMs <= 0) runtimeMs = 1;
        if (memoryMb <= 0) memoryMb = 1.0;

        // Baseline targets
        double targetRuntimeMs = Math.max(20.0, timeLimitMs * 0.15); // e.g. 300ms for 2000ms limit
        double targetMemoryMb = Math.max(16.0, memoryLimitMb * 0.15); // e.g. 38MB

        // Runtime ratio: 1.0 if runtime <= targetRuntimeMs
        double runtimeRatio;
        if (runtimeMs <= targetRuntimeMs) {
            runtimeRatio = 1.0;
        } else {
            double excess = (double) (runtimeMs - targetRuntimeMs) / (timeLimitMs - targetRuntimeMs);
            // Damping curve with square root to smooth out jitter
            runtimeRatio = Math.max(0.0, 1.0 - Math.sqrt(Math.min(1.0, excess)));
        }

        // Memory ratio
        double memoryRatio;
        if (memoryMb <= targetMemoryMb) {
            memoryRatio = 1.0;
        } else {
            double excess = (memoryMb - targetMemoryMb) / (memoryLimitMb - targetMemoryMb);
            memoryRatio = Math.max(0.0, 1.0 - Math.sqrt(Math.min(1.0, excess)));
        }

        double score = (runtimeRatio * 70.0) + (memoryRatio * 30.0);
        return Math.round(Math.min(100.0, Math.max(0.0, score)) * 10.0) / 10.0;
    }

    /**
     * Calculates total match score (out of 1000):
     * - Correctness: 600 pts
     * - Execution Efficiency: 200 pts
     * - Memory Efficiency: 100 pts
     * - Submission Speed: 100 pts
     */
    public int calculateMatchScore(boolean isAccepted, int testsPassed, int totalTests,
                                   long runtimeMs, double memoryMb, int timeLimitMs, int memoryLimitMb,
                                   int submissionTimeSeconds, int matchDurationSeconds) {
        if (!isAccepted) {
            // Partial correctness if some test cases passed (up to 300 pts)
            if (totalTests > 0) {
                return (int) Math.round(((double) testsPassed / totalTests) * 300.0);
            }
            return 0;
        }

        // 1. Correctness: 600 pts
        int correctnessScore = 600;

        // 2. Efficiency calculations
        double efficiency = calculateEfficiency(runtimeMs, memoryMb, timeLimitMs, memoryLimitMb);
        int executionScore = (int) Math.round((efficiency * 0.70 / 70.0) * 200.0); // max 200
        int memoryScore = (int) Math.round((efficiency * 0.30 / 30.0) * 100.0);    // max 100

        // 3. Submission Speed score: max 100 pts
        int speedScore;
        if (matchDurationSeconds <= 0) {
            speedScore = 50;
        } else {
            double timeUsedRatio = (double) submissionTimeSeconds / matchDurationSeconds;
            speedScore = (int) Math.round(Math.max(0.0, 1.0 - timeUsedRatio) * 100.0);
        }

        int total = correctnessScore + executionScore + memoryScore + speedScore;
        return Math.min(1000, Math.max(0, total));
    }
}
