package com.codecombat;

import com.codecombat.judge.EfficiencyEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EfficiencyEngineTest {

    private EfficiencyEngine engine;

    @BeforeEach
    void setUp() {
        engine = new EfficiencyEngine();
    }

    @Test
    void testAntiJitterTolerance() {
        // 102ms vs 105ms should produce almost zero scoring difference (< 0.5 points)
        double score1 = engine.calculateEfficiency(102, 22.0, 2000, 256);
        double score2 = engine.calculateEfficiency(105, 22.0, 2000, 256);

        assertTrue(Math.abs(score1 - score2) <= 0.5, "Minor hardware jitter (102ms vs 105ms) must produce negligible score difference");
    }

    @Test
    void testFastSolutionScoresHigherThanSlow() {
        double fastScore = engine.calculateEfficiency(50, 18.0, 2000, 256);
        double slowScore = engine.calculateEfficiency(1800, 200.0, 2000, 256);

        assertTrue(fastScore > slowScore, "Fast solution should score substantially higher than near-timeout solution");
    }

    @Test
    void testMatchScoreHierarchyCorrectnessDominates() {
        // Accepted solution with moderate runtime
        int acceptedScore = engine.calculateMatchScore(true, 5, 5, 120, 24.0, 2000, 256, 300, 900);

        // Failed solution (3/5 tests) even with super fast runtime
        int failedScore = engine.calculateMatchScore(false, 3, 5, 10, 10.0, 2000, 256, 50, 900);

        assertTrue(acceptedScore >= 600, "Accepted solution score must be >= 600");
        assertTrue(failedScore < 300, "Failed solution score must be < 300");
        assertTrue(acceptedScore > failedScore, "Accepted solution must beat failed solution regardless of speed");
    }
}
