import React, { useState } from 'react';
import { CheckCircle2, XCircle, Clock, Zap, Terminal, Sparkles, Code2 } from 'lucide-react';

export const TestResultsPanel = ({ result, loading, publicTestCases = [] }) => {
  const [panelView, setPanelView] = useState('RESULT'); // 'RESULT' or 'TESTCASES'
  const [activeCaseIdx, setActiveCaseIdx] = useState(0);

  if (loading) {
    return (
      <div className="h-full flex items-center justify-center bg-dark-900 border border-dark-700 rounded-2xl p-6 text-slate-400 font-mono text-xs">
        <div className="flex items-center space-x-3">
          <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-cyan-400"></div>
          <span>Executing test cases in isolated sandbox...</span>
        </div>
      </div>
    );
  }

  const isAccepted = result?.result === 'ACCEPTED';
  const testCaseResults = result?.testCaseResults || [];

  return (
    <div className="h-full flex flex-col bg-dark-900 border border-dark-700 rounded-2xl overflow-hidden shadow-2xl">
      {/* Top Tab Bar: [ Testcase ] & [ Test Result ] */}
      <div className="flex items-center space-x-2 px-3 py-2 bg-dark-800 border-b border-dark-700 flex-shrink-0 text-xs font-mono">
        <button
          onClick={() => setPanelView('TESTCASES')}
          className={`flex items-center space-x-1.5 px-3 py-1 rounded-lg transition-colors ${
            panelView === 'TESTCASES'
              ? 'bg-dark-700 text-white font-bold border border-dark-500'
              : 'text-slate-400 hover:text-slate-200'
          }`}
        >
          <Code2 className="w-3.5 h-3.5" />
          <span>Testcase</span>
        </button>

        <button
          onClick={() => setPanelView('RESULT')}
          className={`flex items-center space-x-1.5 px-3 py-1 rounded-lg transition-colors ${
            panelView === 'RESULT'
              ? 'bg-dark-700 text-cyan-400 font-bold border border-dark-500'
              : 'text-slate-400 hover:text-slate-200'
          }`}
        >
          <Terminal className="w-3.5 h-3.5" />
          <span>Test Result {result ? `(${result.result?.replace(/_/g, ' ')})` : ''}</span>
        </button>
      </div>

      {/* View 1: Testcase Inspector */}
      {panelView === 'TESTCASES' && (
        <div className="flex-1 flex flex-col min-h-0 p-3 space-y-3 font-mono text-xs">
          <div className="flex items-center space-x-1.5">
            {[1, 2, 3].map((num, idx) => (
              <button
                key={idx}
                onClick={() => setActiveCaseIdx(idx)}
                className={`px-3 py-1 rounded-lg transition-colors ${
                  activeCaseIdx === idx
                    ? 'bg-dark-700 text-cyan-300 font-bold border border-dark-500'
                    : 'bg-dark-800 text-slate-400 hover:bg-dark-700'
                }`}
              >
                Case {num}
              </button>
            ))}
          </div>

          <div className="flex-1 overflow-y-auto space-y-2">
            <div className="text-slate-400 font-semibold">Standard Input:</div>
            <div className="p-3 bg-dark-800 rounded-xl border border-dark-700 text-slate-200 whitespace-pre-wrap">
              {activeCaseIdx === 0 ? "4\n2 7 11 15\n9" : activeCaseIdx === 1 ? "3\n3 2 4\n6" : "2\n3 3\n6"}
            </div>
            <div className="text-[11px] text-slate-500">
              Passes array elements followed by target into solution method.
            </div>
          </div>
        </div>
      )}

      {/* View 2: Test Results */}
      {panelView === 'RESULT' && (
        !result ? (
          <div className="flex-1 flex flex-col items-center justify-center p-6 text-slate-500 font-mono text-xs text-center space-y-2">
            <Terminal className="w-8 h-8 text-slate-600 mb-1" />
            <div>Run code or submit solution to view test execution results & efficiency benchmarks.</div>
          </div>
        ) : (
          <div className="flex-1 flex flex-col min-h-0">
            {/* Result Verdict Bar */}
            <div className={`px-4 py-2.5 border-b border-dark-700 flex flex-wrap items-center justify-between gap-2 ${
              isAccepted ? 'bg-emerald-950/20' : 'bg-rose-950/20'
            }`}>
              <div className="flex items-center space-x-2">
                {isAccepted ? (
                  <CheckCircle2 className="w-5 h-5 text-emerald-400" />
                ) : (
                  <XCircle className="w-5 h-5 text-rose-400" />
                )}
                <div>
                  <span className={`font-bold font-mono text-sm ${isAccepted ? 'text-emerald-400' : 'text-rose-400'}`}>
                    {result.result?.replace(/_/g, ' ')}
                  </span>
                  <span className="text-xs text-slate-400 ml-2">
                    ({result.testsPassed} / {result.totalTests} passed)
                  </span>
                </div>
              </div>

              {/* Benchmarks */}
              <div className="flex items-center gap-2 font-mono text-xs">
                <span className="px-2.5 py-0.5 rounded bg-dark-800 border border-dark-600 text-cyan-300">
                  {result.executionTimeMs || 0} ms
                </span>
                <span className="px-2.5 py-0.5 rounded bg-dark-800 border border-dark-600 text-indigo-300">
                  {result.memoryUsageMb || 0} MB
                </span>
                {result.efficiencyScore != null && (
                  <span className="px-2.5 py-0.5 rounded bg-indigo-950 border border-indigo-500/40 text-amber-300 font-bold">
                    Efficiency: {result.efficiencyScore}/100
                  </span>
                )}
              </div>
            </div>

            {/* Compiler Output if any */}
            {result.compilerOutput && (
              <div className="p-3 bg-rose-950/40 border-b border-rose-500/30 text-rose-300 text-xs font-mono whitespace-pre-wrap max-h-28 overflow-y-auto">
                {result.compilerOutput}
              </div>
            )}

            {/* Testcase Results Accordion */}
            {testCaseResults.length > 0 && (
              <div className="flex-1 flex flex-col min-h-0">
                <div className="flex items-center space-x-1 px-3 py-1.5 bg-dark-800/80 border-b border-dark-700 overflow-x-auto">
                  {testCaseResults.map((tc, idx) => (
                    <button
                      key={idx}
                      onClick={() => setActiveCaseIdx(idx)}
                      className={`flex items-center space-x-1.5 px-2.5 py-1 rounded-lg text-xs font-mono transition-colors ${
                        activeCaseIdx === idx
                          ? 'bg-dark-700 text-white font-bold border border-dark-500'
                          : 'text-slate-400 hover:text-slate-200'
                      }`}
                    >
                      <span className={`w-2 h-2 rounded-full ${tc.passed ? 'bg-emerald-400' : 'bg-rose-400'}`}></span>
                      <span>Case {idx + 1} {tc.isHidden ? '(Hidden)' : ''}</span>
                    </button>
                  ))}
                </div>

                {testCaseResults[activeCaseIdx] && (
                  <div className="flex-1 p-3 overflow-y-auto space-y-2.5 font-mono text-xs">
                    <div>
                      <div className="text-slate-400 font-semibold mb-1">Input:</div>
                      <div className="p-2 bg-dark-800 rounded-lg border border-dark-700 text-slate-200 whitespace-pre-wrap">
                        {testCaseResults[activeCaseIdx].input}
                      </div>
                    </div>

                    <div>
                      <div className="text-slate-400 font-semibold mb-1">Expected Output:</div>
                      <div className="p-2 bg-dark-800 rounded-lg border border-dark-700 text-emerald-400 whitespace-pre-wrap">
                        {testCaseResults[activeCaseIdx].expected}
                      </div>
                    </div>

                    <div>
                      <div className="text-slate-400 font-semibold mb-1">Actual Output:</div>
                      <div className={`p-2 rounded-lg border whitespace-pre-wrap ${
                        testCaseResults[activeCaseIdx].passed
                          ? 'bg-dark-800 border-dark-700 text-emerald-300'
                          : 'bg-rose-950/30 border-rose-500/30 text-rose-300'
                      }`}>
                        {testCaseResults[activeCaseIdx].actual || '[No output or error]'}
                      </div>
                    </div>
                  </div>
                )}
              </div>
            )}
          </div>
        )
      )}
    </div>
  );
};
