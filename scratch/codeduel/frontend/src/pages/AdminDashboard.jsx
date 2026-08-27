import React, { useState, useEffect } from 'react';
import { adminApi, problemApi } from '../api';
import { Shield, Database, Users, Swords, Plus, Trash2, Edit3, Ban, CheckCircle, Search } from 'lucide-react';

export const AdminDashboard = () => {
  const [stats, setStats] = useState(null);
  const [users, setUsers] = useState([]);
  const [problems, setProblems] = useState([]);
  const [activeTab, setActiveTab] = useState('STATS');
  const [loading, setLoading] = useState(true);

  // New problem form
  const [showAddProblem, setShowAddProblem] = useState(false);
  const [newProblem, setNewProblem] = useState({
    title: '',
    difficulty: 'MEDIUM',
    topics: 'Array,Hash Table',
    description: '',
    constraints: '',
    expectedTimeComplexity: 'O(n)',
    expectedSpaceComplexity: 'O(1)',
  });

  useEffect(() => {
    loadAdminData();
  }, []);

  const loadAdminData = async () => {
    try {
      const [statsRes, usersRes, probsRes] = await Promise.allSettled([
        adminApi.getStats(),
        adminApi.getUsers(0, 20),
        problemApi.getProblems({ page: 0, size: 20 })
      ]);

      if (statsRes.status === 'fulfilled' && statsRes.value?.data) {
        setStats(statsRes.value.data);
      }
      if (usersRes.status === 'fulfilled' && usersRes.value?.data?.content) {
        setUsers(usersRes.value.data.content);
      }
      if (probsRes.status === 'fulfilled' && probsRes.value?.data?.content) {
        setProblems(probsRes.value.data.content);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleToggleBan = async (userId) => {
    try {
      await adminApi.toggleBan(userId);
      setUsers(prev => prev.map(u => u.id === userId ? { ...u, isBanned: !u.isBanned } : u));
    } catch (err) {
      console.error(err);
    }
  };

  const handleDeleteProblem = async (problemId) => {
    if (window.confirm(`Are you sure you want to deactivate problem ${problemId}?`)) {
      try {
        await adminApi.deleteProblem(problemId);
        setProblems(prev => prev.filter(p => p.id !== problemId));
      } catch (err) {
        console.error(err);
      }
    }
  };

  const handleCreateProblemSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await adminApi.createProblem(newProblem);
      if (res.data) {
        setProblems([res.data, ...problems]);
        setShowAddProblem(false);
        setNewProblem({
          title: '',
          difficulty: 'MEDIUM',
          topics: 'Array,Hash Table',
          description: '',
          constraints: '',
          expectedTimeComplexity: 'O(n)',
          expectedSpaceComplexity: 'O(1)',
        });
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8 animate-fade-in">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-amber-500/10 border border-amber-500/30 text-amber-400 text-xs font-mono mb-2">
            <Shield className="w-4 h-4" />
            <span>Platform Administration</span>
          </div>
          <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
            Admin Dashboard
          </h1>
          <p className="text-xs sm:text-sm text-slate-400">
            System metrics, question bank management, and user moderation
          </p>
        </div>

        <div className="flex items-center space-x-2">
          <button
            onClick={() => setShowAddProblem(true)}
            className="flex items-center gap-2 px-4 py-2 rounded-xl text-xs font-bold text-white bg-indigo-600 hover:bg-indigo-500 transition-colors shadow-lg"
          >
            <Plus className="w-4 h-4" />
            <span>Add Problem</span>
          </button>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 font-mono">
        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Total Problems</div>
          <div className="text-2xl font-black text-cyan-400">{stats?.totalProblems || 1000}</div>
          <div className="text-[11px] text-slate-500 font-sans">
            {stats?.easyProblems || 250} Easy / {stats?.mediumProblems || 550} Med / {stats?.hardProblems || 200} Hard
          </div>
        </div>

        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Registered Users</div>
          <div className="text-2xl font-black text-indigo-400">{stats?.totalUsers || 0}</div>
          <div className="text-[11px] text-slate-500 font-sans">Platform contenders</div>
        </div>

        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Total Duels</div>
          <div className="text-2xl font-black text-emerald-400">{stats?.totalMatches || 0}</div>
          <div className="text-[11px] text-slate-500 font-sans">{stats?.activeMatches || 0} currently active</div>
        </div>

        <div className="p-5 rounded-2xl bg-dark-800 border border-dark-600 space-y-1">
          <div className="text-xs text-slate-400 font-sans">Submissions Judged</div>
          <div className="text-2xl font-black text-purple-400">{stats?.totalSubmissions || 0}</div>
          <div className="text-[11px] text-slate-500 font-sans">Evaluated via sandbox</div>
        </div>
      </div>

      {/* Tab Navigation */}
      <div className="flex items-center space-x-2 border-b border-dark-700 pb-2">
        <button
          onClick={() => setActiveTab('PROBLEMS')}
          className={`px-4 py-2 rounded-xl text-xs font-bold transition-colors ${
            activeTab === 'PROBLEMS' ? 'bg-dark-800 text-cyan-400 border border-dark-600' : 'text-slate-400 hover:text-white'
          }`}
        >
          Problem Management ({stats?.totalProblems || 1000})
        </button>
        <button
          onClick={() => setActiveTab('USERS')}
          className={`px-4 py-2 rounded-xl text-xs font-bold transition-colors ${
            activeTab === 'USERS' ? 'bg-dark-800 text-indigo-400 border border-dark-600' : 'text-slate-400 hover:text-white'
          }`}
        >
          User Moderation ({users.length})
        </button>
      </div>

      {/* Tab 1: Problem Management */}
      {activeTab === 'PROBLEMS' && (
        <div className="bg-dark-800 border border-dark-600 rounded-3xl overflow-hidden shadow-2xl">
          <div className="divide-y divide-dark-700">
            {problems.map((p) => (
              <div key={p.id} className="p-4 sm:px-6 flex items-center justify-between gap-4 text-xs font-mono">
                <div className="space-y-1 min-w-0">
                  <div className="flex items-center space-x-2">
                    <span className="font-bold text-cyan-400">{p.id}</span>
                    <span className="text-white font-bold font-sans text-sm truncate">{p.title}</span>
                    <span className="text-[10px] px-2 py-0.5 rounded bg-dark-900 border border-dark-700 text-slate-400">
                      {p.difficulty}
                    </span>
                  </div>
                  <div className="text-slate-400">{p.topics}</div>
                </div>

                <div className="flex items-center space-x-2">
                  <button
                    onClick={() => handleDeleteProblem(p.id)}
                    className="p-2 text-rose-400 hover:bg-rose-950/30 rounded-lg transition-colors"
                    title="Deactivate Problem"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Tab 2: User Moderation */}
      {activeTab === 'USERS' && (
        <div className="bg-dark-800 border border-dark-600 rounded-3xl overflow-hidden shadow-2xl">
          <div className="divide-y divide-dark-700">
            {users.map((u) => (
              <div key={u.id} className="p-4 sm:px-6 flex items-center justify-between gap-4 text-xs font-mono">
                <div className="space-y-1">
                  <div className="flex items-center space-x-2">
                    <span className="text-white font-bold font-sans text-sm">{u.username}</span>
                    <span className="text-slate-500">({u.email})</span>
                    <span className="text-cyan-400 font-bold">Rating: {u.rating}</span>
                  </div>
                  <div className="text-slate-400">
                    {u.matchesPlayed} Matches • {u.wins} Wins • Role: {u.role}
                  </div>
                </div>

                <div className="flex items-center space-x-2">
                  <button
                    onClick={() => handleToggleBan(u.id)}
                    className={`px-3 py-1.5 rounded-lg text-xs font-bold font-sans transition-colors ${
                      u.isBanned ? 'bg-emerald-600 text-white' : 'bg-rose-600 text-white'
                    }`}
                  >
                    {u.isBanned ? 'Unban User' : 'Ban User'}
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Add Problem Modal */}
      {showAddProblem && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-dark-900/90 backdrop-blur-xl animate-fade-in">
          <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 sm:p-8 max-w-xl w-full shadow-2xl space-y-4 max-h-[90vh] overflow-y-auto">
            <h2 className="text-xl font-bold text-white">Add Curated Problem</h2>
            <form onSubmit={handleCreateProblemSubmit} className="space-y-3 font-mono text-xs">
              <div>
                <label className="block text-slate-300 font-sans mb-1">Problem Title</label>
                <input
                  type="text"
                  required
                  value={newProblem.title}
                  onChange={(e) => setNewProblem({ ...newProblem, title: e.target.value })}
                  placeholder="e.g. Subarray Sum Equals Target"
                  className="w-full bg-dark-900 border border-dark-600 rounded-xl p-2.5 text-white"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="block text-slate-300 font-sans mb-1">Difficulty</label>
                  <select
                    value={newProblem.difficulty}
                    onChange={(e) => setNewProblem({ ...newProblem, difficulty: e.target.value })}
                    className="w-full bg-dark-900 border border-dark-600 rounded-xl p-2.5 text-white"
                  >
                    <option value="EASY">EASY</option>
                    <option value="MEDIUM">MEDIUM</option>
                    <option value="HARD">HARD</option>
                  </select>
                </div>

                <div>
                  <label className="block text-slate-300 font-sans mb-1">Topics</label>
                  <input
                    type="text"
                    value={newProblem.topics}
                    onChange={(e) => setNewProblem({ ...newProblem, topics: e.target.value })}
                    placeholder="Array,Hash Table"
                    className="w-full bg-dark-900 border border-dark-600 rounded-xl p-2.5 text-white"
                  />
                </div>
              </div>

              <div>
                <label className="block text-slate-300 font-sans mb-1">Description</label>
                <textarea
                  rows={4}
                  required
                  value={newProblem.description}
                  onChange={(e) => setNewProblem({ ...newProblem, description: e.target.value })}
                  placeholder="Problem description and task requirements..."
                  className="w-full bg-dark-900 border border-dark-600 rounded-xl p-2.5 text-white font-sans"
                />
              </div>

              <div className="flex justify-end gap-2 pt-3 border-t border-dark-700">
                <button
                  type="button"
                  onClick={() => setShowAddProblem(false)}
                  className="px-4 py-2 rounded-xl text-slate-300 bg-dark-700 hover:bg-dark-600 font-sans"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-5 py-2 rounded-xl text-white font-bold bg-indigo-600 hover:bg-indigo-500 font-sans shadow-lg"
                >
                  Create Problem
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
