import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { matchApi, problemApi, analyticsApi } from '../api';
import { Swords, Code2, Trophy, History, User, Zap, Flame, Target, ArrowRight, Sparkles, CheckCircle2, Clock } from 'lucide-react';

export const Dashboard = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [profile, setProfile] = useState(null);
  const [recentMatches, setRecentMatches] = useState([]);
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      const [profRes, matchRes, recRes] = await Promise.allSettled([
        analyticsApi.getMyAnalytics(),
        matchApi.getHistory(0, 5),
        problemApi.getRecommendations()
      ]);

      if (profRes.status === 'fulfilled' && profRes.value?.data) {
        setProfile(profRes.value.data);
      }
      if (matchRes.status === 'fulfilled' && matchRes.value?.data?.content) {
        setRecentMatches(matchRes.value.data.content);
      }
      if (recRes.status === 'fulfilled' && recRes.value?.data) {
        setRecommendations(recRes.value.data);
      }
    } catch (err) {
      console.error('Error loading dashboard:', err);
    } finally {
      setLoading(false);
    }
  };

  const currentUser = profile?.user || user;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8 animate-fade-in">
      {/* Welcome & Rating Hero Banner */}
      <div className="relative overflow-hidden rounded-3xl bg-gradient-to-r from-dark-800 via-dark-800 to-indigo-950/40 border border-dark-600 p-6 sm:p-8 shadow-2xl">
        <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
          <div className="space-y-2">
            <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-cyan-400 text-xs font-mono">
              <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping"></span>
              <span>Competitive Season Active</span>
            </div>
            <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
              Welcome back, <span className="text-cyan-400">{currentUser?.username || 'Contender'}</span>
            </h1>
            <p className="text-xs sm:text-sm text-slate-400 max-w-xl">
              Ready for your next 1v1 battle? Challenge programmers near your rating and climb the leaderboards.
            </p>
          </div>

          {/* Big Action CTA */}
          <div className="w-full md:w-auto">
            <Link
              to="/versus"
              className="flex items-center justify-center gap-2.5 px-8 py-4 rounded-2xl font-black text-sm text-white bg-gradient-to-r from-indigo-600 via-cyan-600 to-teal-500 hover:scale-105 shadow-xl shadow-cyan-500/20 transition-all duration-300 font-mono tracking-wider uppercase group"
            >
              <Swords className="w-5 h-5 text-white group-hover:rotate-12 transition-transform duration-300" />
              <span>FIND MATCH</span>
              <ArrowRight className="w-4 h-4 ml-1" />
            </Link>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-7 gap-3 sm:gap-4 mt-8 pt-6 border-t border-dark-700 font-mono">
          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans">Rating</div>
            <div className="text-xl font-black text-cyan-400 mt-0.5">
              {currentUser?.rating || 1200}
            </div>
          </div>

          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans">Global Rank</div>
            <div className="text-xl font-black text-indigo-400 mt-0.5">
              #{currentUser?.globalRank || 1}
            </div>
          </div>

          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans">Win Rate</div>
            <div className="text-xl font-black text-emerald-400 mt-0.5">
              {currentUser?.winRate || 0}%
            </div>
          </div>

          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans">Wins</div>
            <div className="text-xl font-black text-white mt-0.5">
              {currentUser?.wins || 0}
            </div>
          </div>

          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans">Losses</div>
            <div className="text-xl font-black text-slate-400 mt-0.5">
              {currentUser?.losses || 0}
            </div>
          </div>

          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans flex items-center gap-1">
              <Flame className="w-3 h-3 text-amber-400" />
              <span>Win Streak</span>
            </div>
            <div className="text-xl font-black text-amber-400 mt-0.5">
              {currentUser?.winStreak || 0}
            </div>
          </div>

          <div className="bg-dark-900/80 p-3.5 rounded-2xl border border-dark-700">
            <div className="text-[11px] text-slate-400 font-sans">Best Streak</div>
            <div className="text-xl font-black text-purple-400 mt-0.5">
              {currentUser?.bestWinStreak || 0}
            </div>
          </div>
        </div>
      </div>

      {/* Quick Navigation Action Grid */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
        <Link
          to="/practice"
          className="p-5 rounded-2xl bg-dark-800 hover:bg-dark-700 border border-dark-600 transition-all flex flex-col justify-between group shadow-lg"
        >
          <div className="w-10 h-10 rounded-xl bg-indigo-500/10 border border-indigo-500/30 flex items-center justify-center text-indigo-400 group-hover:scale-110 transition-transform">
            <Code2 className="w-5 h-5" />
          </div>
          <div className="mt-4">
            <div className="text-sm font-bold text-white group-hover:text-cyan-400 transition-colors">Practice Mode</div>
            <div className="text-xs text-slate-400 mt-0.5">Explore 1,000 question bank</div>
          </div>
        </Link>

        <Link
          to="/leaderboard"
          className="p-5 rounded-2xl bg-dark-800 hover:bg-dark-700 border border-dark-600 transition-all flex flex-col justify-between group shadow-lg"
        >
          <div className="w-10 h-10 rounded-xl bg-amber-500/10 border border-amber-500/30 flex items-center justify-center text-amber-400 group-hover:scale-110 transition-transform">
            <Trophy className="w-5 h-5" />
          </div>
          <div className="mt-4">
            <div className="text-sm font-bold text-white group-hover:text-amber-400 transition-colors">Leaderboard</div>
            <div className="text-xs text-slate-400 mt-0.5">Global rank & top players</div>
          </div>
        </Link>

        <Link
          to="/matches"
          className="p-5 rounded-2xl bg-dark-800 hover:bg-dark-700 border border-dark-600 transition-all flex flex-col justify-between group shadow-lg"
        >
          <div className="w-10 h-10 rounded-xl bg-cyan-500/10 border border-cyan-500/30 flex items-center justify-center text-cyan-400 group-hover:scale-110 transition-transform">
            <History className="w-5 h-5" />
          </div>
          <div className="mt-4">
            <div className="text-sm font-bold text-white group-hover:text-cyan-400 transition-colors">Match History</div>
            <div className="text-xs text-slate-400 mt-0.5">Past duels & analytics</div>
          </div>
        </Link>

        <Link
          to="/profile"
          className="p-5 rounded-2xl bg-dark-800 hover:bg-dark-700 border border-dark-600 transition-all flex flex-col justify-between group shadow-lg"
        >
          <div className="w-10 h-10 rounded-xl bg-purple-500/10 border border-purple-500/30 flex items-center justify-center text-purple-400 group-hover:scale-110 transition-transform">
            <User className="w-5 h-5" />
          </div>
          <div className="mt-4">
            <div className="text-sm font-bold text-white group-hover:text-purple-400 transition-colors">User Profile</div>
            <div className="text-xs text-slate-400 mt-0.5">Badges & topic mastery</div>
          </div>
        </Link>
      </div>

      {/* Main Content Split: Smart Recommendations & Recent Duels */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Recommended for You */}
        <div className="lg:col-span-2 space-y-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-2 text-sm font-bold text-white">
              <Sparkles className="w-4 h-4 text-cyan-400" />
              <span>Recommended for You</span>
            </div>
            <Link to="/practice" className="text-xs text-cyan-400 hover:underline">
              View All 1,000 Questions
            </Link>
          </div>

          <div className="space-y-3">
            {recommendations.length === 0 ? (
              <div className="p-6 rounded-2xl bg-dark-800 border border-dark-700 text-center text-xs text-slate-500 font-mono">
                No recommendations yet. Start playing matches or practice problems to get tailored suggestions!
              </div>
            ) : (
              recommendations.map((rec, idx) => (
                <div
                  key={idx}
                  className="p-4 rounded-2xl bg-dark-800 hover:bg-dark-700 border border-dark-600 flex items-center justify-between gap-4 transition-colors"
                >
                  <div className="space-y-1 min-w-0">
                    <div className="flex items-center space-x-2">
                      <span className="text-xs font-mono font-bold text-cyan-400">{rec.problem.id}</span>
                      <span className="text-sm font-bold text-white truncate">{rec.problem.title}</span>
                      <span className={`text-[10px] px-2 py-0.5 rounded-full font-semibold ${
                        rec.problem.difficulty === 'EASY' ? 'bg-emerald-500/10 text-emerald-400' :
                        rec.problem.difficulty === 'MEDIUM' ? 'bg-amber-500/10 text-amber-400' :
                        'bg-rose-500/10 text-rose-400'
                      }`}>
                        {rec.problem.difficulty}
                      </span>
                    </div>
                    <div className="text-xs text-slate-400 flex items-center gap-1.5">
                      <Target className="w-3.5 h-3.5 text-indigo-400 flex-shrink-0" />
                      <span className="truncate">{rec.reason}</span>
                    </div>
                  </div>

                  <Link
                    to={`/practice/${rec.problem.id}`}
                    className="flex-shrink-0 px-4 py-2 text-xs font-bold text-white bg-indigo-600 hover:bg-indigo-500 rounded-xl transition-colors"
                  >
                    Solve
                  </Link>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Recent Matches */}
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-2 text-sm font-bold text-white">
              <History className="w-4 h-4 text-indigo-400" />
              <span>Recent Battles</span>
            </div>
            <Link to="/matches" className="text-xs text-cyan-400 hover:underline">
              All Matches
            </Link>
          </div>

          <div className="space-y-3">
            {recentMatches.length === 0 ? (
              <div className="p-6 rounded-2xl bg-dark-800 border border-dark-700 text-center text-xs text-slate-500 font-mono">
                No recent matches. Click "FIND MATCH" to start your first 1v1 battle!
              </div>
            ) : (
              recentMatches.map((m) => {
                const opp = m.players?.find(p => p.userId !== currentUser?.id);
                const me = m.players?.find(p => p.userId === currentUser?.id);
                const won = m.winnerId === currentUser?.id;
                const isDraw = m.isDraw;

                return (
                  <div
                    key={m.id}
                    onClick={() => navigate(`/matches/${m.id}`)}
                    className="p-3.5 rounded-2xl bg-dark-800 hover:bg-dark-700 border border-dark-600 cursor-pointer transition-colors space-y-2"
                  >
                    <div className="flex items-center justify-between text-xs">
                      <span className="font-bold text-white truncate max-w-[140px]">
                        VS {opp?.username || 'Opponent'}
                      </span>
                      <span className={`px-2 py-0.5 rounded-full text-[10px] font-bold font-mono ${
                        won ? 'bg-emerald-500/20 text-emerald-400' :
                        isDraw ? 'bg-cyan-500/20 text-cyan-400' :
                        'bg-rose-500/20 text-rose-400'
                      }`}>
                        {won ? 'WIN' : isDraw ? 'DRAW' : 'LOSS'}
                      </span>
                    </div>

                    <div className="flex items-center justify-between text-xs text-slate-400 font-mono">
                      <span className="truncate">{m.problem?.title || 'Coding Duel'}</span>
                      <span className={me?.ratingChange >= 0 ? 'text-emerald-400 font-bold' : 'text-rose-400 font-bold'}>
                        {me?.ratingChange >= 0 ? `+${me?.ratingChange}` : me?.ratingChange}
                      </span>
                    </div>
                  </div>
                );
              })
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
