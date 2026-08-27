import React from 'react';
import { Link } from 'react-router-dom';
import { Swords, Code2, Zap, Shield, Trophy, Cpu, ArrowRight } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

export const Home = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="flex flex-col min-h-[calc(100vh-4rem)]">
      {/* Hero Section */}
      <section className="relative overflow-hidden pt-12 pb-20 lg:pt-20 lg:pb-28">
        <div className="absolute inset-0 bg-[radial-gradient(ellipse_at_top,_var(--tw-gradient-stops))] from-indigo-900/30 via-dark-900 to-dark-900 -z-10"></div>

        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-indigo-500/10 border border-indigo-500/30 text-cyan-400 text-xs font-mono mb-8 animate-fade-in">
            <Zap className="w-4 h-4 text-amber-400" />
            <span>Next-Gen 1v1 Real-Time Competitive Coding</span>
          </div>

          <h1 className="text-4xl sm:text-6xl lg:text-7xl font-extrabold text-white tracking-tight leading-none mb-6">
            DUEL IN CODE. <br />
            <span className="bg-gradient-to-r from-cyan-400 via-indigo-400 to-purple-400 bg-clip-text text-transparent">
              PROVE EFFICIENCY.
            </span>
          </h1>

          <p className="max-w-2xl mx-auto text-base sm:text-lg text-slate-400 mb-10 leading-relaxed">
            Face off against live opponents on the exact same DSA problem. Win not just by submitting first, but by engineering cleaner, higher-efficiency algorithmic solutions.
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <Link
              to={isAuthenticated ? "/versus" : "/register"}
              className="w-full sm:w-auto flex items-center justify-center gap-2 px-8 py-3.5 rounded-xl font-bold text-sm text-white bg-gradient-to-r from-indigo-600 to-cyan-600 hover:from-indigo-500 hover:to-cyan-500 shadow-xl shadow-indigo-600/30 hover:scale-105 transition-all duration-200"
            >
              <Swords className="w-5 h-5" />
              <span>Enter Versus Arena</span>
              <ArrowRight className="w-4 h-4 ml-1" />
            </Link>

            <Link
              to="/practice"
              className="w-full sm:w-auto flex items-center justify-center gap-2 px-8 py-3.5 rounded-xl font-bold text-sm text-slate-300 hover:text-white bg-dark-800 hover:bg-dark-700 border border-dark-600 transition-colors"
            >
              <Code2 className="w-5 h-5 text-indigo-400" />
              <span>Explore 1,000 Problems</span>
            </Link>
          </div>

          {/* Versus Mock Preview Card */}
          <div className="mt-16 max-w-4xl mx-auto rounded-2xl p-1 bg-gradient-to-b from-dark-600 to-dark-800 shadow-2xl">
            <div className="bg-dark-900 rounded-[14px] p-4 sm:p-6 border border-dark-700 font-mono text-xs text-left">
              <div className="flex items-center justify-between border-b border-dark-700 pb-3 mb-4 text-slate-400">
                <div className="flex items-center gap-2">
                  <span className="w-3 h-3 rounded-full bg-rose-500 inline-block"></span>
                  <span className="w-3 h-3 rounded-full bg-amber-500 inline-block"></span>
                  <span className="w-3 h-3 rounded-full bg-emerald-500 inline-block"></span>
                  <span className="ml-2 font-bold text-white">CODECOMBAT ARENA — CD-0001: TWO SUM</span>
                </div>
                <div className="text-cyan-400 font-bold">⏱ 11:42 REMAINING</div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="bg-dark-800 p-4 rounded-xl border border-emerald-500/30 space-y-2">
                  <div className="flex justify-between font-bold">
                    <span className="text-white">Player 1 (Rishu - 1542)</span>
                    <span className="text-emerald-400">✓ ACCEPTED</span>
                  </div>
                  <div className="text-slate-400">Runtime: <span className="text-cyan-300 font-bold">124 ms</span></div>
                  <div className="text-slate-400">Efficiency Score: <span className="text-emerald-400 font-bold">96/100</span></div>
                  <div className="text-slate-400">Elo Gain: <span className="text-emerald-400 font-bold">+25 Rating</span></div>
                </div>

                <div className="bg-dark-800 p-4 rounded-xl border border-dark-700 space-y-2">
                  <div className="flex justify-between font-bold">
                    <span className="text-slate-300">Player 2 (Rahul - 1518)</span>
                    <span className="text-amber-400">● CODING</span>
                  </div>
                  <div className="text-slate-400">Tests Passed: <span className="text-slate-200">8 / 15</span></div>
                  <div className="text-slate-400">Status: <span className="text-slate-200">Running Sandbox...</span></div>
                  <div className="text-slate-400">Elo Loss: <span className="text-rose-400 font-bold">-18 Rating</span></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Feature Pillars */}
      <section className="py-16 bg-dark-800/40 border-t border-dark-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="p-6 rounded-2xl bg-dark-800 border border-dark-700 space-y-3">
              <div className="w-12 h-12 rounded-xl bg-indigo-500/10 border border-indigo-500/30 flex items-center justify-center text-indigo-400">
                <Cpu className="w-6 h-6" />
              </div>
              <h3 className="text-base font-bold text-white">Normalized Efficiency Scoring</h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                Rewarding algorithm optimality (70% runtime + 30% memory) rather than simply who clicked submit first.
              </p>
            </div>

            <div className="p-6 rounded-2xl bg-dark-800 border border-dark-700 space-y-3">
              <div className="w-12 h-12 rounded-xl bg-cyan-500/10 border border-cyan-500/30 flex items-center justify-center text-cyan-400">
                <Shield className="w-6 h-6" />
              </div>
              <h3 className="text-base font-bold text-white">Isolated Judging Sandbox</h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                Multi-language execution engine (Java, Python, C++, JS) with hidden test cases and strict resource limits.
              </p>
            </div>

            <div className="p-6 rounded-2xl bg-dark-800 border border-dark-700 space-y-3">
              <div className="w-12 h-12 rounded-xl bg-purple-500/10 border border-purple-500/30 flex items-center justify-center text-purple-400">
                <Trophy className="w-6 h-6" />
              </div>
              <h3 className="text-base font-bold text-white">Real Elo & Global Leaderboards</h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                Dynamic K-factor Elo calculations backed by high-speed Redis sorted sets and permanent MySQL history.
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};
