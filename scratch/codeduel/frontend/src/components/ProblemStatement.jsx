import React, { useState } from 'react';
import { ExternalLink, BookOpen, Lightbulb, FileText, Code2, Copy, Check, ChevronDown, ChevronRight, Sparkles, Tag } from 'lucide-react';

/**
 * Custom Rich Text Renderer:
 * Converts raw markdown artifacts (**word**, `code`, ### heading, $O(n)$) into
 * beautifully styled UI elements with vibrant orange, light-blue, and emerald highlights.
 */
const FormattedContent = ({ text }) => {
  if (!text) return null;

  const lines = text.split('\n');

  const parseInline = (line) => {
    const parts = [];
    let remaining = line;
    let keyIdx = 0;

    while (remaining.length > 0) {
      // Check for code `...`
      const codeMatch = remaining.match(/^`([^`]+)`/);
      if (codeMatch) {
        parts.push(
          <span
            key={keyIdx++}
            className="text-cyan-300 font-mono bg-cyan-500/15 px-1.5 py-0.5 rounded border border-cyan-500/25 text-xs inline-block mx-0.5"
          >
            {codeMatch[1]}
          </span>
        );
        remaining = remaining.slice(codeMatch[0].length);
        continue;
      }

      // Check for bold **...**
      const boldMatch = remaining.match(/^\*\*([^*]+)\*\*/);
      if (boldMatch) {
        parts.push(
          <span
            key={keyIdx++}
            className="text-amber-400 font-semibold bg-amber-500/10 px-1.5 py-0.5 rounded border border-amber-500/20 mx-0.5 inline-block"
          >
            {boldMatch[1]}
          </span>
        );
        remaining = remaining.slice(boldMatch[0].length);
        continue;
      }

      // Check for math $...$
      const mathMatch = remaining.match(/^\$([^$]+)\$/);
      if (mathMatch) {
        parts.push(
          <span
            key={keyIdx++}
            className="text-emerald-300 font-mono bg-emerald-500/10 px-1.5 py-0.5 rounded border border-emerald-500/20 text-xs inline-block mx-0.5 font-bold"
          >
            {mathMatch[1]}
          </span>
        );
        remaining = remaining.slice(mathMatch[0].length);
        continue;
      }

      // Find next occurrence of `, *, or $
      const nextSpecial = remaining.search(/[`*$]/);
      if (nextSpecial === -1) {
        parts.push(<span key={keyIdx++}>{remaining}</span>);
        break;
      } else if (nextSpecial === 0) {
        parts.push(<span key={keyIdx++}>{remaining[0]}</span>);
        remaining = remaining.slice(1);
      } else {
        parts.push(<span key={keyIdx++}>{remaining.slice(0, nextSpecial)}</span>);
        remaining = remaining.slice(nextSpecial);
      }
    }

    return parts;
  };

  return (
    <div className="space-y-3">
      {lines.map((line, idx) => {
        const trimmed = line.trim();
        if (!trimmed) return <div key={idx} className="h-1" />;

        // Header 3: ### Heading (Special badges for interview approaches)
        if (trimmed.startsWith('### ')) {
          const headerText = trimmed.slice(4);

          let headerBadgeStyle = 'text-white border-dark-600 bg-dark-800';
          if (headerText.includes('Approach 1') || headerText.includes('Brute Force')) {
            headerBadgeStyle = 'text-rose-400 border-rose-500/30 bg-rose-500/10';
          } else if (headerText.includes('Approach 2') || headerText.includes('Better')) {
            headerBadgeStyle = 'text-amber-400 border-amber-500/30 bg-amber-500/10';
          } else if (headerText.includes('Approach 3') || headerText.includes('Most Optimal')) {
            headerBadgeStyle = 'text-emerald-400 border-emerald-500/30 bg-emerald-500/10';
          } else if (headerText.includes('Pattern Recognition') || headerText.includes('Intuition')) {
            headerBadgeStyle = 'text-cyan-400 border-cyan-500/30 bg-cyan-500/10';
          } else if (headerText.includes('Interview Pitfalls') || headerText.includes('Follow-ups')) {
            headerBadgeStyle = 'text-purple-400 border-purple-500/30 bg-purple-500/10';
          }

          return (
            <div key={idx} className="mt-5 mb-2">
              <h3 className={`text-xs uppercase font-bold tracking-wider px-3 py-1.5 rounded-lg border inline-flex items-center gap-2 ${headerBadgeStyle}`}>
                <span>{headerText}</span>
              </h3>
            </div>
          );
        }

        // Header 2: ## Heading
        if (trimmed.startsWith('## ')) {
          return (
            <h2 key={idx} className="text-sm font-bold text-cyan-300 mt-4 mb-2 tracking-wide flex items-center gap-2">
              <span className="w-1.5 h-1.5 rounded-full bg-cyan-400"></span>
              <span>{trimmed.slice(3)}</span>
            </h2>
          );
        }

        // Header 1: # Heading
        if (trimmed.startsWith('# ')) {
          return (
            <h1 key={idx} className="text-base font-bold text-white mt-4 mb-2">
              {trimmed.slice(2)}
            </h1>
          );
        }

        // Bullet point: - ...
        if (trimmed.startsWith('- ')) {
          return (
            <div key={idx} className="flex items-start space-x-2 pl-2">
              <span className="text-cyan-400 font-bold leading-relaxed text-xs">•</span>
              <div className="text-slate-300 text-xs leading-relaxed flex-1">
                {parseInline(trimmed.slice(2))}
              </div>
            </div>
          );
        }

        return (
          <p key={idx} className="text-slate-300 text-xs leading-relaxed">
            {parseInline(line)}
          </p>
        );
      })}
    </div>
  );
};

export const ProblemStatement = ({ problem }) => {
  const [activeTab, setActiveTab] = useState('DESCRIPTION');
  const [solutionLanguage, setSolutionLanguage] = useState(() => {
    const pref = localStorage.getItem('codeduel_preferred_language') || 'JAVA';
    return pref === 'CPP' ? 'CPP' : 'JAVA';
  });
  const [revealedHints, setRevealedHints] = useState({});
  const [copied, setCopied] = useState(false);

  if (!problem) {
    return (
      <div className="p-6 text-center text-slate-500 font-mono text-xs">
        Loading problem statement...
      </div>
    );
  }

  let examplesList = [];
  try {
    if (problem.examples) {
      examplesList = typeof problem.examples === 'string' ? JSON.parse(problem.examples) : problem.examples;
    }
  } catch (e) {
    examplesList = [];
  }

  let hintsList = [];
  try {
    if (problem.hints) {
      hintsList = typeof problem.hints === 'string' ? JSON.parse(problem.hints) : problem.hints;
    }
  } catch (e) {
    hintsList = [];
  }

  const toggleHint = (idx) => {
    setRevealedHints(prev => ({ ...prev, [idx]: !prev[idx] }));
  };

  const copyCode = (codeText) => {
    navigator.clipboard.writeText(codeText);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const getDifficultyColor = (diff) => {
    switch (diff) {
      case 'EASY':
        return 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30';
      case 'MEDIUM':
        return 'bg-amber-500/10 text-amber-400 border-amber-500/30';
      case 'HARD':
        return 'bg-rose-500/10 text-rose-400 border-rose-500/30';
      default:
        return 'bg-slate-800 text-slate-300 border-slate-700';
    }
  };

  // Specific, interview-grade reference solutions for Java and C++
  const getJavaSolution = () => {
    if (problem.title === 'Set Matrix Zeroes') {
      return `import java.util.*;

public class Solution {
    /**
     * Pattern: Matrix In-Place Modification, State Flagging Variables
     * Time Complexity: O(m * n) | Space Complexity: O(1)
     * 
     * Interview Strategy:
     * Use matrix[0][..] and matrix[..][0] as in-place zero flags.
     * Maintain a separate boolean col0 for the first column.
     */
    public int[][] solve(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean col0 = false;
        
        for (int r = 0; r < m; r++) {
            if (matrix[r][0] == 0) col0 = true;
            for (int c = 1; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }
        
        // Update inner matrix
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 1; c--) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                    matrix[r][c] = 0;
                }
            }
            if (col0) matrix[r][0] = 0;
        }
        return matrix;
    }
}`;
    }

    if (problem.title === 'Two Sum') {
      return `import java.util.*;

public class Solution {
    /**
     * Pattern: Hash Map & Complement Lookup
     * Time Complexity: O(n) | Space Complexity: O(n)
     * 
     * Interview Strategy:
     * As we traverse nums, we store each element alongside its index.
     * For each num, we check if complement (target - num) exists in map.
     */
    public int[] solve(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}`;
    }

    if (problem.title === 'Contains Duplicate') {
      return `import java.util.*;

public class Solution {
    /**
     * Pattern: Hash Set Frequency Tracking
     * Time Complexity: O(n) | Space Complexity: O(n)
     * 
     * Interview Strategy:
     * Maintain a HashSet of visited elements. Return true on first collision.
     */
    public boolean solve(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true; // Duplicate detected in O(1)
            }
        }
        return false;
    }
}`;
    }

    if (problem.title === 'Best Time to Buy and Sell Stock') {
      return `public class Solution {
    /**
     * Pattern: Kadane's Algorithm / One-Pass Min Tracking
     * Time Complexity: O(n) | Space Complexity: O(1)
     * 
     * Interview Strategy:
     * Track minPrice seen so far, update maxProfit whenever (currentPrice - minPrice) exceeds it.
     */
    public int solve(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;
    }
}`;
    }

    if (problem.title === 'Binary Search') {
      return `public class Solution {
    /**
     * Pattern: Binary Search / Search Space Halving
     * Time Complexity: O(log n) | Space Complexity: O(1)
     * 
     * Interview Strategy:
     * Use mid = left + (right - left) / 2 to avoid integer overflow.
     */
    public int solve(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}`;
    }

    if (problem.title === 'Climbing Stairs') {
      return `public class Solution {
    /**
     * Pattern: Dynamic Programming / Space-Optimized Fibonacci
     * Time Complexity: O(n) | Space Complexity: O(1)
     */
    public int solve(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
}`;
    }

    // Default template tailored to problem signature
    return `import java.util.*;

public class Solution {
    /**
     * Pattern: ${problem.patterns || 'Optimal Algorithm'}
     * Expected Time Complexity: O(${problem.expectedTimeComplexity || 'n'})
     * Expected Space Complexity: O(${problem.expectedSpaceComplexity || '1'})
     */
    public int solve(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = Math.max(result, num);
        }
        return result;
    }
}`;
  };

  const getCppSolution = () => {
    if (problem.title === 'Set Matrix Zeroes') {
      return `#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    /**
     * Pattern: Matrix In-Place Modification, State Flagging Variables
     * Time Complexity: O(m * n) | Space Complexity: O(1)
     */
    vector<vector<int>> solve(vector<vector<int>>& matrix) {
        int m = matrix.size(), n = matrix[0].size();
        bool col0 = false;
        
        for (int r = 0; r < m; r++) {
            if (matrix[r][0] == 0) col0 = true;
            for (int c = 1; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }
        
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 1; c--) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                    matrix[r][c] = 0;
                }
            }
            if (col0) matrix[r][0] = 0;
        }
        return matrix;
    }
};`;
    }

    if (problem.title === 'Two Sum') {
      return `#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

