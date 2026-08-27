import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import confetti from 'canvas-confetti';
import { Trophy, Swords, Zap, ArrowRight, RotateCcw, LayoutDashboard, BarChart3, CheckCircle, XCircle } from 'lucide-react';

export const MatchResultModal = ({ result, currentUserId, onClose, onRematch, onViewAnalysis }) => {
  const navigate = useNavigate();

  const isWinner = result?.winnerId === currentUserId;
  const isDraw = result?.isDraw;

  const myPlayer = result?.players?.find(p => p.userId === currentUserId);
  const opponent = result?.players?.find(p => p.userId !== currentUserId);

  useEffect(() => {
    if (isWinner) {
      confetti({
        particleCount: 80,
        spread: 70,
        origin: { y: 0.6 }
      });
    }
  }, [isWinner]);

  if (!result) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-dark-900/90 backdrop-blur-xl animate-fade-in">
      <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 sm:p-8 max-w-2xl w-full shadow-2xl space-y-6">
        {/* Banner */}
        <div className="text-center space-y-2">
          <div className="flex justify-center">
            {isWinner ? (
              <div className="p-3.5 rounded-2xl bg-amber-500/20 border border-amber-500/40 text-amber-400 animate-bounce">
                <Trophy className="w-10 h-10" />
              </div>
            ) : isDraw ? (
              <div className="p-3.5 rounded-2xl bg-cyan-500/20 border border-cyan-500/40 text-cyan-400">
                <Swords className="w-10 h-10" />
              </div>
            ) : (
              <div className="p-3.5 rounded-2xl bg-rose-500/20 border border-rose-500/40 text-rose-400">
                <XCircle className="w-10 h-10" />
              </div>
            )}
          </div>

          <h2 className="text-3xl font-black font-mono tracking-tight uppercase">
            {isWinner ? (
              <span className="text-emerald-400 drop-shadow-[0_0_15px_rgba(16,185,129,0.5)]">VICTORY</span>
            ) : isDraw ? (
              <span className="text-cyan-400">DRAW</span>
            ) : (
              <span className="text-rose-400">DEFEAT</span>
            )}
          </h2>

          <div className="text-xs font-mono text-slate-400">
            Problem: <span className="text-white font-semibold">{result.problemTitle || result.problemId}</span>
          </div>
        </div>

        {/* Head-to-Head Comparison Grid */}
        <div className="grid grid-cols-2 gap-4">
          {/* My Card */}
          <div className={`p-4 rounded-2xl border space-y-3 ${
            isWinner ? 'bg-emerald-950/20 border-emerald-500/40' : 'bg-dark-900 border-dark-700'
          }`}>
            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 rounded-lg bg-indigo-600 flex items-center justify-center text-xs font-bold text-white uppercase">
                {myPlayer?.username?.charAt(0) || 'Y'}
              </div>
              <div className="truncate">
                <div className="text-xs font-bold text-white truncate">{myPlayer?.username || 'You'}</div>
                <div className="text-[11px] font-mono text-cyan-400 font-semibold">
                  Rating: {myPlayer?.ratingAfter || myPlayer?.ratingBefore}
                  <span className={`ml-1 font-bold ${myPlayer?.ratingChange >= 0 ? 'text-emerald-400' : 'text-rose-400'}`}>
                    ({myPlayer?.ratingChange >= 0 ? `+${myPlayer?.ratingChange}` : myPlayer?.ratingChange})
                  </span>
                </div>
              </div>
            </div>

            <div className="space-y-1.5 font-mono text-xs pt-2 border-t border-dark-700/60">
              <div className="flex justify-between">
                <span className="text-slate-400">Status:</span>
                <span className={myPlayer?.status === 'ACCEPTED' ? 'text-emerald-400 font-bold' : 'text-slate-300'}>
                  {myPlayer?.status?.replace(/_/g, ' ')}
                </span>
              </div>
              <div className="flex justify-between">
                <span className="text-slate-400">Runtime:</span>
                <span className="text-white">{myPlayer?.executionTimeMs ? `${myPlayer.executionTimeMs} ms` : '-'}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-slate-400">Memory:</span>
                <span className="text-white">{myPlayer?.memoryUsageMb ? `${myPlayer.memoryUsageMb} MB` : '-'}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-slate-400">Efficiency:</span>
                <span className="text-cyan-300 font-bold">{myPlayer?.efficiencyScore || 0}/100</span>
              </div>
              <div className="flex justify-between text-sm font-bold pt-1 border-t border-dark-700">
                <span className="text-slate-300">Total Score:</span>
                <span className="text-indigo-400">{myPlayer?.score || 0}</span>
              </div>
            </div>
          </div>

          {/* Opponent Card */}
          <div className={`p-4 rounded-2xl border space-y-3 ${
            !isWinner && !isDraw ? 'bg-emerald-950/20 border-emerald-500/40' : 'bg-dark-900 border-dark-700'
          }`}>
            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 rounded-lg bg-rose-600 flex items-center justify-center text-xs font-bold text-white uppercase">
                {opponent?.username?.charAt(0) || 'O'}
              </div>
              <div className="truncate">
                <div className="text-xs font-bold text-white truncate">{opponent?.username || 'Opponent'}</div>
                <div className="text-[11px] font-mono text-cyan-400 font-semibold">
                  Rating: {opponent?.ratingAfter || opponent?.ratingBefore}
                  <span className={`ml-1 font-bold ${opponent?.ratingChange >= 0 ? 'text-emerald-400' : 'text-rose-400'}`}>
                    ({opponent?.ratingChange >= 0 ? `+${opponent?.ratingChange}` : opponent?.ratingChange})
                  </span>
                </div>
              </div>
            </div>

            <div className="space-y-1.5 font-mono text-xs pt-2 border-t border-dark-700/60">
              <div className="flex justify-between">
                <span className="text-slate-400">Status:</span>
                <span className={opponent?.status === 'ACCEPTED' ? 'text-emerald-400 font-bold' : 'text-slate-300'}>
                  {opponent?.status?.replace(/_/g, ' ')}
                </span>
              </div>
              <div className="flex justify-between">
                <span className="text-slate-400">Runtime:</span>
                <span className="text-white">{opponent?.executionTimeMs ? `${opponent.executionTimeMs} ms` : '-'}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-slate-400">Memory:</span>
                <span className="text-white">{opponent?.memoryUsageMb ? `${opponent.memoryUsageMb} MB` : '-'}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-slate-400">Efficiency:</span>
                <span className="text-cyan-300 font-bold">{opponent?.efficiencyScore || 0}/100</span>
              </div>
              <div className="flex justify-between text-sm font-bold pt-1 border-t border-dark-700">
                <span className="text-slate-300">Total Score:</span>
                <span className="text-indigo-400">{opponent?.score || 0}</span>
              </div>
            </div>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="flex flex-col sm:flex-row items-center justify-between gap-3 pt-2">
          {onViewAnalysis && (
            <button
              onClick={onViewAnalysis}
              className="w-full sm:w-auto flex items-center justify-center gap-2 px-4 py-2 rounded-xl text-xs font-bold text-cyan-300 bg-dark-700 hover:bg-dark-600 border border-cyan-500/30 transition-colors"
            >
              <BarChart3 className="w-4 h-4" />
              <span>Post-Match Analysis</span>
            </button>
          )}

          <div className="w-full sm:w-auto flex items-center gap-2">
            <button
              onClick={() => navigate('/dashboard')}
              className="flex-1 sm:flex-none flex items-center justify-center gap-1.5 px-4 py-2 rounded-xl text-xs font-semibold text-slate-300 hover:text-white bg-dark-700 hover:bg-dark-600 transition-colors"
            >
              <LayoutDashboard className="w-4 h-4" />
              <span>Dashboard</span>
            </button>

            <button
              onClick={() => navigate('/versus')}
              className="flex-1 sm:flex-none flex items-center justify-center gap-1.5 px-5 py-2 rounded-xl text-xs font-bold text-white bg-gradient-to-r from-indigo-600 to-cyan-600 hover:from-indigo-500 hover:to-cyan-500 shadow-lg shadow-indigo-600/30 transition-all"
            >
              <RotateCcw className="w-4 h-4" />
              <span>Play Again</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
