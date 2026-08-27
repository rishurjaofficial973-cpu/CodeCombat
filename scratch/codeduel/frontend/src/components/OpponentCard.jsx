import React, { useState, useEffect } from 'react';
import { User, Activity, CheckCircle, XCircle, Clock, AlertTriangle, WifiOff, Loader2 } from 'lucide-react';

export const OpponentCard = ({ opponent, lastEvent }) => {
  const [status, setStatus] = useState(opponent?.status || 'CODING');
  const [testsPassed, setTestsPassed] = useState(opponent?.testsPassed || 0);
  const [totalTests, setTotalTests] = useState(opponent?.totalTests || 0);
  const [disconnectTimer, setDisconnectTimer] = useState(30);

  useEffect(() => {
    if (opponent) {
      setStatus(opponent.status || 'CODING');
      setTestsPassed(opponent.testsPassed || 0);
      setTotalTests(opponent.totalTests || 0);
    }
  }, [opponent]);

  useEffect(() => {
    if (!lastEvent) return;

    if (lastEvent.type === 'PLAYER_CODING' && lastEvent.senderId === opponent?.userId) {
      if (status !== 'ACCEPTED') setStatus('CODING');
    } else if (lastEvent.type === 'PLAYER_RUNNING' && lastEvent.senderId === opponent?.userId) {
      setStatus('RUNNING');
    } else if (lastEvent.type === 'PLAYER_ACCEPTED' && lastEvent.senderId === opponent?.userId) {
      setStatus('ACCEPTED');
      if (lastEvent.data?.testsPassed) setTestsPassed(lastEvent.data.testsPassed);
      if (lastEvent.data?.totalTests) setTotalTests(lastEvent.data.totalTests);
    } else if (lastEvent.type === 'PLAYER_WRONG' && lastEvent.senderId === opponent?.userId) {
      setStatus('WRONG_ANSWER');
      if (lastEvent.data?.testsPassed) setTestsPassed(lastEvent.data.testsPassed);
      if (lastEvent.data?.totalTests) setTotalTests(lastEvent.data.totalTests);
    } else if (lastEvent.type === 'PLAYER_DISCONNECTED' && lastEvent.senderId === opponent?.userId) {
      setStatus('DISCONNECTED');
      setDisconnectTimer(30);
    } else if (lastEvent.type === 'PLAYER_RECONNECTED' && lastEvent.senderId === opponent?.userId) {
      setStatus('CODING');
    }
  }, [lastEvent, opponent?.userId, status]);

  // Handle Disconnection countdown
  useEffect(() => {
    if (status !== 'DISCONNECTED') return;

    const interval = setInterval(() => {
      setDisconnectTimer((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);

    return () => clearInterval(interval);
  }, [status]);

  const getStatusBadge = () => {
    switch (status) {
      case 'RUNNING':
        return (
          <span className="flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-amber-500/20 text-amber-300 border border-amber-500/30 text-xs font-semibold animate-pulse">
            <Loader2 className="w-3.5 h-3.5 animate-spin" />
            <span>Running Code</span>
          </span>
        );
      case 'ACCEPTED':
        return (
          <span className="flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-emerald-500/20 text-emerald-300 border border-emerald-500/30 text-xs font-semibold neon-glow-emerald">
            <CheckCircle className="w-3.5 h-3.5 text-emerald-400" />
            <span>Accepted</span>
          </span>
        );
      case 'WRONG_ANSWER':
        return (
          <span className="flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-rose-500/20 text-rose-300 border border-rose-500/30 text-xs font-semibold">
            <XCircle className="w-3.5 h-3.5 text-rose-400" />
            <span>Wrong Answer</span>
          </span>
        );
      case 'TIME_LIMIT_EXCEEDED':
      case 'TLE':
        return (
          <span className="flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-orange-500/20 text-orange-300 border border-orange-500/30 text-xs font-semibold">
            <Clock className="w-3.5 h-3.5" />
            <span>Time Limit Exceeded</span>
          </span>
        );
      case 'DISCONNECTED':
        return (
          <span className="flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-slate-800 text-rose-400 border border-rose-500/40 text-xs font-semibold animate-pulse">
            <WifiOff className="w-3.5 h-3.5" />
            <span>Reconnecting ({disconnectTimer}s)</span>
          </span>
        );
      default:
        return (
          <span className="flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-emerald-950/40 text-emerald-400 border border-emerald-500/20 text-xs font-medium">
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping"></span>
            <span>Coding</span>
          </span>
        );
    }
  };

  return (
    <div className="bg-dark-800/90 border border-dark-600 rounded-2xl p-4 shadow-xl backdrop-blur-sm flex flex-col space-y-3">
      <div className="flex items-center justify-between">
        <div className="text-[11px] font-mono uppercase tracking-wider text-slate-400 font-bold flex items-center gap-1.5">
          <Activity className="w-3.5 h-3.5 text-cyan-400" />
          <span>Opponent Status</span>
        </div>
        {getStatusBadge()}
      </div>

      <div className="flex items-center space-x-3 bg-dark-900/60 p-3 rounded-xl border border-dark-700">
        <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-rose-500 to-amber-600 flex items-center justify-center font-bold text-white shadow-md">
          {opponent?.username?.charAt(0)?.toUpperCase() || 'O'}
        </div>
        <div className="flex-1 min-w-0">
          <div className="text-sm font-bold text-white truncate">{opponent?.username || 'Opponent'}</div>
          <div className="text-xs font-mono text-cyan-400 flex items-center gap-1 font-semibold">
            <span>Rating:</span>
            <span className="text-white">{opponent?.ratingBefore || 1500}</span>
          </div>
        </div>
      </div>

      {/* Progress Telemetry */}
      {totalTests > 0 && (
        <div className="space-y-1.5">
          <div className="flex justify-between text-xs text-slate-400">
            <span>Tests Passed:</span>
            <span className="font-mono font-bold text-slate-200">{testsPassed} / {totalTests}</span>
          </div>
          <div className="w-full h-2 bg-dark-900 rounded-full overflow-hidden border border-dark-700">
            <div
              className={`h-full transition-all duration-500 ${
                testsPassed === totalTests ? 'bg-emerald-500 neon-glow-emerald' : 'bg-cyan-500'
              }`}
              style={{ width: `${(testsPassed / totalTests) * 100}%` }}
            ></div>
          </div>
        </div>
      )}

      {status === 'DISCONNECTED' && (
        <div className="p-2.5 rounded-lg bg-rose-950/40 border border-rose-500/30 text-rose-300 text-xs flex items-center gap-2">
          <AlertTriangle className="w-4 h-4 flex-shrink-0" />
          <span>Opponent disconnected. If they fail to reconnect in {disconnectTimer}s, match will be awarded to you.</span>
        </div>
      )}
    </div>
  );
};
