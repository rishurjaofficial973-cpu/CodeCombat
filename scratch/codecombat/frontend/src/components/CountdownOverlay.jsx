import React, { useState, useEffect } from 'react';
import { Swords, Zap } from 'lucide-react';

export const CountdownOverlay = ({ onComplete, initialSeconds = 3 }) => {
  const [count, setCount] = useState(initialSeconds);

  useEffect(() => {
    if (count <= 0) {
      const timer = setTimeout(() => {
        if (onComplete) onComplete();
      }, 700);
      return () => clearTimeout(timer);
    }

    const interval = setInterval(() => {
      setCount((prev) => prev - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, [count, onComplete]);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-dark-900/90 backdrop-blur-xl animate-fade-in">
      <div className="text-center">
        <div className="flex justify-center mb-6">
          <div className="p-4 rounded-3xl bg-indigo-500/10 border border-indigo-500/30 text-cyan-400 animate-bounce">
            <Swords className="w-16 h-16" />
          </div>
        </div>

        <h2 className="text-2xl font-mono uppercase tracking-widest text-slate-400 mb-4 font-bold">
          Match Starting In
        </h2>

        <div className="relative flex items-center justify-center">
          <div className="text-9xl font-black font-mono tracking-tighter bg-gradient-to-r from-cyan-400 via-indigo-400 to-purple-500 bg-clip-text text-transparent transform scale-110 transition-transform duration-300">
            {count > 0 ? count : 'GO!'}
          </div>
        </div>

        <p className="mt-8 text-sm font-mono text-cyan-300 flex items-center justify-center gap-2">
          <Zap className="w-4 h-4 text-amber-400" />
          Optimize for Correctness & Efficiency!
        </p>
      </div>
    </div>
  );
};