class Solution {
public:
    /**
     * Pattern: Hash Map & Complement Lookup
     * Time Complexity: O(n) | Space Complexity: O(n)
     */
    vector<int> solve(vector<int>& nums, int target) {
        unordered_map<int, int> map;
        for (int i = 0; i < nums.size(); i++) {
            int complement = target - nums[i];
            if (map.count(complement)) {
                return {map[complement], i};
            }
            map[nums[i]] = i;
        }
        return {};
    }
};`;
    }

    if (problem.title === 'Contains Duplicate') {
      return `#include <iostream>
#include <vector>
#include <unordered_set>

using namespace std;

class Solution {
public:
    /**
     * Pattern: Hash Set Frequency Tracking
     * Time Complexity: O(n) | Space Complexity: O(n)
     */
    bool solve(vector<int>& nums) {
        unordered_set<int> seen;
        for (int num : nums) {
            if (seen.count(num)) {
                return true;
            }
            seen.insert(num);
        }
        return false;
    }
};`;
    }

    if (problem.title === 'Best Time to Buy and Sell Stock') {
      return `#include <iostream>
#include <vector>
#include <climits>
#include <algorithm>

using namespace std;

class Solution {
public:
    /**
     * Pattern: Kadane's Algorithm / One-Pass Min Tracking
     * Time Complexity: O(n) | Space Complexity: O(1)
     */
    int solve(vector<int>& prices) {
        int minPrice = INT_MAX;
        int maxProfit = 0;
        for (int price : prices) {
            minPrice = min(minPrice, price);
            maxProfit = max(maxProfit, price - minPrice);
        }
        return maxProfit;
    }
};`;
    }

    if (problem.title === 'Binary Search') {
      return `#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    /**
     * Pattern: Binary Search / Search Space Halving
     * Time Complexity: O(log n) | Space Complexity: O(1)
     */
    int solve(vector<int>& nums, int target) {
        int left = 0, right = nums.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
};`;
    }

    if (problem.title === 'Climbing Stairs') {
      return `#include <iostream>

