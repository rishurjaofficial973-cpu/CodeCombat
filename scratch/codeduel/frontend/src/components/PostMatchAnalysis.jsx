import React from 'react';
import { BarChart2, Cpu, HardDrive, Sparkles, TrendingUp, CheckCircle, Clock } from 'lucide-react';

export const PostMatchAnalysis = ({ analysis, onBack }) => {
  if (!analysis) {
    return (
      <div className="p-6 text-center text-slate-500 font-mono text-xs">
        No match analysis data available.
      </div>
    );
  }

  return (
    <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 sm:p-8 space-y-6 shadow-2xl">
      <div className="flex items-center justify-between border-b border-dark-700 pb-4">
        <div>
          <div className="text-xs font-mono uppercase tracking-wider text-cyan-400 font-bold">
            Post-Match Deep Analysis
          </div>
          <h2 className="text-xl font-bold text-white mt-0.5">
            {analysis.problemTitle || analysis.problemId}
          </h2>
        </div>

        {onBack && (
          <button
            onClick={onBack}
            className="px-4 py-1.5 text-xs font-semibold text-slate-300 hover:text-white bg-dark-700 hover:bg-dark-600 rounded-xl transition-colors"
          >
            Back
          </button>
        )}
      </div>

      {/* Benchmark Percentile Banner */}
      {analysis.myRuntimePercentile != null && (
        <div className="p-4 rounded-2xl bg-gradient-to-r from-indigo-950/40 via-cyan-950/30 to-dark-800 border border-cyan-500/30 flex items-center space-x-4">
          <div className="p-3 rounded-xl bg-cyan-500/20 text-cyan-400">
            <TrendingUp className="w-6 h-6" />
          </div>
          <div>
            <div className="text-sm font-bold text-white">
              You performed faster than <span className="text-cyan-400 font-mono font-black">{analysis.myRuntimePercentile}%</span> of valid submissions.
            </div>
            <div className="text-xs text-slate-400 mt-0.5">
              Normalized runtime: <span className="text-slate-200 font-mono">{analysis.myRuntimeMs}ms</span> (Community Average: {analysis.avgProblemRuntimeMs || 120}ms)
            </div>
          </div>
        </div>
      )}

      {/* Metrics Breakdown Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Runtime Performance */}
        <div className="p-4 rounded-2xl bg-dark-900 border border-dark-700 space-y-3">
          <div className="flex items-center justify-between text-xs font-bold text-slate-300">
            <span className="flex items-center gap-1.5 text-cyan-400">
              <Clock className="w-4 h-4" />
              <span>Execution Runtime</span>
            </span>
            <span className="font-mono text-cyan-300">{analysis.myRuntimeMs || 0} ms</span>
          </div>

          <div className="space-y-2 pt-2 border-t border-dark-700 font-mono text-xs">
            <div className="flex justify-between text-slate-400">
              <span>Your Runtime:</span>
              <span className="text-white font-semibold">{analysis.myRuntimeMs || 0} ms</span>
            </div>
            <div className="flex justify-between text-slate-400">
              <span>Opponent Runtime:</span>
              <span className="text-white font-semibold">{analysis.opponentRuntimeMs || 0} ms</span>
            </div>
            <div className="flex justify-between text-slate-400">
              <span>Community Average:</span>
              <span className="text-slate-400">{analysis.avgProblemRuntimeMs || 120} ms</span>
            </div>
          </div>
        </div>

        {/* Memory & Space */}
        <div className="p-4 rounded-2xl bg-dark-900 border border-dark-700 space-y-3">
          <div className="flex items-center justify-between text-xs font-bold text-slate-300">
            <span className="flex items-center gap-1.5 text-indigo-400">
              <HardDrive className="w-4 h-4" />
              <span>Memory Utilization</span>
            </span>
            <span className="font-mono text-indigo-300">{analysis.myMemoryMb || 0} MB</span>
          </div>

          <div className="space-y-2 pt-2 border-t border-dark-700 font-mono text-xs">
            <div className="flex justify-between text-slate-400">
              <span>Your Memory:</span>
              <span className="text-white font-semibold">{analysis.myMemoryMb || 0} MB</span>
            </div>
            <div className="flex justify-between text-slate-400">
              <span>Opponent Memory:</span>
              <span className="text-white font-semibold">{analysis.opponentMemoryMb || 0} MB</span>
            </div>
            <div className="flex justify-between text-slate-400">
              <span>Community Average:</span>
              <span className="text-slate-400">{analysis.avgProblemMemoryMb || 26} MB</span>
            </div>
          </div>
        </div>
      </div>

      {/* Complexity Evaluation */}
      <div className="p-5 rounded-2xl bg-dark-900 border border-dark-700 space-y-3">
        <div className="text-xs font-mono uppercase tracking-wider text-slate-400 font-bold flex items-center gap-2">
          <Cpu className="w-4 h-4 text-cyan-400" />
          <span>Algorithmic Complexity Evaluation</span>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 pt-2 font-mono text-xs">
          <div className="p-3 bg-dark-800 rounded-xl border border-dark-700">
            <div className="text-slate-500 text-[11px]">Expected Time</div>
            <div className="text-cyan-300 font-bold mt-1 text-sm">{analysis.expectedTimeComplexity || 'O(n)'}</div>
          </div>
          <div className="p-3 bg-dark-800 rounded-xl border border-dark-700">
            <div className="text-slate-500 text-[11px]">Estimated Time</div>
            <div className="text-emerald-400 font-bold mt-1 text-sm">{analysis.myEstimatedTimeComplexity || 'O(n)'}</div>
          </div>
          <div className="p-3 bg-dark-800 rounded-xl border border-dark-700">
            <div className="text-slate-500 text-[11px]">Expected Space</div>
            <div className="text-indigo-300 font-bold mt-1 text-sm">{analysis.expectedSpaceComplexity || 'O(1)'}</div>
          </div>
          <div className="p-3 bg-dark-800 rounded-xl border border-dark-700">
            <div className="text-slate-500 text-[11px]">Efficiency Score</div>
            <div className="text-amber-400 font-bold mt-1 text-sm">{analysis.myEfficiencyScore || 0}/100</div>
          </div>
        </div>
      </div>

      {/* Optimization Tips */}
      {analysis.optimizationTips && analysis.optimizationTips.length > 0 && (
        <div className="p-4 rounded-2xl bg-indigo-950/20 border border-indigo-500/20 space-y-2">
          <div className="text-xs font-bold text-indigo-300 flex items-center gap-1.5">
            <Sparkles className="w-4 h-4 text-cyan-400" />
            <span>Optimization Hints & Notes</span>
          </div>
          <ul className="space-y-1 text-xs text-slate-300 list-disc list-inside">
            {analysis.optimizationTips.map((tip, idx) => (
              <li key={idx}>{tip}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};
