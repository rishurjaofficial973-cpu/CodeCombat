package com.codeduel;

import com.codeduel.judge.ExecutionSandbox;
import com.codeduel.model.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutionSandboxTest {

    private final ExecutionSandbox sandbox = new ExecutionSandbox();

    @Test
    public void testJavaExecution() {
        String javaCode = """
import java.util.*;

public class Solution {
    public int[] solve(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int comp = target - nums[i];
            if (map.containsKey(comp)) {
                return new int[]{map.get(comp), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
""";
        ExecutionSandbox.ExecutionResult result = sandbox.execute(Language.JAVA, javaCode, "4\n2 7 11 15\n9", 3000, 256);
        assertFalse(result.isCompilationError(), "Compilation error: " + result.getError());
        assertFalse(result.isTimedOut(), "Timed out");
        assertEquals("0 1", result.getOutput().trim());
    }

    @Test
    public void testPythonExecution() {
        String pythonCode = """
class Solution:
    def solve(self, nums: list[int], target: int) -> list[int]:
        seen = {}
        for i, num in enumerate(nums):
            comp = target - num
            if comp in seen:
                return [seen[comp], i]
            seen[num] = i
        return []
""";
        ExecutionSandbox.ExecutionResult result = sandbox.execute(Language.PYTHON, pythonCode, "4\n2 7 11 15\n9", 3000, 256);
        assertFalse(result.isCompilationError(), "Compilation error: " + result.getError());
        assertFalse(result.isTimedOut(), "Timed out");
        assertEquals("0 1", result.getOutput().trim());
    }
}
