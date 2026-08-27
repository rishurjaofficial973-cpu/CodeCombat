import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Navbar } from './components/Navbar';
import { Footer } from './components/Footer';
import { ProtectedRoute, AdminRoute } from './components/ProtectedRoute';

import { Home } from './pages/Home';
import { Login } from './pages/Login';
import { Register } from './pages/Register';
import { Dashboard } from './pages/Dashboard';
import { MatchmakingLobby } from './pages/MatchmakingLobby';
import { VersusArena } from './pages/VersusArena';
import { PracticeList } from './pages/PracticeList';
import { PracticeWorkspace } from './pages/PracticeWorkspace';
import { Leaderboard } from './pages/Leaderboard';
import { MatchHistory } from './pages/MatchHistory';
import { ProfileAnalytics } from './pages/ProfileAnalytics';
import { AdminDashboard } from './pages/AdminDashboard';
import { Settings } from './pages/Settings';

export function App() {
  return (
    <div className="min-h-screen flex flex-col bg-dark-900 text-slate-100 selection:bg-indigo-500 selection:text-white">
      <Navbar />
      <main className="flex-1 flex flex-col">
        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/practice" element={<PracticeList />} />
          <Route path="/practice/:problemId" element={<PracticeWorkspace />} />
          <Route path="/leaderboard" element={<Leaderboard />} />

          {/* Authenticated Protected Routes */}
          <Route element={<ProtectedRoute />}>
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/versus" element={<MatchmakingLobby />} />
            <Route path="/versus/:matchId" element={<VersusArena />} />
            <Route path="/matches" element={<MatchHistory />} />
            <Route path="/matches/:matchId" element={<MatchHistory />} />
            <Route path="/profile" element={<ProfileAnalytics />} />
            <Route path="/settings" element={<Settings />} />
          </Route>

          {/* Admin Protected Routes */}
          <Route element={<AdminRoute />}>
            <Route path="/admin" element={<AdminDashboard />} />
          </Route>

          {/* Fallback */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
      <Footer />
    </div>
  );
}

export default App;
