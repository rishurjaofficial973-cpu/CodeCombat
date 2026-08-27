import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useWebSocket } from '../context/WebSocketContext';
import { matchApi, submissionApi } from '../api';
import { ProblemStatement } from '../components/ProblemStatement';
import { MonacoCodeEditor } from '../components/MonacoCodeEditor';
import { TestResultsPanel } from '../components/TestResultsPanel';
import { OpponentCard } from '../components/OpponentCard';
import { ServerTimer } from '../components/ServerTimer';
import { CountdownOverlay } from '../components/CountdownOverlay';
import { MatchResultModal } from '../components/MatchResultModal';
import { PostMatchAnalysis } from '../components/PostMatchAnalysis';
import { Swords, Zap, Activity, BarChart2 } from 'lucide-react';

export const VersusArena = () => {
  const { matchId } = useParams();
  const { user } = useAuth();
  const { subscribe, send } = useWebSocket();
  const navigate = useNavigate();

  const [match, setMatch] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [running, setRunning] = useState(false);
  const [latestResult, setLatestResult] = useState(null);
  const [lastWsEvent, setLastWsEvent] = useState(null);
  const [matchResult, setMatchResult] = useState(null);
  const [showAnalysis, setShowAnalysis] = useState(false);
  const [countdownRemaining, setCountdownRemaining] = useState(null);

  // Fetch match state on load
  useEffect(() => {
    const fetchMatch = async () => {
      try {
        const res = await matchApi.getMatch(matchId);
        if (res.data) {
          setMatch(res.data);
          if (res.data.status === 'COMPLETED') {
            const resultRes = await matchApi.getMatchResult(matchId);
            if (resultRes.data) setMatchResult(resultRes.data);
          }
        }
      } catch (err) {
        console.error('Failed to load match:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchMatch();
  }, [matchId]);

  // Subscribe to match topic
  useEffect(() => {
    if (!matchId) return;

    const topic = `/topic/match.${matchId}`;
    const unsubscribe = subscribe(topic, (event) => {
      console.log('Received match event:', event);
      setLastWsEvent(event);

      if (event.type === 'COUNTDOWN') {
        setCountdownRemaining(event.data || 3);
      } else if (event.type === 'MATCH_START') {
        setCountdownRemaining(null);
        if (event.data) setMatch(event.data);
      } else if (event.type === 'MATCH_FINISHED') {
        setMatchResult(event.data);
      }
    });

    return () => {
      if (unsubscribe) unsubscribe();
    };
  }, [matchId, subscribe]);

  const handleTypingHeartbeat = () => {
    if (user && matchId) {
      send(`/app/match/${matchId}/typing`, {
        userId: user.id,
        username: user.username,
      });
    }
  };

  const handleRunCode = async (language, sourceCode) => {
    if (!match?.problem?.id) return;
    setRunning(true);
    try {
      const res = await submissionApi.submitCode({
        matchId: null, // Practice / local run against public cases
        problemId: match.problem.id,
        language,
        sourceCode,
        isPractice: true,
      });
      if (res.data) {
        setLatestResult(res.data);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setRunning(false);
    }
  };

  const handleSubmitSolution = async (language, sourceCode) => {
    if (!match?.problem?.id || !matchId) return;
    setSubmitting(true);
    try {
      const res = await submissionApi.submitCode({
        matchId,
        problemId: match.problem.id,
        language,
        sourceCode,
        isPractice: false,
      });
      if (res.data) {
        setLatestResult(res.data);
      }
    } catch (err) {
      console.error('Submission failed:', err);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center bg-dark-900 text-slate-400 font-mono">
        <div className="flex items-center space-x-3">
          <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-cyan-400"></div>
          <span>Loading battle arena & problem data...</span>
        </div>
      </div>
    );
  }

  if (!match) {
    return (
      <div className="min-h-[calc(100vh-4rem)] flex flex-col items-center justify-center p-6 text-center space-y-4">
        <div className="text-xl font-bold text-white">Match Not Found</div>
        <button
          onClick={() => navigate('/versus')}
          className="px-6 py-2.5 rounded-xl font-bold text-xs text-white bg-indigo-600 hover:bg-indigo-500"
        >
          Return to Lobby
        </button>
      </div>
    );
  }

  const opponent = match?.players?.find((p) => p.userId !== user?.id);

  return (
    <div className="h-[calc(100vh-4rem)] flex flex-col bg-dark-900 text-slate-100 overflow-hidden">
      {/* Countdown overlay if active */}
      {countdownRemaining != null && (
        <CountdownOverlay
          onComplete={() => setCountdownRemaining(null)}
          initialSeconds={countdownRemaining}
        />
      )}

      {/* Result Modal / Post Match Analysis */}
      {matchResult && !showAnalysis && (
        <MatchResultModal
          result={matchResult}
          currentUserId={user?.id}
          onClose={() => setMatchResult(null)}
          onRematch={() => navigate('/versus')}
          onViewAnalysis={() => setShowAnalysis(true)}
        />
      )}

      {/* Match Top Bar */}
      <div className="flex items-center justify-between px-4 sm:px-6 py-2.5 bg-dark-800 border-b border-dark-700 flex-shrink-0">
        <div className="flex items-center space-x-3">
          <div className="flex items-center gap-1.5 font-mono font-bold text-xs text-cyan-400">
            <Swords className="w-4 h-4" />
            <span className="hidden sm:inline">DUEL:</span>
            <span className="text-white">{match.problem?.title}</span>
          </div>

          <span className="text-[10px] px-2 py-0.5 rounded bg-dark-700 text-indigo-300 font-mono border border-dark-600">
            {match.mode} MODE
          </span>
        </div>

        {/* Server Authoritative Timer */}
        <div className="flex items-center space-x-4">
          <ServerTimer
            initialRemainingSeconds={match.remainingSeconds || 900}
            onTimeUp={() => console.log("Time's up!")}
          />
        </div>

        {/* User Info Bar */}
        <div className="flex items-center space-x-3 text-xs font-mono">
          <div className="text-right hidden sm:block">
            <span className="text-slate-400">Rating: </span>
            <span className="text-cyan-400 font-bold">{user?.rating || 1500}</span>
          </div>
        </div>
      </div>

      {/* Arena Content Area */}
      <div className="flex-1 p-3 grid grid-cols-1 lg:grid-cols-12 gap-3 min-h-0 overflow-hidden">
        {/* Left Column: Problem Statement (4 Cols) */}
        <div className="lg:col-span-4 h-full overflow-hidden flex flex-col">
          <ProblemStatement problem={match.problem} />
        </div>

        {/* Center Column: Monaco Editor + Test Results Panel (5 Cols) */}
        <div className="lg:col-span-5 h-full flex flex-col space-y-3 overflow-hidden">
          <div className="flex-1 min-h-0">
            <MonacoCodeEditor
              problemId={match.problem?.id}
              starterCodes={match.problem?.starterCodes}
              onSubmit={handleSubmitSolution}
              onRun={handleRunCode}
              onTyping={handleTypingHeartbeat}
              submitting={submitting}
              running={running}
            />
          </div>

          <div className="h-56 min-h-0">
            <TestResultsPanel result={latestResult} loading={running || submitting} />
          </div>
        </div>

        {/* Right Column: Opponent Telemetry & Match Overview (3 Cols) */}
        <div className="lg:col-span-3 h-full overflow-y-auto space-y-3 flex flex-col">
          <OpponentCard opponent={opponent} lastEvent={lastWsEvent} />

          {/* Match Mode Information Box */}
          <div className="bg-dark-800/80 border border-dark-700 rounded-2xl p-4 space-y-2 text-xs font-mono text-slate-300">
            <div className="font-bold text-white flex items-center gap-1.5 font-sans">
              <Zap className="w-3.5 h-3.5 text-amber-400" />
              <span>Scoring Rules ({match.mode})</span>
            </div>
            <p className="text-[11px] text-slate-400 font-sans leading-relaxed">
              {match.mode === 'SCORE'
                ? 'Correctness (600 pts) dominates, followed by Execution Runtime (200 pts), Memory (100 pts), and Submission Speed (100 pts).'
                : match.mode === 'SUDDEN_DEATH'
                ? 'First player with an Accepted solution wins immediately!'
                : 'First accepted submission wins with runtime tie-breaker.'}
            </p>
          </div>

          {showAnalysis && matchResult?.analysis && (
            <div className="flex-1">
              <PostMatchAnalysis
                analysis={matchResult.analysis}
                onBack={() => setShowAnalysis(false)}
              />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
