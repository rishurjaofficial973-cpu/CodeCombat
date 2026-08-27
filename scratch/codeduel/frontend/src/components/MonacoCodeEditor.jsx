import React, { useState, useEffect, useRef } from 'react';
import Editor from '@monaco-editor/react';
import { Play, Send, RotateCcw, ChevronDown } from 'lucide-react';

const LANGUAGE_CONFIGS = {
  JAVA: { name: 'Java (OpenJDK 17)', monacoLang: 'java' },
  CPP: { name: 'C++ (G++ 17)', monacoLang: 'cpp' },
  PYTHON: { name: 'Python 3 (3.11)', monacoLang: 'python' },
  JAVASCRIPT: { name: 'JavaScript (Node 20)', monacoLang: 'javascript' }
};

export const MonacoCodeEditor = ({
  problemId,
  starterCodes,
  onSubmit,
  onRun,
  onTyping,
  submitting = false,
  running = false
}) => {
  // Load persisted user language preference (defaults to JAVA)
  const [selectedLanguage, setSelectedLanguage] = useState(() => {
    return localStorage.getItem('codeduel_preferred_language') || 'JAVA';
  });

  const [code, setCode] = useState('');
  const [savedStatus, setSavedStatus] = useState('Saved');
  const typingTimerRef = useRef(null);

  // Load code from draft or starter code when language or problem changes
  useEffect(() => {
    if (!problemId) return;

    const draftKey = `codeduel_draft_${problemId}_${selectedLanguage}`;
    const savedDraft = localStorage.getItem(draftKey);

    if (savedDraft) {
      setCode(savedDraft);
    } else if (starterCodes && starterCodes[selectedLanguage]) {
      setCode(starterCodes[selectedLanguage]);
    } else {
      if (selectedLanguage === 'JAVA') {
        setCode('import java.util.*;\n\npublic class Solution {\n    public boolean solve(int[] nums) {\n        // Write your logic here\n        return false;\n    }\n}\n');
      } else if (selectedLanguage === 'CPP') {
        setCode('#include <iostream>\n#include <vector>\nusing namespace std;\n\nclass Solution {\npublic:\n    bool solve(vector<int>& nums) {\n        // Write your logic here\n        return false;\n    }\n};\n');
      } else if (selectedLanguage === 'PYTHON') {
        setCode('class Solution:\n    def solve(self, nums: list[int]) -> bool:\n        # Write your logic here\n        pass\n');
      } else {
        setCode('function solve(nums) {\n    // Write your logic here\n    return false;\n}\n');
      }
    }
  }, [problemId, selectedLanguage, starterCodes]);

  const handleLanguageChange = (newLang) => {
    setSelectedLanguage(newLang);
    // Persist language choice across all problems and duels
    localStorage.setItem('codeduel_preferred_language', newLang);

    const draftKey = `codeduel_draft_${problemId}_${newLang}`;
    const savedDraft = localStorage.getItem(draftKey);
    if (savedDraft) {
      setCode(savedDraft);
    } else if (starterCodes && starterCodes[newLang]) {
      setCode(starterCodes[newLang]);
    } else {
      if (newLang === 'JAVA') {
        setCode('import java.util.*;\n\npublic class Solution {\n    public boolean solve(int[] nums) {\n        // Write your logic here\n        return false;\n    }\n}\n');
      } else if (newLang === 'CPP') {
        setCode('#include <iostream>\n#include <vector>\nusing namespace std;\n\nclass Solution {\npublic:\n    bool solve(vector<int>& nums) {\n        // Write your logic here\n        return false;\n    }\n};\n');
      } else if (newLang === 'PYTHON') {
        setCode('class Solution:\n    def solve(self, nums: list[int]) -> bool:\n        # Write your logic here\n        pass\n');
      } else {
        setCode('function solve(nums) {\n    // Write your logic here\n    return false;\n}\n');
      }
    }
  };

  const handleEditorChange = (value) => {
    setCode(value || '');
    setSavedStatus('Saving...');

    if (problemId) {
      const draftKey = `codeduel_draft_${problemId}_${selectedLanguage}`;
      localStorage.setItem(draftKey, value || '');
      setTimeout(() => setSavedStatus('Saved'), 400);
    }

    if (onTyping) {
      if (typingTimerRef.current) clearTimeout(typingTimerRef.current);
      typingTimerRef.current = setTimeout(() => {
        onTyping();
      }, 400);
    }
  };

  const handleResetCode = () => {
    if (window.confirm('Reset code to initial template? Your current draft will be overwritten.')) {
      const defaultCode = starterCodes && starterCodes[selectedLanguage] ? starterCodes[selectedLanguage] : '';
      setCode(defaultCode);
      const draftKey = `codeduel_draft_${problemId}_${selectedLanguage}`;
      localStorage.removeItem(draftKey);
      setSavedStatus('Reset');
    }
  };

  const handleKeyDown = (e) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
      e.preventDefault();
      if (!submitting && !running && onSubmit) {
        onSubmit(selectedLanguage, code);
      }
    }
  };

  return (
    <div className="flex flex-col h-full bg-dark-900 border border-dark-700 rounded-2xl overflow-hidden shadow-2xl" onKeyDown={handleKeyDown}>
      {/* Editor Header Bar */}
      <div className="flex items-center justify-between px-3 sm:px-4 py-2 bg-dark-800 border-b border-dark-700 flex-shrink-0 gap-2">
        <div className="flex items-center space-x-2 min-w-0">
          {/* Language Selector with Persistence */}
          <div className="relative flex-shrink-0">
            <select
              value={selectedLanguage}
              onChange={(e) => handleLanguageChange(e.target.value)}
              className="bg-dark-900 text-xs font-bold text-cyan-300 border border-dark-600 rounded-lg px-2.5 py-1.5 pr-7 appearance-none focus:outline-none focus:border-cyan-500 cursor-pointer shadow-sm"
            >
              {Object.entries(LANGUAGE_CONFIGS).map(([key, config]) => (
                <option key={key} value={key} className="bg-dark-900 text-white">
                  {config.name}
                </option>
              ))}
            </select>
            <ChevronDown className="w-3.5 h-3.5 text-slate-400 absolute right-2 top-2.5 pointer-events-none" />
          </div>

          <span className="text-[11px] font-mono text-slate-400 hidden sm:inline truncate">
            {savedStatus}
          </span>
        </div>

        {/* Action Buttons */}
        <div className="flex items-center space-x-2 flex-shrink-0">
          <button
            type="button"
            onClick={handleResetCode}
            title="Reset code to clean signature template"
            className="p-1.5 text-slate-400 hover:text-slate-200 hover:bg-dark-700 rounded-lg transition-colors flex-shrink-0"
          >
            <RotateCcw className="w-3.5 h-3.5" />
          </button>

          {onRun && (
            <button
              type="button"
              onClick={() => onRun(selectedLanguage, code)}
              disabled={running || submitting}
              className="flex items-center space-x-1.5 px-3 py-1.5 text-xs font-bold text-slate-200 bg-dark-700 hover:bg-dark-600 rounded-lg border border-dark-500 transition-all disabled:opacity-50 flex-shrink-0 whitespace-nowrap shadow-sm"
            >
              <Play className="w-3.5 h-3.5 text-cyan-400 fill-cyan-400" />
              <span>{running ? 'Running...' : 'Run'}</span>
            </button>
          )}

          <button
            type="button"
            onClick={() => onSubmit(selectedLanguage, code)}
            disabled={submitting || running}
            className="flex items-center space-x-1.5 px-3.5 py-1.5 text-xs font-bold text-white bg-gradient-to-r from-indigo-600 to-cyan-600 hover:from-indigo-500 hover:to-cyan-500 rounded-lg shadow-lg shadow-indigo-600/30 transition-all disabled:opacity-50 flex-shrink-0 whitespace-nowrap"
          >
            <Send className="w-3.5 h-3.5" />
            <span>{submitting ? 'Judging...' : 'Submit (Ctrl+↵)'}</span>
          </button>
        </div>
      </div>

      {/* Monaco Editor Container */}
      <div className="flex-1 min-h-[300px] relative">
        <Editor
          height="100%"
          language={LANGUAGE_CONFIGS[selectedLanguage]?.monacoLang || 'java'}
          value={code}
          theme="vs-dark"
          onChange={handleEditorChange}
          options={{
            fontSize: 13,
            fontFamily: "'Fira Code', monospace",
            minimap: { enabled: false },
            scrollBeyondLastLine: false,
            automaticLayout: true,
            tabSize: 4,
            padding: { top: 12, bottom: 12 },
            lineNumbersMinChars: 3,
            renderLineHighlight: 'all',
            cursorBlinking: 'smooth',
            smoothScrolling: true,
          }}
        />
      </div>
    </div>
  );
};
