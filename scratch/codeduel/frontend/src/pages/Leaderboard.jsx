import React, { useState, useEffect } from 'react';
import { leaderboardApi } from '../api';
import { useAuth } from '../context/AuthContext';
import { Trophy, Flame, Search, Medal, Shield, Crown } from 'lucide-react';

export const Leaderboard = () => {
  const { user } = useAuth();
  const [leaderboard, setLeaderboard] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadLeaderboard();
  }, []);

  const loadLeaderboard = async () => {
    try {
      const res = await leaderboardApi.getLeaderboard(100);
      if (res.data) {
        setLeaderboard(res.data);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const getTier = (rating) => {
    if (rating >= 2200) return { name: 'Grandmaster', color: 'text-rose-400 bg-rose-500/10 border-rose-500/30' };
    if (rating >= 1900) return { name: 'Master', color: 'text-purple-400 bg-purple-500/10 border-purple-500/30' };
    if (rating >= 1600) return { name: 'Diamond', color: 'text-cyan-400 bg-cyan-500/10 border-cyan-500/30' };
    if (rating >= 1300) return { name: 'Gold', color: 'text-amber-400 bg-amber-500/10 border-amber-500/30' };
    return { name: 'Silver', color: 'text-slate-300 bg-slate-500/10 border-slate-500/30' };
  };

  const filtered = leaderboard.filter(entry =>
    entry.username.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6 animate-fade-in">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-amber-500/10 border border-amber-500/30 text-amber-400 text-xs font-mono mb-2">
            <Trophy className="w-4 h-4" />
            <span>Global Elo Rankings</span>
          </div>
          <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
            Competitive Leaderboard
          </h1>
          <p className="text-xs sm:text-sm text-slate-400">
            Top ranked programmers on the CodeDuel arena
          </p>
        </div>

        {/* Search */}
        <div className="relative w-full sm:w-72">
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search contender by username..."
            className="w-full bg-dark-800 border border-dark-600 rounded-xl px-4 py-2 pl-10 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500"
          />
          <Search className="w-4 h-4 text-slate-500 absolute left-3.5 top-2.5" />
        </div>
      </div>

      {/* Leaderboard Table Card */}
      <div className="bg-dark-800 border border-dark-600 rounded-3xl overflow-hidden shadow-2xl">
        {loading ? (
          <div className="p-12 text-center text-slate-400 font-mono text-xs">
            <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-amber-400 mx-auto mb-3"></div>
            Loading leaderboard standings...
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left font-mono text-xs">
              <thead className="bg-dark-900/80 text-slate-400 uppercase text-[11px] border-b border-dark-700">
                <tr>
                  <th className="py-3.5 px-4 sm:px-6">Rank</th>
                  <th className="py-3.5 px-4">Contender</th>
                  <th className="py-3.5 px-4">Tier</th>
                  <th className="py-3.5 px-4">Rating</th>
                  <th className="py-3.5 px-4">Win Rate</th>
                  <th className="py-3.5 px-4">Wins / Losses</th>
                  <th className="py-3.5 px-4 text-right">Streak</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-dark-700">
                {filtered.map((entry) => {
                  const isMe = user && entry.userId === user.id;
                  const tier = getTier(entry.rating);

                  return (
                    <tr
                      key={entry.userId}
                      className={`transition-colors ${
                        isMe ? 'bg-indigo-950/40 font-bold' : 'hover:bg-dark-700/50'
                      }`}
                    >
                      <td className="py-4 px-4 sm:px-6">
                        <div className="flex items-center space-x-2">
                          {entry.rank === 1 ? (
                            <span className="text-amber-400 font-bold text-sm flex items-center gap-1">
                              <Crown className="w-4 h-4 fill-amber-400" /> #1
                            </span>
                          ) : entry.rank === 2 ? (
                            <span className="text-slate-300 font-bold text-sm">#2</span>
                          ) : entry.rank === 3 ? (
                            <span className="text-amber-600 font-bold text-sm">#3</span>
                          ) : (
                            <span className="text-slate-500">#{entry.rank}</span>
                          )}
                        </div>
                      </td>

                      <td className="py-4 px-4">
                        <div className="flex items-center space-x-2.5">
                          <div className="w-7 h-7 rounded-lg bg-dark-700 flex items-center justify-center font-bold text-white uppercase text-[11px]">
                            {entry.username.charAt(0)}
                          </div>
                          <span className="text-white font-sans font-semibold">
                            {entry.username} {isMe && <span className="text-cyan-400 text-xs">(You)</span>}
                          </span>
                        </div>
                      </td>

                      <td className="py-4 px-4">
                        <span className={`px-2.5 py-0.5 rounded-full text-[10px] font-semibold border ${tier.color}`}>
                          {tier.name}
                        </span>
                      </td>

                      <td className="py-4 px-4 text-cyan-400 font-bold text-sm">
                        {entry.rating}
                      </td>

                      <td className="py-4 px-4 text-emerald-400 font-semibold">
                        {entry.winRate}%
                      </td>

                      <td className="py-4 px-4 text-slate-400">
                        <span className="text-white font-semibold">{entry.wins}</span> W / {entry.losses} L
                      </td>

                      <td className="py-4 px-4 text-right">
                        <span className="inline-flex items-center gap-1 text-amber-400 font-bold">
                          <Flame className="w-3.5 h-3.5 fill-amber-400" />
                          <span>{entry.winStreak}</span>
                        </span>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};
