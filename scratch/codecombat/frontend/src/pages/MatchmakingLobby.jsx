import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useWebSocket } from '../context/WebSocketContext';
import { matchApi } from '../api';
import { CountdownOverlay } from '../components/CountdownOverlay';
import { Swords, Radar, X, Zap, Shield, ChevronDown, Filter } from 'lucide-react';

const TOPIC_OPTIONS = [
  'All Topics',
  'Arrays & Hashing',
  'Two Pointers & Sliding Window',
  'Binary Search',
  'Strings',
  'Linked List',
  'Stack & Monotonic Stack',
  'Trees & BST',
  'Heap / Priority Queue',
  'Greedy',
  'Backtracking',
  'Graphs',
  'Dynamic Programming',
  'Bit Manipulation',
];

export const MatchmakingLobby = () => {
  const { user } = useAuth();
  const { subscribe } = useWebSocket();
  const navigate = useNavigate();

  const [searching, setSearching] = useState(false);
  const [elapsedSeconds, setElapsedSeconds] = useState(0);
  const [mode, setMode] = useState('SCORE');
  const [difficulty, setDifficulty] = useState('');
  const [topic, setTopic] = useState('All Topics');
  const [matchedGame, setMatchedGame] = useState(null);
  const [showCountdown, setShowCountdown] = useState(false);

  const timerRef = useRef(null);

  // Subscribe to user matchmaking notifications
  useEffect(() => {
    const unsubscribe = subscribe('/user/queue/matchmaking', (event) => {
      console.log('Matchmaking event received:', event);
      if (event.type === 'MATCH_FOUND' && event.data) {
        setSearching(false);
        setMatchedGame(event.data);
        setShowCountdown(true);
      }
    });

    return () => {
      if (unsubscribe) unsubscribe();
    };
  }, [subscribe]);

  // Elapsed timer tick
  useEffect(() => {
    if (searching) {
      setElapsedSeconds(0);
      timerRef.current = setInterval(() => {
        setElapsedSeconds((prev) => prev + 1);
      }, 1000);
    } else {
      if (timerRef.current) clearInterval(timerRef.current);
    }

    return () => {
      if (timerRef.current) clearInterval(timerRef.current);
    };
  }, [searching]);

  const handleStartSearch = async () => {
    try {
      setSearching(true);
      const req = {
        mode,
        preferredDifficulty: difficulty || null,
        preferredTopic: topic !== 'All Topics' ? topic : null,
      };
      await matchApi.findMatch(req);
    } catch (err) {
      console.error('Failed to start matchmaking:', err);
      setSearching(false);
    }
  };

  const handleCancelSearch = async () => {
    try {
      await matchApi.cancelMatch();
    } catch (err) {
      console.error(err);
    } finally {
      setSearching(false);
      setElapsedSeconds(0);
    }
  };

  const getCurrentRatingRange = () => {
    const userRating = user?.rating || 1200;
    let delta = 50;
    if (elapsedSeconds >= 30) delta = 500;
    else if (elapsedSeconds >= 20) delta = 200;
    else if (elapsedSeconds >= 10) delta = 100;

    return {
      min: Math.max(100, userRating - delta),
      max: userRating + delta,
      delta,
    };
  };

  const range = getCurrentRatingRange();

  const handleCountdownFinished = () => {
    if (matchedGame?.id) {
      navigate(`/versus/${matchedGame.id}`);
    }
  };

  const opponent = matchedGame?.players?.find((p) => p.userId !== user?.id);

  return (
    <div className="max-w-4xl mx-auto px-4 py-12">
      {/* 3...2...1...GO Countdown Overlay */}
      {showCountdown && (
        <CountdownOverlay onComplete={handleCountdownFinished} initialSeconds={3} />
      )}

      <div className="bg-dark-800 border border-dark-600 rounded-3xl p-8 sm:p-12 shadow-2xl space-y-8 text-center relative overflow-hidden">
        {/* Radar Background Glow */}
        {searching && (
          <div className="absolute inset-0 bg-[radial-gradient(circle_at_center,_var(--tw-gradient-stops))] from-cyan-950/40 via-dark-900/60 to-transparent pointer-events-none"></div>
        )}

        {/* Header */}
        <div className="space-y-2 relative z-10">
          <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-indigo-500/10 border border-indigo-500/30 text-cyan-400 text-xs font-mono">
            <Swords className="w-4 h-4" />
            <span>Ranked 1v1 Matchmaking</span>
          </div>
          <h1 className="text-3xl sm:text-4xl font-extrabold text-white tracking-tight">
            {searching ? 'Finding Opponent...' : '1v1 Versus Arena'}
          </h1>
          <p className="text-xs sm:text-sm text-slate-400 max-w-lg mx-auto">
            {searching
              ? 'Searching for a competitive match near your rating window...'
              : 'Configure your preferences and jump straight into real-time battle.'}
          </p>
        </div>

        {/* Dynamic Radar Scanner or Match Found Card */}
        {matchedGame ? (
          <div className="p-6 rounded-2xl bg-dark-900 border border-emerald-500/40 space-y-6 animate-scale-up relative z-10">
            <div className="text-xs font-mono uppercase tracking-widest text-emerald-400 font-bold">
              MATCH FOUND!
            </div>

            <div className="grid grid-cols-3 items-center gap-4">
              <div className="text-center space-y-1">
                <div className="w-14 h-14 mx-auto rounded-2xl bg-indigo-600 flex items-center justify-center font-bold text-lg text-white uppercase shadow-lg">
                  {user?.username?.charAt(0) || 'Y'}
                </div>
                <div className="text-sm font-bold text-white truncate">{user?.username}</div>
                <div className="text-xs font-mono text-cyan-400 font-bold">⚡ {user?.rating || 1500}</div>
              </div>

              <div className="text-2xl font-black font-mono text-amber-400 animate-pulse">
                VS
              </div>

              <div className="text-center space-y-1">
                <div className="w-14 h-14 mx-auto rounded-2xl bg-rose-600 flex items-center justify-center font-bold text-lg text-white uppercase shadow-lg">
                  {opponent?.username?.charAt(0) || 'O'}
                </div>
                <div className="text-sm font-bold text-white truncate">{opponent?.username || 'Opponent'}</div>
                <div className="text-xs font-mono text-cyan-400 font-bold">⚡ {opponent?.ratingBefore || 1500}</div>
              </div>
            </div>

            <div className="text-xs font-mono text-slate-400">
              Problem: <span className="text-white font-bold">{matchedGame?.problem?.title}</span>
            </div>
          </div>
        ) : searching ? (
          <div className="py-8 space-y-6 relative z-10">
            {/* Animated Radar Pulse */}
            <div className="relative w-40 h-40 mx-auto flex items-center justify-center">
              <div className="absolute inset-0 rounded-full border border-cyan-500/20 animate-ping"></div>
              <div className="absolute inset-2 rounded-full border border-indigo-500/30"></div>
              <div className="absolute inset-8 rounded-full border border-cyan-500/40"></div>
              <div className="w-20 h-20 rounded-full bg-dark-900 border border-cyan-500/60 flex items-center justify-center shadow-lg shadow-cyan-500/20">
                <Radar className="w-10 h-10 text-cyan-400 animate-spin" style={{ animationDuration: '3s' }} />
              </div>
            </div>

            {/* Live Search Metadata */}
            <div className="space-y-2 font-mono text-xs">
              <div className="text-slate-400">
                Elapsed Time: <span className="text-white font-bold">{elapsedSeconds}s</span>
              </div>
              <div className="text-slate-400">
                Your Rating: <span className="text-cyan-400 font-bold">{user?.rating || 1200}</span>
              </div>
              <div className="text-slate-400">
                Target Rating Window:{' '}
                <span className="text-emerald-400 font-bold">
                  {range.min} - {range.max} (±{range.delta})
                </span>
              </div>
            </div>

            <button
              onClick={handleCancelSearch}
              className="inline-flex items-center gap-2 px-6 py-2.5 rounded-xl font-bold text-xs text-rose-400 bg-dark-900 hover:bg-rose-950/30 border border-rose-500/30 transition-colors"
            >
              <X className="w-4 h-4" />
              <span>Cancel Search</span>
            </button>
          </div>
        ) : (
          <div className="space-y-6 relative z-10">
            {/* Preference Controls */}
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 text-left font-mono text-xs">
              {/* Match Mode */}
              <div className="bg-dark-900 p-4 rounded-2xl border border-dark-700 space-y-2">
                <label className="text-slate-400 font-sans font-semibold">Duel Mode</label>
                <select
                  value={mode}
                  onChange={(e) => setMode(e.target.value)}
                  className="w-full bg-dark-800 border border-dark-600 text-white rounded-xl p-2.5 focus:outline-none focus:border-cyan-500 cursor-pointer"
                >
                  <option value="SCORE">Score Mode (Efficiency + Speed)</option>
                  <option value="CLASSIC">Classic Mode (First Accepted)</option>
                  <option value="SUDDEN_DEATH">Sudden Death (Instant Finish)</option>
                </select>
              </div>

              {/* Difficulty Preference */}
              <div className="bg-dark-900 p-4 rounded-2xl border border-dark-700 space-y-2">
                <label className="text-slate-400 font-sans font-semibold">Difficulty</label>
                <select
                  value={difficulty}
                  onChange={(e) => setDifficulty(e.target.value)}
                  className="w-full bg-dark-800 border border-dark-600 text-white rounded-xl p-2.5 focus:outline-none focus:border-cyan-500 cursor-pointer"
                >
                  <option value="">Auto (Based on Rating)</option>
                  <option value="EASY">Easy</option>
                  <option value="MEDIUM">Medium</option>
                  <option value="HARD">Hard</option>
                </select>
              </div>

              {/* Topic Preference */}
              <div className="bg-dark-900 p-4 rounded-2xl border border-dark-700 space-y-2">
                <label className="text-slate-400 font-sans font-semibold">Topic Category</label>
                <select
                  value={topic}
                  onChange={(e) => setTopic(e.target.value)}
                  className="w-full bg-dark-800 border border-dark-600 text-white rounded-xl p-2.5 focus:outline-none focus:border-cyan-500 cursor-pointer"
                >
                  {TOPIC_OPTIONS.map((t) => (
                    <option key={t} value={t}>{t}</option>
                  ))}
                </select>
              </div>
            </div>

            {/* Launch Search Button */}
            <button
              onClick={handleStartSearch}
              className="w-full py-4 rounded-2xl font-black text-sm text-white bg-gradient-to-r from-indigo-600 via-cyan-600 to-teal-500 hover:scale-[1.02] shadow-xl shadow-cyan-500/25 transition-all duration-300 font-mono tracking-wider uppercase flex items-center justify-center gap-2"
            >
              <Swords className="w-5 h-5" />
              <span>FIND OPPONENT</span>
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
