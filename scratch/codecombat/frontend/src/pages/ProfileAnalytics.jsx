import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { analyticsApi } from '../api';
import { User, Trophy, Flame, Target, Shield, Award, Sparkles, CheckCircle2, TrendingUp } from 'lucide-react';

export const ProfileAnalytics = () => {
  const { user } = useAuth();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    try {
      const res = await analyticsApi.getMyAnalytics();
      if (res.data) setProfile(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const currentUser = profile?.user || user;
  const ratingHistory = profile?.ratingHistory || [];
  const difficultyStats = profile?.difficultyStats || {};
  const topicMastery = profile?.topicMastery || {};
  const achievements = profile?.achievements || [];

  if (loading) {
    return (
      <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center font-mono text-slate-400 text-xs">
        <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-cyan-400 mr-2"></div>
        Loading analytics profile...
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8 animate-fade-in">
      {/* Profile Card Header */}
      <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 sm:p-8 shadow-2xl flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
        <div className="flex items-center space-x-4">
          <div className="w-16 h-16 rounded-2xl bg-gradient-to-br from-indigo-500 via-cyan-500 to-teal-400 p-0.5 shadow-xl">
            <div className="w-full h-full bg-dark-900 rounded-[14px] flex items-center justify-center font-black text-2xl text-white uppercase">
              {currentUser?.username?.charAt(0) || 'U'}
            </div>
          </div>

          <div className="space-y-1">
            <div className="flex items-center gap-2">
              <h1 className="text-2xl font-bold text-white tracking-tight">{currentUser?.username}</h1>
              <span className="text-[10px] px-2 py-0.5 rounded-full bg-indigo-500/20 text-indigo-300 font-mono border border-indigo-500/30">
                {currentUser?.role?.replace('ROLE_', '')}
              </span>
            </div>
            <p className="text-xs text-slate-400 font-mono">
              Member since {new Date(currentUser?.createdAt || Date.now()).toLocaleDateString()}
            </p>
          </div>
        </div>

        {/* Big Rating Pill */}
        <div className="flex items-center space-x-6 font-mono">
          <div className="text-right">
            <div className="text-xs text-slate-400 font-sans">Global Rank</div>
            <div className="text-2xl font-black text-indigo-400">#{currentUser?.globalRank || 1}</div>
          </div>

          <div className="p-4 bg-dark-900 rounded-2xl border border-cyan-500/30 text-center min-w-[120px]">
            <div className="text-[11px] text-slate-400 font-sans">Elo Rating</div>
            <div className="text-3xl font-black text-cyan-400">⚡ {currentUser?.rating || 1200}</div>
          </div>
        </div>
      </div>

      {/* 4-Stat Box Grid */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 font-mono">
        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Win Rate</div>
          <div className="text-2xl font-black text-emerald-400">{currentUser?.winRate || 0}%</div>
          <div className="text-[11px] text-slate-500 font-sans">{currentUser?.wins} W - {currentUser?.losses} L - {currentUser?.draws} D</div>
        </div>

        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans flex items-center gap-1">
            <Flame className="w-3.5 h-3.5 text-amber-400" />
            <span>Streaks</span>
          </div>
          <div className="text-2xl font-black text-amber-400">{currentUser?.winStreak || 0} Wins</div>
          <div className="text-[11px] text-slate-500 font-sans">Best: {currentUser?.bestWinStreak || 0} consecutive</div>
        </div>

        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Solved Problems</div>
          <div className="text-2xl font-black text-cyan-300">{profile?.solvedProblemsCount || 0}</div>
          <div className="text-[11px] text-slate-500 font-sans">{profile?.attemptedProblemsCount || 0} attempted</div>
        </div>

        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Accepted Submissions</div>
          <div className="text-2xl font-black text-purple-400">{profile?.acceptedSubmissionsCount || 0}</div>
          <div className="text-[11px] text-slate-500 font-sans">Total verified solves</div>
        </div>
      </div>

      {/* Rating Progression & Topic Breakdown */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Rating Progression List */}
        <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 space-y-4 shadow-xl">
          <div className="flex items-center space-x-2 text-sm font-bold text-white">
            <TrendingUp className="w-4 h-4 text-cyan-400" />
            <span>Rating Progression History</span>
          </div>

          {ratingHistory.length === 0 ? (
            <div className="p-8 text-center text-xs text-slate-500 font-mono">
              No rating changes recorded yet. Compete in 1v1 duels to establish your history.
            </div>
          ) : (
            <div className="max-h-64 overflow-y-auto divide-y divide-dark-700 font-mono text-xs">
              {ratingHistory.slice().reverse().map((rh) => (
                <div key={rh.id} className="py-2.5 flex items-center justify-between">
                  <div>
                    <span className="text-white font-semibold">VS {rh.opponentUsername || 'Opponent'}</span>
                    <span className="text-slate-500 ml-2 text-[11px]">({new Date(rh.createdAt).toLocaleDateString()})</span>
                  </div>
                  <div className="flex items-center space-x-3">
                    <span className="text-slate-400">{rh.oldRating} → <span className="text-white font-bold">{rh.newRating}</span></span>
                    <span className={`font-bold ${rh.ratingChange >= 0 ? 'text-emerald-400' : 'text-rose-400'}`}>
                      {rh.ratingChange >= 0 ? `+${rh.ratingChange}` : rh.ratingChange}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Difficulty Breakdown */}
        <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 space-y-4 shadow-xl">
          <div className="flex items-center space-x-2 text-sm font-bold text-white">
            <Target className="w-4 h-4 text-indigo-400" />
            <span>Solved by Difficulty</span>
          </div>

          <div className="space-y-4 pt-2">
            <div className="space-y-1.5 font-mono text-xs">
              <div className="flex justify-between">
                <span className="text-emerald-400 font-semibold">Easy</span>
                <span className="text-white">{difficultyStats.EASY || 0} solved</span>
              </div>
              <div className="w-full h-2.5 bg-dark-900 rounded-full overflow-hidden border border-dark-700">
                <div className="h-full bg-emerald-500" style={{ width: `${Math.min(100, ((difficultyStats.EASY || 0) / 250) * 100)}%` }}></div>
              </div>
            </div>

            <div className="space-y-1.5 font-mono text-xs">
              <div className="flex justify-between">
                <span className="text-amber-400 font-semibold">Medium</span>
                <span className="text-white">{difficultyStats.MEDIUM || 0} solved</span>
              </div>
              <div className="w-full h-2.5 bg-dark-900 rounded-full overflow-hidden border border-dark-700">
                <div className="h-full bg-amber-500" style={{ width: `${Math.min(100, ((difficultyStats.MEDIUM || 0) / 550) * 100)}%` }}></div>
              </div>
            </div>

            <div className="space-y-1.5 font-mono text-xs">
              <div className="flex justify-between">
                <span className="text-rose-400 font-semibold">Hard</span>
                <span className="text-white">{difficultyStats.HARD || 0} solved</span>
              </div>
              <div className="w-full h-2.5 bg-dark-900 rounded-full overflow-hidden border border-dark-700">
                <div className="h-full bg-rose-500" style={{ width: `${Math.min(100, ((difficultyStats.HARD || 0) / 200) * 100)}%` }}></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Unlocked Achievements Gallery */}
      <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 sm:p-8 space-y-6 shadow-2xl">
        <div className="flex items-center space-x-2 text-sm font-bold text-white">
          <Award className="w-4 h-4 text-amber-400" />
          <span>Unlocked Achievements & Medals ({achievements.length})</span>
        </div>

        {achievements.length === 0 ? (
          <div className="p-8 text-center text-xs text-slate-500 font-mono">
            No achievements unlocked yet. Win matches, establish streaks, and optimize your solutions to earn badges!
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {achievements.map((ach) => (
              <div
                key={ach.id}
                className="p-4 rounded-2xl bg-dark-900 border border-dark-700 flex items-start space-x-3.5"
              >
                <div className="w-10 h-10 rounded-xl bg-amber-500/20 text-amber-400 border border-amber-500/30 flex items-center justify-center flex-shrink-0">
                  <Award className="w-5 h-5" />
                </div>
                <div className="space-y-0.5 min-w-0">
                  <div className="text-xs font-bold text-white truncate">{ach.title}</div>
                  <div className="text-[11px] text-slate-400">{ach.description}</div>
                  <div className="text-[10px] font-mono text-cyan-400 pt-1">+{ach.points} Pts</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
