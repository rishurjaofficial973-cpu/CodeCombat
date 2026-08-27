import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { matchApi } from '../api';
import { useAuth } from '../context/AuthContext';
import { PostMatchAnalysis } from '../components/PostMatchAnalysis';
import { History, Swords, Trophy, Clock, ChevronRight, BarChart2 } from 'lucide-react';

export const MatchHistory = () => {
  const { matchId } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();

  const [matches, setMatches] = useState([]);
  const [selectedResult, setSelectedResult] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadMatches();
  }, []);

  useEffect(() => {
    if (matchId) {
      loadSpecificMatchResult(matchId);
    }
  }, [matchId]);

  const loadMatches = async () => {
    try {
      const res = await matchApi.getHistory(0, 30);
      if (res.data?.content) {
        setMatches(res.data.content);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const loadSpecificMatchResult = async (id) => {
    try {
      const res = await matchApi.getMatchResult(id);
      if (res.data) {
        setSelectedResult(res.data);
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6 animate-fade-in">
      <div>
        <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-cyan-400 text-xs font-mono mb-2">
          <History className="w-4 h-4" />
          <span>Battle Log</span>
        </div>
        <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
          Match History
        </h1>
        <p className="text-xs sm:text-sm text-slate-400">
          Review your 1v1 performance, runtime efficiencies, and rating trends
        </p>
      </div>

      {/* Post Match Modal if selected */}
      {selectedResult && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-dark-900/90 backdrop-blur-xl animate-fade-in">
          <div className="max-w-3xl w-full max-h-[90vh] overflow-y-auto">
            <PostMatchAnalysis
              analysis={selectedResult.analysis}
              onBack={() => {
                setSelectedResult(null);
                if (matchId) navigate('/matches');
              }}
            />
          </div>
        </div>
      )}

      {/* Matches List */}
      <div className="space-y-3">
        {loading ? (
          <div className="p-12 text-center text-slate-400 font-mono text-xs bg-dark-800 rounded-3xl border border-dark-600">
            <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-cyan-400 mx-auto mb-3"></div>
            Loading battle logs...
          </div>
        ) : matches.length === 0 ? (
          <div className="p-12 text-center text-slate-500 font-mono text-xs bg-dark-800 rounded-3xl border border-dark-600">
            No matches found in your history yet.
          </div>
        ) : (
          matches.map((m) => {
            const me = m.players?.find(p => p.userId === user?.id);
            const opp = m.players?.find(p => p.userId !== user?.id);
            const isWinner = m.winnerId === user?.id;
            const isDraw = m.isDraw;

            return (
              <div
                key={m.id}
                onClick={() => loadSpecificMatchResult(m.id)}
                className="p-5 rounded-2xl bg-dark-800 hover:bg-dark-700/80 border border-dark-600 cursor-pointer transition-all duration-200 flex flex-col sm:flex-row sm:items-center justify-between gap-4 shadow-lg group"
              >
                <div className="space-y-1.5 min-w-0">
                  <div className="flex items-center space-x-3">
                    <span className={`px-2.5 py-0.5 rounded-full text-xs font-bold font-mono ${
                      isWinner ? 'bg-emerald-500/20 text-emerald-400 border border-emerald-500/30' :
                      isDraw ? 'bg-cyan-500/20 text-cyan-400 border border-cyan-500/30' :
                      'bg-rose-500/20 text-rose-400 border border-rose-500/30'
                    }`}>
                      {isWinner ? 'VICTORY' : isDraw ? 'DRAW' : 'DEFEAT'}
                    </span>

                    <span className="font-bold text-sm text-white truncate">
                      VS {opp?.username || 'Opponent'} ({opp?.ratingBefore || 1500})
                    </span>
                  </div>

                  <div className="flex flex-wrap items-center gap-3 text-xs text-slate-400 font-mono">
                    <span>Problem: <span className="text-slate-200 font-semibold">{m.problem?.title || 'Coding Problem'}</span></span>
                    <span>•</span>
                    <span>Mode: {m.mode}</span>
                  </div>
                </div>

                {/* Score & Performance Summary */}
                <div className="flex items-center space-x-6 self-end sm:self-center font-mono text-xs">
                  <div className="text-right">
                    <div className="text-slate-400 text-[11px]">Score</div>
                    <div className="font-bold text-white">
                      {me?.score || 0} - {opp?.score || 0}
                    </div>
                  </div>

                  <div className="text-right">
                    <div className="text-slate-400 text-[11px]">Runtime</div>
                    <div className="font-bold text-cyan-300">
                      {me?.executionTimeMs ? `${me.executionTimeMs}ms` : '-'}
                    </div>
                  </div>

                  <div className="text-right">
                    <div className="text-slate-400 text-[11px]">Rating</div>
                    <div className={`font-bold text-sm ${me?.ratingChange >= 0 ? 'text-emerald-400' : 'text-rose-400'}`}>
                      {me?.ratingChange >= 0 ? `+${me?.ratingChange}` : me?.ratingChange}
                    </div>
                  </div>

                  <ChevronRight className="w-5 h-5 text-slate-500 group-hover:text-cyan-400 group-hover:translate-x-1 transition-all" />
                </div>
              </div>
            );
          })
        )}
      </div>
    </div>
  );
};
