package com.codeduel;

import com.codeduel.judge.ComplexityEstimator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexityEstimatorTest {

    private ComplexityEstimator estimator;

    @BeforeEach
    void setUp() {
        estimator = new ComplexityEstimator();
    }

    @Test
    void testLinearComplexityDetection() {
        String linearCode = """
            public int[] solve(int[] nums, int target) {
                Map<Integer, Integer> map = new HashMap<>();
                for (int i = 0; i < nums.length; i++) {
                    int complement = target - nums[i];
                    if (map.containsKey(complement)) return new int[]{map.get(complement), i};
                    map.put(nums[i], i);
                }
                return new int[]{};
            }
            """;
        ComplexityEstimator.ComplexityAnalysis analysis = estimator.estimateComplexity(linearCode, "JAVA");
        assertEquals("O(n)", analysis.getEstimatedTimeComplexity());
        assertEquals("O(n)", analysis.getEstimatedSpaceComplexity());
    }

    @Test
    void testQuadraticComplexityDetection() {
        String quadraticCode = """
            public int[] solve(int[] nums, int target) {
                for (int i = 0; i < nums.length; i++) {
                    for (int j = i + 1; j < nums.length; j++) {
                        if (nums[i] + nums[j] == target) return new int[]{i, j};
                    }
                }
                return new int[]{};
            }
            """;
        ComplexityEstimator.ComplexityAnalysis analysis = estimator.estimateComplexity(quadraticCode, "JAVA");
        assertEquals("O(n²)", analysis.getEstimatedTimeComplexity());
        assertFalse(analysis.getSuggestions().isEmpty(), "Suggestions should advise optimizing nested loops");
    }
}
