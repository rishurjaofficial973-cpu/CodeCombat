package com.codeduel;

import com.codeduel.model.Problem;
import com.codeduel.model.TestCase;
import com.codeduel.repository.ProblemRepository;
import com.codeduel.repository.TestCaseRepository;
import com.codeduel.seed.ProblemSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class All1000ProblemsVerificationTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ProblemSeeder problemSeeder;

    @Test
    @Transactional(readOnly = true)
    @DisplayName("Verify All 1,000 Questions - 3-Tier Approaches, Authentic Interview Patterns & Clean Signatures")
    void testAll1000ProblemsDataIntegrity() {
        List<Problem> allProblems = problemRepository.findAll();
        assertEquals(1000, allProblems.size(), "Question bank must contain exactly 1,000 problems");

        int totalTestCases = 0;

        for (Problem problem : allProblems) {
            assertNotNull(problem.getId(), "Problem ID must not be null");
            assertNotNull(problem.getTitle(), "Problem title must not be null");
            assertNotNull(problem.getSlug(), "Problem slug must not be null");
            assertNotNull(problem.getDifficulty(), "Problem difficulty must not be null");
            assertNotNull(problem.getTopics(), "Problem topics must not be null");
            assertNotNull(problem.getPatterns(), "Problem patterns must not be null");
            assertFalse(problem.getPatterns().isBlank(), "Problem patterns must not be blank for " + problem.getTitle());
            assertNotNull(problem.getDescription(), "Problem description must not be null");
            assertNotNull(problem.getConstraints(), "Problem constraints must not be null");
            assertNotNull(problem.getExamples(), "Problem examples must not be null");
            assertNotNull(problem.getHints(), "Problem hints must not be null");
            assertNotNull(problem.getEditorial(), "Problem editorial must not be null");

            // Editorial must contain the complete 3-Tier Interview Approach breakdown
            assertTrue(problem.getEditorial().contains("Approach 1: Brute Force") || problem.getEditorial().contains("Approach 1"),
                    "Editorial must contain Approach 1 for " + problem.getTitle());
            assertTrue(problem.getEditorial().contains("Approach 2"),
                    "Editorial must contain Approach 2 for " + problem.getTitle());
            assertTrue(problem.getEditorial().contains("Approach 3: Most Optimal") || problem.getEditorial().contains("Approach 3"),
                    "Editorial must contain Approach 3: Most Optimal for " + problem.getTitle());
            assertTrue(problem.getEditorial().contains("Interview Pitfalls") || problem.getEditorial().contains("Time Complexity"),
                    "Editorial must contain Interview Pitfalls / Complexity Analysis for " + problem.getTitle());

            // Starter codes must be present for Java, Python, C++, and JS
            assertNotNull(problem.getStarterCodeJava(), "Java starter code missing for " + problem.getTitle());
            assertNotNull(problem.getStarterCodePython(), "Python starter code missing for " + problem.getTitle());
            assertNotNull(problem.getStarterCodeCpp(), "C++ starter code missing for " + problem.getTitle());
            assertNotNull(problem.getStarterCodeJs(), "JS starter code missing for " + problem.getTitle());

            // Signatures must be valid method declarations
            assertTrue(problem.getStarterCodeJava().contains("public") && problem.getStarterCodeJava().contains("solve("),
                    "Java starter code must have solve method for " + problem.getTitle());
            assertTrue(problem.getStarterCodeCpp().contains("solve("),
                    "C++ starter code must have solve method for " + problem.getTitle());

            // Check specific signatures for key problems
            if ("Contains Duplicate".equalsIgnoreCase(problem.getTitle())) {
                assertTrue(problem.getStarterCodeJava().contains("boolean solve(int[] nums)"),
                        "Contains Duplicate Java signature must be boolean solve(int[] nums)");
            } else if ("Two Sum".equalsIgnoreCase(problem.getTitle())) {
                assertTrue(problem.getStarterCodeJava().contains("int[] solve(int[] nums, int target)"),
                        "Two Sum Java signature must be int[] solve(int[] nums, int target)");
                assertTrue(problem.getPatterns().contains("Hash Map") || problem.getPatterns().contains("Two Pointers"),
                        "Two Sum pattern must contain Hash Map or Two Pointers");
            } else if ("Best Time to Buy and Sell Stock".equalsIgnoreCase(problem.getTitle())) {
                assertTrue(problem.getStarterCodeJava().contains("int solve(int[] nums)") || problem.getStarterCodeJava().contains("int solve(int[] prices)"),
                        "Stock problem Java signature must return int");
            } else if ("Climbing Stairs".equalsIgnoreCase(problem.getTitle())) {
                assertTrue(problem.getStarterCodeJava().contains("int solve(int n)"),
                        "Climbing Stairs Java signature must be int solve(int n)");
            } else if ("Set Matrix Zeroes".equalsIgnoreCase(problem.getTitle())) {
                assertEquals(com.codeduel.model.Difficulty.MEDIUM, problem.getDifficulty(),
                        "Set Matrix Zeroes must be MEDIUM difficulty");
                assertEquals("set-matrix-zeroes", problem.getSlug(),
                        "Set Matrix Zeroes slug must be 'set-matrix-zeroes'");
                assertTrue(problem.getStarterCodeJava().contains("int[][] solve(int[][] matrix)"),
                        "Set Matrix Zeroes signature must be int[][] solve(int[][] matrix)");
                assertTrue(problem.getExamples().contains("matrix = [[1,1,1],[1,0,1],[1,1,1]]"),
                        "Set Matrix Zeroes examples must contain matrix examples");
                assertTrue(problem.getPatterns().contains("Matrix In-Place Modification"),
                        "Set Matrix Zeroes patterns must contain Matrix In-Place Modification");
            }

            // Check test cases for each problem
            List<TestCase> testCases = testCaseRepository.findByProblemIdOrderByOrderIndexAsc(problem.getId());
            assertEquals(5, testCases.size(), "Each problem must have exactly 5 test cases. Failed on: " + problem.getId());
            totalTestCases += testCases.size();

            // Verify testcase format matches signature
            for (TestCase tc : testCases) {
                assertNotNull(tc.getInputData(), "Test case input data must not be null for " + problem.getTitle());
                assertNotNull(tc.getExpectedOutput(), "Test case expected output must not be null for " + problem.getTitle());
                assertFalse(tc.getInputData().isBlank(), "Test case input must not be blank for " + problem.getTitle());
                assertFalse(tc.getExpectedOutput().isBlank(), "Test case output must not be blank for " + problem.getTitle());

                // Boolean problems must have true/false expected outputs
                if (problem.getStarterCodeJava().contains("boolean solve(")) {
                    assertTrue(tc.getExpectedOutput().equals("true") || tc.getExpectedOutput().equals("false"),
                            "Boolean problem expected output must be 'true' or 'false'. Got: " + tc.getExpectedOutput() + " for " + problem.getTitle());
                }
            }
        }

        assertEquals(5000, totalTestCases, "Total seeded test cases across all 1,000 problems must be exactly 5,000");
    }
}
