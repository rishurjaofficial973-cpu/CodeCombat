import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { authApi } from '../api';
import { Swords, Lock, User, AlertCircle, ArrowRight } from 'lucide-react';

export const Login = () => {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({ username: '', password: '' });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const res = await authApi.login(formData);
      if (res.data) {
        login(res.data);
        navigate('/dashboard');
      }
    } catch (err) {
      setError(err.message || 'Login failed. Check your username and password.');
    } finally {
      setLoading(false);
    }
  };

  const handleQuickDemo = (uname) => {
    setFormData({ username: uname, password: uname === 'admin' ? 'admin123' : 'password123' });
  };

  return (
    <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center p-4">
      <div className="max-w-md w-full bg-dark-800 border border-dark-600 rounded-3xl p-8 shadow-2xl space-y-6">
        <div className="text-center space-y-2">
          <div className="flex justify-center">
            <div className="w-12 h-12 rounded-2xl bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center text-cyan-400">
              <Swords className="w-6 h-6" />
            </div>
          </div>
          <h2 className="text-2xl font-bold text-white tracking-tight">
            Welcome to CodeDuel
          </h2>
          <p className="text-xs text-slate-400">
            Sign in to start matching and climb the competitive ladder
          </p>
        </div>

        {error && (
          <div className="p-3.5 rounded-xl bg-rose-950/40 border border-rose-500/30 text-rose-300 text-xs flex items-center gap-2">
            <AlertCircle className="w-4 h-4 flex-shrink-0" />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-semibold text-slate-300 mb-1.5">Username</label>
            <div className="relative">
              <input
                type="text"
                required
                value={formData.username}
                onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                placeholder="e.g. anshu"
                className="w-full bg-dark-900 border border-dark-600 rounded-xl px-3.5 py-2.5 pl-10 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors"
              />
              <User className="w-4 h-4 text-slate-500 absolute left-3.5 top-3" />
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-300 mb-1.5">Password</label>
            <div className="relative">
              <input
                type="password"
                required
                value={formData.password}
                onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                placeholder="••••••••"
                className="w-full bg-dark-900 border border-dark-600 rounded-xl px-3.5 py-2.5 pl-10 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors"
              />
              <Lock className="w-4 h-4 text-slate-500 absolute left-3.5 top-3" />
            </div>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full flex items-center justify-center gap-2 py-3 rounded-xl font-bold text-xs text-white bg-indigo-600 hover:bg-indigo-500 shadow-lg shadow-indigo-600/30 transition-all disabled:opacity-50"
          >
            <span>{loading ? 'Authenticating...' : 'Sign In'}</span>
            <ArrowRight className="w-4 h-4" />
          </button>
        </form>

        {/* Quick Demo Accounts */}
        <div className="pt-4 border-t border-dark-700 space-y-2">
          <div className="text-[11px] font-mono text-slate-500 text-center uppercase tracking-wider">
            Quick Demo Logins
          </div>
          <div className="flex items-center justify-center gap-2">
            <button
              type="button"
              onClick={() => handleQuickDemo('anshu')}
              className="px-2.5 py-1 text-xs font-mono rounded-lg bg-dark-900 hover:bg-dark-700 text-cyan-400 border border-dark-600 transition-colors"
            >
              Anshu (1542)
            </button>
            <button
              type="button"
              onClick={() => handleQuickDemo('rahul')}
              className="px-2.5 py-1 text-xs font-mono rounded-lg bg-dark-900 hover:bg-dark-700 text-amber-400 border border-dark-600 transition-colors"
            >
              Rahul (1518)
            </button>
            <button
              type="button"
              onClick={() => handleQuickDemo('admin')}
              className="px-2.5 py-1 text-xs font-mono rounded-lg bg-dark-900 hover:bg-dark-700 text-purple-400 border border-dark-600 transition-colors"
            >
              Admin (2100)
            </button>
          </div>
        </div>

        <div className="text-center text-xs text-slate-400">
          Don't have an account?{' '}
          <Link to="/register" className="text-cyan-400 hover:underline font-semibold">
            Create account
          </Link>
        </div>
      </div>
    </div>
  );
};