using namespace std;

class Solution {
public:
    /**
     * Pattern: Dynamic Programming / Space-Optimized Fibonacci
     * Time Complexity: O(n) | Space Complexity: O(1)
     */
    int solve(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
};`;
    }

    return `#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    /**
     * Pattern: ${problem.patterns || 'Optimal Algorithm'}
     * Expected Time Complexity: O(${problem.expectedTimeComplexity || 'n'})
     * Expected Space Complexity: O(${problem.expectedSpaceComplexity || '1'})
     */
    int solve(vector<int>& nums) {
        int ans = 0;
        for (int x : nums) {
            ans = max(ans, x);
        }
        return ans;
    }
};`;
  };

  return (
    <div className="h-full flex flex-col bg-dark-900 border border-dark-700 rounded-2xl overflow-hidden shadow-2xl">
      {/* LeetCode Multi-Tab Navigation Bar */}
      <div className="flex items-center space-x-1 px-3 py-2 bg-dark-800 border-b border-dark-700 overflow-x-auto scrollbar-none flex-shrink-0">
        <button
          onClick={() => setActiveTab('DESCRIPTION')}
          className={`flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition-colors ${
            activeTab === 'DESCRIPTION'
              ? 'bg-dark-700 text-cyan-400 font-bold border border-dark-500 shadow-sm'
              : 'text-slate-400 hover:text-slate-200 hover:bg-dark-800'
          }`}
        >
          <FileText className="w-3.5 h-3.5" />
          <span>Description</span>
        </button>

        <button
          onClick={() => setActiveTab('EDITORIAL')}
          className={`flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition-colors ${
            activeTab === 'EDITORIAL'
              ? 'bg-dark-700 text-indigo-400 font-bold border border-dark-500 shadow-sm'
              : 'text-slate-400 hover:text-slate-200 hover:bg-dark-800'
          }`}
        >
          <BookOpen className="w-3.5 h-3.5" />
          <span>Editorial (3 Approaches)</span>
        </button>

        <button
          onClick={() => setActiveTab('SOLUTIONS')}
          className={`flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition-colors ${
            activeTab === 'SOLUTIONS'
              ? 'bg-dark-700 text-emerald-400 font-bold border border-dark-500 shadow-sm'
              : 'text-slate-400 hover:text-slate-200 hover:bg-dark-800'
          }`}
        >
          <Code2 className="w-3.5 h-3.5" />
          <span>Solutions (Java / C++)</span>
        </button>

        {hintsList.length > 0 && (
          <button
            onClick={() => setActiveTab('HINTS')}
            className={`flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition-colors ${
              activeTab === 'HINTS'
                ? 'bg-dark-700 text-amber-400 font-bold border border-dark-500 shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-dark-800'
            }`}
          >
            <Lightbulb className="w-3.5 h-3.5" />
            <span>Hints ({hintsList.length})</span>
          </button>
        )}
      </div>

      {/* Tab 1: Problem Description */}
      {activeTab === 'DESCRIPTION' && (
        <div className="flex-1 overflow-y-auto p-5 space-y-6 text-slate-200 text-xs leading-relaxed">
          {/* Header */}
          <div>
            <div className="flex items-center space-x-2 text-xs font-mono text-slate-400 mb-1">
              <span className="font-bold text-cyan-400">{problem.id}</span>
              <span>•</span>
              <span className="text-slate-400">{problem.source || 'LeetCode'}</span>
            </div>
            <h1 className="text-xl font-bold text-white tracking-tight">
              {problem.title}
            </h1>

            {/* Badges & Interview Pattern Highlight */}
            <div className="flex flex-wrap items-center gap-2 mt-3">
              <span className={`px-2.5 py-0.5 rounded-full text-xs font-semibold border ${getDifficultyColor(problem.difficulty)}`}>
                {problem.difficulty}
              </span>

              {problem.patterns && (
                <span className="flex items-center gap-1 text-[11px] px-2.5 py-0.5 rounded-full bg-indigo-500/15 text-indigo-300 border border-indigo-500/30 font-bold">
                  <Tag className="w-3 h-3 text-indigo-400" />
                  <span>{problem.patterns}</span>
                </span>
              )}

              {problem.topics && problem.topics.split(',').map((t, idx) => (
                <span key={idx} className="text-[11px] px-2.5 py-0.5 rounded-full bg-dark-800 text-slate-300 border border-dark-700 font-medium">
                  {t.trim()}
                </span>
              ))}

              {problem.externalUrl && (
                <a
                  href={problem.externalUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center gap-1 px-2.5 py-0.5 rounded-full text-xs text-slate-400 hover:text-cyan-300 bg-dark-800 hover:bg-dark-700 border border-dark-600 transition-colors ml-auto"
                >
                  <span>LeetCode Link</span>
                  <ExternalLink className="w-3 h-3" />
                </a>
              )}
            </div>
          </div>

          {/* Clean Rendered Problem Statement */}
          <div className="bg-dark-800/40 p-4 rounded-xl border border-dark-700/60">
            <FormattedContent text={problem.description} />
          </div>

          {/* Examples */}
          {examplesList && examplesList.length > 0 && (
            <div className="space-y-3">
              <h3 className="font-bold text-white text-xs uppercase tracking-wider font-mono flex items-center gap-2">
                <span className="w-1.5 h-1.5 rounded-full bg-cyan-400"></span>
                <span>Examples</span>
              </h3>
              {examplesList.map((ex, idx) => (
                <div key={idx} className="bg-dark-800/90 rounded-xl p-3.5 border border-dark-700 space-y-2 font-mono text-xs">
                  <div className="font-bold text-slate-400 font-sans">Example {idx + 1}:</div>
                  <div>
                    <span className="text-slate-400 font-semibold font-sans">Input: </span>
                    <code className="text-cyan-300 font-bold">{ex.input}</code>
                  </div>
                  <div>
                    <span className="text-slate-400 font-semibold font-sans">Output: </span>
                    <code className="text-emerald-400 font-bold">{ex.output}</code>
                  </div>
                  {ex.explanation && (
                    <div className="text-slate-400 text-xs font-sans pt-1 border-t border-dark-700/60">
                      <span className="font-semibold text-slate-300">Explanation: </span>
                      {ex.explanation}
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}

          {/* Constraints */}
          {problem.constraints && (
            <div className="space-y-2">
              <h3 className="font-bold text-white text-xs uppercase tracking-wider font-mono flex items-center gap-2">
                <span className="w-1.5 h-1.5 rounded-full bg-amber-400"></span>
                <span>Constraints</span>
              </h3>
              <div className="bg-dark-800/60 rounded-xl p-3.5 border border-dark-700">
                <FormattedContent text={problem.constraints} />
              </div>
            </div>
          )}
        </div>
      )}

      {/* Tab 2: 3-Tier Editorial */}
      {activeTab === 'EDITORIAL' && (
        <div className="flex-1 overflow-y-auto p-5 space-y-4 text-slate-200 text-xs leading-relaxed">
          <div className="flex items-center space-x-2 text-indigo-400 font-bold font-mono text-xs">
            <BookOpen className="w-4 h-4" />
            <span>Interview Editorial & 3-Tier Algorithmic Approaches</span>
          </div>

          <div className="bg-dark-800/80 rounded-xl p-4 border border-dark-700">
            <FormattedContent text={problem.editorial || problem.solutionExplanation || 'Editorial blueprint is being prepared.'} />
          </div>
        </div>
      )}

      {/* Tab 3: Solutions (Java & C++) */}
      {activeTab === 'SOLUTIONS' && (
        <div className="flex-1 overflow-y-auto p-5 space-y-4 text-slate-200 text-xs leading-relaxed">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-2 text-emerald-400 font-bold font-mono text-xs">
              <Code2 className="w-4 h-4" />
              <span>Interview Reference Solution</span>
            </div>

            {/* Language Toggle: Java vs C++ */}
            <div className="flex items-center space-x-1 bg-dark-800 p-1 rounded-lg border border-dark-600">
              <button
                onClick={() => {
                  setSolutionLanguage('JAVA');
                  localStorage.setItem('codeduel_preferred_language', 'JAVA');
                }}
                className={`px-3 py-1 rounded text-xs font-bold font-mono transition-colors ${
                  solutionLanguage === 'JAVA'
                    ? 'bg-indigo-600 text-white shadow-sm'
                    : 'text-slate-400 hover:text-white'
                }`}
              >
                ☕ Java
              </button>
              <button
                onClick={() => {
                  setSolutionLanguage('CPP');
                  localStorage.setItem('codeduel_preferred_language', 'CPP');
                }}
                className={`px-3 py-1 rounded text-xs font-bold font-mono transition-colors ${
                  solutionLanguage === 'CPP'
                    ? 'bg-cyan-600 text-white shadow-sm'
                    : 'text-slate-400 hover:text-white'
                }`}
              >
                ⚡ C++
              </button>
            </div>
          </div>

          {/* Solution Code Card */}
          <div className="bg-dark-800 rounded-xl border border-dark-700 overflow-hidden shadow-lg">
            <div className="flex items-center justify-between px-4 py-2.5 bg-dark-800/90 border-b border-dark-700">
              <div className="font-mono text-xs font-bold text-slate-300 flex items-center gap-2">
                <span className="w-2 h-2 rounded-full bg-emerald-400 inline-block"></span>
                <span>{solutionLanguage === 'JAVA' ? 'Solution.java (Optimal O(n))' : 'Solution.cpp (Optimal O(n))'}</span>
              </div>
              <button
                onClick={() => copyCode(solutionLanguage === 'JAVA' ? getJavaSolution() : getCppSolution())}
                className="flex items-center space-x-1 px-2.5 py-1 rounded bg-dark-700 hover:bg-dark-600 text-slate-300 text-xs font-mono transition-colors"
              >
                {copied ? <Check className="w-3.5 h-3.5 text-emerald-400" /> : <Copy className="w-3.5 h-3.5" />}
                <span>{copied ? 'Copied!' : 'Copy'}</span>
              </button>
            </div>

            <pre className="p-4 bg-dark-900 font-mono text-xs text-slate-200 overflow-x-auto leading-relaxed">
              {solutionLanguage === 'JAVA' ? getJavaSolution() : getCppSolution()}
            </pre>
          </div>
        </div>
      )}

      {/* Tab 4: Hints */}
      {activeTab === 'HINTS' && (
        <div className="flex-1 overflow-y-auto p-5 space-y-3 text-slate-200 text-xs leading-relaxed">
          <div className="flex items-center space-x-2 text-amber-400 font-bold font-mono text-xs mb-2">
            <Lightbulb className="w-4 h-4" />
            <span>Progressive Hints (Click to reveal)</span>
          </div>

          {hintsList.map((hint, idx) => (
            <div key={idx} className="bg-dark-800 rounded-xl border border-dark-700 overflow-hidden">
              <button
                onClick={() => toggleHint(idx)}
                className="w-full px-4 py-3 text-left font-semibold text-xs text-slate-300 hover:text-white flex items-center justify-between"
              >
                <span>Hint {idx + 1}</span>
                {revealedHints[idx] ? <ChevronDown className="w-4 h-4" /> : <ChevronRight className="w-4 h-4" />}
              </button>
              {revealedHints[idx] && (
                <div className="px-4 py-3 bg-dark-900/60 border-t border-dark-700 text-xs text-slate-300">
                  <FormattedContent text={hint} />
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
