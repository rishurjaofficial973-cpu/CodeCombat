import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { problemApi, submissionApi } from '../api';
import { ProblemStatement } from '../components/ProblemStatement';
import { MonacoCodeEditor } from '../components/MonacoCodeEditor';
import { TestResultsPanel } from '../components/TestResultsPanel';
import { Code2, ArrowLeft, CheckCircle2 } from 'lucide-react';

export const PracticeWorkspace = () => {
  const { problemId } = useParams();
  const navigate = useNavigate();

  const [problem, setProblem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [running, setRunning] = useState(false);
  const [latestResult, setLatestResult] = useState(null);

  useEffect(() => {
    const fetchProblem = async () => {
      try {
        const res = await problemApi.getProblemById(problemId);
        if (res.data) setProblem(res.data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchProblem();
  }, [problemId]);

  const handleRunCode = async (language, sourceCode) => {
    setRunning(true);
    try {
      const res = await submissionApi.submitCode({
        matchId: null,
        problemId,
        language,
        sourceCode,
        isPractice: true,
      });
      if (res.data) setLatestResult(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setRunning(false);
    }
  };

  const handleSubmitSolution = async (language, sourceCode) => {
    setSubmitting(true);
    try {
      const res = await submissionApi.submitCode({
        matchId: null,
        problemId,
        language,
        sourceCode,
        isPractice: true,
      });
      if (res.data) setLatestResult(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center bg-dark-900 text-slate-400 font-mono">
        <div className="flex items-center space-x-3">
          <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-cyan-400"></div>
          <span>Loading problem workspace...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="h-[calc(100vh-4rem)] flex flex-col bg-dark-900 text-slate-100 overflow-hidden">
      {/* Top Bar */}
      <div className="flex items-center justify-between px-4 sm:px-6 py-2.5 bg-dark-800 border-b border-dark-700 flex-shrink-0">
        <div className="flex items-center space-x-3">
          <button
            onClick={() => navigate('/practice')}
            className="flex items-center gap-1 text-xs text-slate-400 hover:text-white transition-colors"
          >
            <ArrowLeft className="w-4 h-4" />
            <span>Problem List</span>
          </button>
          <span className="text-dark-600">|</span>
          <span className="font-mono font-bold text-xs text-cyan-400">{problem?.id}: {problem?.title}</span>
        </div>

        <div className="flex items-center space-x-2">
          {problem?.isSolvedByMe && (
            <span className="flex items-center gap-1 text-xs font-semibold text-emerald-400">
              <CheckCircle2 className="w-4 h-4" />
              <span>Solved</span>
            </span>
          )}
        </div>
      </div>

      {/* 2-Column Split Workspace */}
      <div className="flex-1 p-3 grid grid-cols-1 lg:grid-cols-12 gap-3 min-h-0 overflow-hidden">
        {/* Left: Problem statement */}
        <div className="lg:col-span-5 h-full overflow-hidden flex flex-col">
          <ProblemStatement problem={problem} />
        </div>

        {/* Right: Monaco Editor + Test Results */}
        <div className="lg:col-span-7 h-full flex flex-col space-y-3 overflow-hidden">
          <div className="flex-1 min-h-0">
            <MonacoCodeEditor
              problemId={problem?.id}
              starterCodes={problem?.starterCodes}
              onSubmit={handleSubmitSolution}
              onRun={handleRunCode}
              submitting={submitting}
              running={running}
            />
          </div>

          <div className="h-60 min-h-0">
            <TestResultsPanel result={latestResult} loading={running || submitting} />
          </div>
        </div>
      </div>
    </div>
  );
};
