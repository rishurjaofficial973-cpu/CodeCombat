import React, { useState, useEffect } from 'react';
import { Timer, AlertCircle } from 'lucide-react';

export const ServerTimer = ({ initialRemainingSeconds = 900, onTimeUp }) => {
  const [seconds, setSeconds] = useState(initialRemainingSeconds);

  useEffect(() => {
    setSeconds(initialRemainingSeconds);
  }, [initialRemainingSeconds]);

  useEffect(() => {
    if (seconds <= 0) {
      if (onTimeUp) onTimeUp();
      return;
    }

    const interval = setInterval(() => {
      setSeconds((prev) => {
        if (prev <= 1) {
          clearInterval(interval);
          if (onTimeUp) onTimeUp();
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(interval);
  }, [seconds, onTimeUp]);

  const formatTime = (secs) => {
    const mins = Math.floor(secs / 60);
    const remainingSecs = secs % 60;
    return `${mins.toString().padStart(2, '0')}:${remainingSecs.toString().padStart(2, '0')}`;
  };

  const isLowTime = seconds < 180; // < 3 mins
  const isCriticalTime = seconds < 60; // < 1 min

  return (
    <div
      className={`flex items-center space-x-2 px-4 py-2 rounded-xl border transition-all duration-300 font-mono ${
        isCriticalTime
          ? 'bg-rose-950/40 border-rose-500/60 text-rose-400 animate-pulse neon-glow-rose'
          : isLowTime
          ? 'bg-amber-950/30 border-amber-500/40 text-amber-300'
          : 'bg-dark-800 border-dark-600 text-slate-200'
      }`}
    >
      <Timer className={`w-4 h-4 ${isCriticalTime ? 'text-rose-400 animate-spin' : isLowTime ? 'text-amber-400' : 'text-cyan-400'}`} />
      <span className="font-bold text-lg tracking-wider">
        {formatTime(seconds)}
      </span>
      {isCriticalTime && (
        <span className="text-[10px] font-sans font-bold bg-rose-500/30 text-rose-300 px-1.5 py-0.5 rounded">
          TIME RUNNING OUT
        </span>
      )}
    </div>
  );
};
