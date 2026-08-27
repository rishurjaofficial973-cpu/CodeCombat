import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { problemApi } from '../api';
import { Code2, Search, Filter, CheckCircle2, ChevronLeft, ChevronRight, Clock, HardDrive } from 'lucide-react';

const TOPIC_LIST = [
  'All',
  'Arrays & Hashing',
  'Two Pointers & Sliding Window',
  'Binary Search',
  'Strings',
  'Linked List',
  'Stack & Monotonic Stack',
  'Queue & Deque',
  'Trees & BST',
  'Heap / Priority Queue',
  'Greedy',
  'Backtracking',
  'Graphs',
  'Dynamic Programming',
  'Bit Manipulation',
  'Trie',
  'Advanced Data Structures & Algorithms'
];

export const PracticeList = () => {
  const [problems, setProblems] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [totalElements, setTotalElements] = useState(0);
  const [search, setSearch] = useState('');
  const [difficulty, setDifficulty] = useState('');
  const [topic, setTopic] = useState('All');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadProblems();
  }, [page, difficulty, topic]);

  const loadProblems = async () => {
    setLoading(true);
    try {
      const res = await problemApi.getProblems({
        page,
        size: 20,
        difficulty: difficulty || null,
        topic: topic !== 'All' ? topic : null,
        search: search.trim() || null,
      });

      if (res.data) {
        setProblems(res.data.content || []);
        setTotalPages(res.data.totalPages || 1);
        setTotalElements(res.data.totalElements || 0);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    setPage(0);
    loadProblems();
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6 animate-fade-in">
      {/* Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-indigo-500/10 border border-indigo-500/30 text-indigo-300 text-xs font-mono mb-2">
            <Code2 className="w-4 h-4 text-cyan-400" />
            <span>1,000-Problem Curated Repository</span>
          </div>
          <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
            Practice Repository
          </h1>
          <p className="text-xs sm:text-sm text-slate-400">
            Hone your competitive programming and interview DSA patterns
          </p>
        </div>

        {/* Search Bar */}
        <form onSubmit={handleSearchSubmit} className="relative w-full md:w-80">
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search 1,000 problems by title or ID..."
            className="w-full bg-dark-800 border border-dark-600 rounded-xl px-4 py-2.5 pl-10 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors"
          />
          <Search className="w-4 h-4 text-slate-500 absolute left-3.5 top-3" />
        </form>
      </div>

      {/* Difficulty & Topic Filter Chips */}
      <div className="space-y-3">
        {/* Difficulty Selector */}
        <div className="flex items-center space-x-2">
          <button
            onClick={() => { setDifficulty(''); setPage(0); }}
            className={`px-3 py-1.5 rounded-xl text-xs font-semibold font-mono transition-colors ${
              difficulty === '' ? 'bg-indigo-600 text-white' : 'bg-dark-800 text-slate-400 hover:bg-dark-700'
            }`}
          >
            All Difficulties ({totalElements})
          </button>
          <button
            onClick={() => { setDifficulty('EASY'); setPage(0); }}
            className={`px-3 py-1.5 rounded-xl text-xs font-semibold font-mono transition-colors ${
              difficulty === 'EASY' ? 'bg-emerald-600 text-white' : 'bg-dark-800 text-emerald-400 hover:bg-dark-700'
            }`}
          >
            Easy (250)
          </button>
          <button
            onClick={() => { setDifficulty('MEDIUM'); setPage(0); }}
            className={`px-3 py-1.5 rounded-xl text-xs font-semibold font-mono transition-colors ${
              difficulty === 'MEDIUM' ? 'bg-amber-600 text-white' : 'bg-dark-800 text-amber-400 hover:bg-dark-700'
            }`}
          >
            Medium (550)
          </button>
          <button
            onClick={() => { setDifficulty('HARD'); setPage(0); }}
            className={`px-3 py-1.5 rounded-xl text-xs font-semibold font-mono transition-colors ${
              difficulty === 'HARD' ? 'bg-rose-600 text-white' : 'bg-dark-800 text-rose-400 hover:bg-dark-700'
            }`}
          >
            Hard (200)
          </button>
        </div>

        {/* Topics Horizontal Scroll */}
        <div className="flex items-center space-x-2 overflow-x-auto pb-2 scrollbar-none">
          {TOPIC_LIST.map((t) => (
            <button
              key={t}
              onClick={() => { setTopic(t); setPage(0); }}
              className={`whitespace-nowrap px-3 py-1 rounded-lg text-xs font-medium transition-colors ${
                topic === t
                  ? 'bg-cyan-500/20 text-cyan-300 border border-cyan-500/40'
                  : 'bg-dark-800/80 text-slate-400 hover:bg-dark-700 border border-dark-700'
              }`}
            >
              {t}
            </button>
          ))}
        </div>
      </div>

      {/* Problem Table / List */}
      <div className="bg-dark-800 border border-dark-600 rounded-3xl overflow-hidden shadow-2xl">
        {loading ? (
          <div className="p-12 text-center text-slate-400 font-mono text-xs">
            <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-cyan-400 mx-auto mb-3"></div>
            Loading problems...
          </div>
        ) : problems.length === 0 ? (
          <div className="p-12 text-center text-slate-500 font-mono text-xs">
            No problems match your selected criteria.
          </div>
        ) : (
          <div className="divide-y divide-dark-700">
            {problems.map((p) => (
              <div
                key={p.id}
                className="p-4 sm:px-6 hover:bg-dark-700/50 transition-colors flex flex-col sm:flex-row sm:items-center justify-between gap-4"
              >
                <div className="space-y-1 min-w-0">
                  <div className="flex items-center space-x-2.5">
                    {p.isSolvedByMe ? (
                      <CheckCircle2 className="w-4 h-4 text-emerald-400 flex-shrink-0" />
                    ) : (
                      <span className="w-4 h-4 rounded-full border border-slate-600 inline-block flex-shrink-0"></span>
                    )}
                    <span className="font-mono text-xs font-bold text-cyan-400">{p.id}</span>
                    <Link
                      to={`/practice/${p.id}`}
                      className="font-bold text-sm text-white hover:text-cyan-300 transition-colors truncate"
                    >
                      {p.title}
                    </Link>
                    <span className={`text-[10px] px-2 py-0.5 rounded-full font-semibold ${
                      p.difficulty === 'EASY' ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/30' :
                      p.difficulty === 'MEDIUM' ? 'bg-amber-500/10 text-amber-400 border border-amber-500/30' :
                      'bg-rose-500/10 text-rose-400 border border-rose-500/30'
                    }`}>
                      {p.difficulty}
                    </span>
                  </div>

                  <div className="flex flex-wrap items-center gap-2 text-xs text-slate-400 pl-6">
                    <span>{p.topics}</span>
                    {p.expectedTimeComplexity && (
                      <>
                        <span>•</span>
                        <span className="font-mono text-cyan-300">Time: {p.expectedTimeComplexity}</span>
                      </>
                    )}
                  </div>
                </div>

                <div className="flex items-center space-x-4 self-end sm:self-center">
                  <Link
                    to={`/practice/${p.id}`}
                    className="px-4 py-1.5 rounded-xl font-bold text-xs text-white bg-indigo-600 hover:bg-indigo-500 transition-colors"
                  >
                    Solve
                  </Link>
                </div>
              </div>
            ))}
          </div>
        )}

        {/* Pagination Bar */}
        <div className="p-4 bg-dark-900 border-t border-dark-700 flex items-center justify-between text-xs text-slate-400 font-mono">
          <div>
            Page <span className="text-white font-bold">{page + 1}</span> of <span className="text-white font-bold">{totalPages}</span>
          </div>

          <div className="flex items-center space-x-2">
            <button
              onClick={() => setPage(prev => Math.max(0, prev - 1))}
              disabled={page === 0}
              className="p-1.5 rounded-lg bg-dark-800 hover:bg-dark-700 disabled:opacity-30 disabled:pointer-events-none text-slate-300"
            >
              <ChevronLeft className="w-4 h-4" />
            </button>
            <button
              onClick={() => setPage(prev => Math.min(totalPages - 1, prev + 1))}
              disabled={page >= totalPages - 1}
              className="p-1.5 rounded-lg bg-dark-800 hover:bg-dark-700 disabled:opacity-30 disabled:pointer-events-none text-slate-300"
            >
              <ChevronRight className="w-4 h-4" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
