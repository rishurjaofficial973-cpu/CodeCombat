package com.codeduel.judge;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ComplexityEstimator {

    public static class ComplexityAnalysis {
        private String estimatedTimeComplexity = "O(n)";
        private String estimatedSpaceComplexity = "O(1)";
        private List<String> suggestions = new ArrayList<>();

        public String getEstimatedTimeComplexity() { return estimatedTimeComplexity; }
        public void setEstimatedTimeComplexity(String estimatedTimeComplexity) { this.estimatedTimeComplexity = estimatedTimeComplexity; }

        public String getEstimatedSpaceComplexity() { return estimatedSpaceComplexity; }
        public void setEstimatedSpaceComplexity(String estimatedSpaceComplexity) { this.estimatedSpaceComplexity = estimatedSpaceComplexity; }

        public List<String> getSuggestions() { return suggestions; }
        public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
    }

    public ComplexityAnalysis estimateComplexity(String sourceCode, String language) {
        ComplexityAnalysis analysis = new ComplexityAnalysis();
        if (sourceCode == null || sourceCode.isBlank()) {
            return analysis;
        }

        int loopDepth = calculateMaxLoopNesting(sourceCode);
        boolean hasRecursion = checkForRecursion(sourceCode);
        boolean hasSorting = checkForSorting(sourceCode);
        boolean hasHashTable = checkForDataStructures(sourceCode);

        // Time Complexity Estimation
        if (loopDepth >= 3) {
            analysis.setEstimatedTimeComplexity("O(n³)");
            analysis.getSuggestions().add("Deeply nested loops (O(n³)) detected. Consider using Hash Maps or Prefix Sums to reduce nesting.");
        } else if (loopDepth == 2) {
            analysis.setEstimatedTimeComplexity("O(n²)");
            analysis.getSuggestions().add("Quadratic complexity O(n²) detected. Check if Two Pointers, Sliding Window, or Hash Lookup can optimize to O(n).");
        } else if (hasSorting) {
            analysis.setEstimatedTimeComplexity("O(n log n)");
            analysis.getSuggestions().add("Sorting operation detected (O(n log n)). Ensure this is required for the optimal approach.");
        } else if (loopDepth == 1 || hasRecursion) {
            analysis.setEstimatedTimeComplexity("O(n)");
            analysis.getSuggestions().add("Linear time complexity O(n) looks clean and optimal for this problem.");
        } else {
            analysis.setEstimatedTimeComplexity("O(log n)");
        }

        // Space Complexity Estimation
        if (hasHashTable || sourceCode.contains("new int[") || sourceCode.contains("vector<") || sourceCode.contains("list(")) {
            analysis.setEstimatedSpaceComplexity("O(n)");
        } else {
            analysis.setEstimatedSpaceComplexity("O(1)");
        }

        return analysis;
    }

    private int calculateMaxLoopNesting(String code) {
        int maxDepth = 0;
        int currentDepth = 0;
        Pattern loopPattern = Pattern.compile("\\b(for|while)\\b");

        String[] lines = code.split("\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("//") || trimmed.startsWith("#")) continue;

            Matcher matcher = loopPattern.matcher(trimmed);
            while (matcher.find()) {
                currentDepth++;
                maxDepth = Math.max(maxDepth, currentDepth);
            }

            if (trimmed.contains("}") && currentDepth > 0) {
                currentDepth--;
            }
        }
        return maxDepth;
    }

    private boolean checkForRecursion(String code) {
        return code.contains("solve(") || code.contains("dfs(") || code.contains("helper(") || code.contains("backtrack(");
    }

    private boolean checkForSorting(String code) {
        return code.contains("Arrays.sort") || code.contains("Collections.sort") || code.contains(".sort(") || code.contains("sorted(");
    }

    private boolean checkForDataStructures(String code) {
        return code.contains("HashMap") || code.contains("HashSet") || code.contains("unordered_map") || code.contains("Map()") || code.contains("Set()") || code.contains("seen =");
    }
}
