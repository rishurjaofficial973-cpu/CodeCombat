import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useWebSocket } from '../context/WebSocketContext';
import { notificationApi } from '../api';
import { Swords, Code2, Trophy, History, LayoutDashboard, Bell, Shield, LogOut, User as UserIcon, Sparkles } from 'lucide-react';

export const Navbar = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const { connected } = useWebSocket();
  const navigate = useNavigate();
  const location = useLocation();

  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [showNotifs, setShowNotifs] = useState(false);
  const [showProfileMenu, setShowProfileMenu] = useState(false);

  useEffect(() => {
    if (isAuthenticated) {
      loadNotifications();
    }
  }, [isAuthenticated]);

  const loadNotifications = async () => {
    try {
      const res = await notificationApi.getNotifications(10);
      if (res.data) {
        setNotifications(res.data);
        setUnreadCount(res.data.filter(n => !n.isRead).length);
      }
    } catch (err) {
      console.debug('Failed to fetch notifications:', err);
    }
  };

  const handleMarkAllRead = async () => {
    try {
      await notificationApi.markAllAsRead();
      setUnreadCount(0);
      setNotifications(prev => prev.map(n => ({ ...n, isRead: true })));
    } catch (err) {
      console.error(err);
    }
  };

  const navLinks = [
    { name: 'Dashboard', path: '/dashboard', icon: LayoutDashboard },
    { name: 'Versus (1v1)', path: '/versus', icon: Swords, highlight: true },
    { name: 'Practice', path: '/practice', icon: Code2 },
    { name: 'Leaderboard', path: '/leaderboard', icon: Trophy },
    { name: 'Matches', path: '/matches', icon: History },
  ];

  return (
    <nav className="sticky top-0 z-40 bg-dark-900/90 backdrop-blur-md border-b border-dark-700">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Brand Logo */}
          <div className="flex items-center space-x-3">
            <Link to="/" className="flex items-center space-x-2.5 group">
              <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-indigo-600 to-cyan-500 p-0.5 shadow-lg shadow-indigo-500/20 group-hover:scale-105 transition-transform duration-200 flex items-center justify-center">
                <div className="w-full h-full bg-dark-900 rounded-[10px] flex items-center justify-center">
                  <Swords className="w-5 h-5 text-cyan-400 group-hover:rotate-12 transition-transform duration-300" />
                </div>
              </div>
              <div className="flex flex-col">
                <span className="font-bold text-xl tracking-wider text-white font-mono flex items-center gap-1.5">
                  CODE<span className="text-cyan-400">DUEL</span>
                  <span className="text-[10px] px-1.5 py-0.5 rounded-full bg-indigo-500/20 text-indigo-300 font-sans border border-indigo-500/30">1v1</span>
                </span>
              </div>
            </Link>
          </div>

          {/* Navigation Links */}
          {isAuthenticated && (
            <div className="hidden md:flex items-center space-x-1">
              {navLinks.map((link) => {
                const Icon = link.icon;
                const active = location.pathname === link.path || location.pathname.startsWith(link.path + '/');
                return (
                  <Link
                    key={link.name}
                    to={link.path}
                    className={`flex items-center space-x-2 px-3.5 py-2 rounded-lg text-sm font-medium transition-all duration-150 ${
                      active
                        ? 'bg-indigo-600/20 text-indigo-300 border border-indigo-500/30'
                        : link.highlight
                        ? 'bg-gradient-to-r from-indigo-500/10 to-cyan-500/10 text-cyan-300 hover:text-white hover:bg-indigo-600/20 border border-cyan-500/20'
                        : 'text-slate-300 hover:text-white hover:bg-dark-800'
                    }`}
                  >
                    <Icon className={`w-4 h-4 ${active ? 'text-indigo-400' : link.highlight ? 'text-cyan-400' : 'text-slate-400'}`} />
                    <span>{link.name}</span>
                  </Link>
                );
              })}
            </div>
          )}

          {/* Right Action Menu */}
          <div className="flex items-center space-x-3">
            {/* Realtime WS Indicator */}
            <div className="hidden sm:flex items-center space-x-1.5 px-2.5 py-1 rounded-full bg-dark-800 border border-dark-700 text-xs">
              <span className={`w-2 h-2 rounded-full ${connected ? 'bg-emerald-400 animate-pulse' : 'bg-rose-500'}`}></span>
              <span className="text-slate-400 font-mono text-[11px]">{connected ? 'Live' : 'Offline'}</span>
            </div>

            {isAuthenticated ? (
              <div className="flex items-center space-x-3">
                {/* Notifications Popover */}
                <div className="relative">
                  <button
                    onClick={() => setShowNotifs(!showNotifs)}
                    className="p-2 rounded-lg text-slate-300 hover:text-white hover:bg-dark-800 relative transition-colors"
                  >
                    <Bell className="w-5 h-5" />
                    {unreadCount > 0 && (
                      <span className="absolute top-1.5 right-1.5 w-4 h-4 rounded-full bg-rose-500 text-white text-[10px] font-bold flex items-center justify-center">
                        {unreadCount}
                      </span>
                    )}
                  </button>

                  {showNotifs && (
                    <div className="absolute right-0 mt-2 w-80 bg-dark-800 border border-dark-600 rounded-xl shadow-2xl overflow-hidden z-50">
                      <div className="flex items-center justify-between p-3 border-b border-dark-700 bg-dark-900/60">
                        <span className="text-xs font-semibold text-slate-200">Notifications</span>
                        {unreadCount > 0 && (
                          <button
                            onClick={handleMarkAllRead}
                            className="text-[11px] text-cyan-400 hover:underline"
                          >
                            Mark all read
                          </button>
                        )}
                      </div>
                      <div className="max-h-64 overflow-y-auto divide-y divide-dark-700">
                        {notifications.length === 0 ? (
                          <div className="p-4 text-center text-xs text-slate-500">No notifications yet</div>
                        ) : (
                          notifications.map((n) => (
                            <div key={n.id} className={`p-3 text-xs transition-colors ${n.isRead ? 'text-slate-400' : 'bg-indigo-950/30 text-slate-200'}`}>
                              <div className="font-semibold text-white mb-0.5">{n.title}</div>
                              <div>{n.message}</div>
                            </div>
                          ))
                        )}
                      </div>
                    </div>
                  )}
                </div>

                {/* User Profile Pill */}
                <div className="relative">
                  <button
                    onClick={() => setShowProfileMenu(!showProfileMenu)}
                    className="flex items-center space-x-2.5 bg-dark-800 hover:bg-dark-700 border border-dark-600 rounded-xl py-1.5 px-3 transition-colors"
                  >
                    <div className="w-7 h-7 rounded-lg bg-gradient-to-br from-indigo-500 to-purple-600 flex items-center justify-center font-bold text-xs text-white uppercase">
                      {user?.username?.charAt(0) || 'U'}
                    </div>
                    <div className="text-left hidden sm:block">
                      <div className="text-xs font-semibold text-slate-100 leading-tight">{user?.username}</div>
                      <div className="text-[11px] font-mono text-cyan-400 font-bold leading-tight">
                        ⚡ {user?.rating || 1200}
                      </div>
                    </div>
                  </button>

                  {showProfileMenu && (
                    <div className="absolute right-0 mt-2 w-48 bg-dark-800 border border-dark-600 rounded-xl shadow-2xl py-1 z-50">
                      <Link
                        to="/profile"
                        onClick={() => setShowProfileMenu(false)}
                        className="flex items-center space-x-2 px-4 py-2 text-xs text-slate-300 hover:text-white hover:bg-dark-700"
                      >
                        <UserIcon className="w-4 h-4 text-indigo-400" />
                        <span>My Profile & Stats</span>
                      </Link>
                      {user?.role === 'ROLE_ADMIN' && (
                        <Link
                          to="/admin"
                          onClick={() => setShowProfileMenu(false)}
                          className="flex items-center space-x-2 px-4 py-2 text-xs text-amber-400 hover:bg-dark-700"
                        >
                          <Shield className="w-4 h-4" />
                          <span>Admin Dashboard</span>
                        </Link>
                      )}
                      <div className="border-t border-dark-700 my-1"></div>
                      <button
                        onClick={() => {
                          setShowProfileMenu(false);
                          logout();
                          navigate('/login');
                        }}
                        className="flex items-center space-x-2 w-full text-left px-4 py-2 text-xs text-rose-400 hover:bg-dark-700"
                      >
                        <LogOut className="w-4 h-4" />
                        <span>Logout</span>
                      </button>
                    </div>
                  )}
                </div>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                <Link
                  to="/login"
                  className="px-3.5 py-1.5 text-xs font-medium text-slate-300 hover:text-white rounded-lg hover:bg-dark-800 transition-colors"
                >
                  Sign In
                </Link>
                <Link
                  to="/register"
                  className="px-4 py-1.5 text-xs font-semibold text-white bg-indigo-600 hover:bg-indigo-500 rounded-lg shadow-lg shadow-indigo-600/30 transition-colors"
                >
                  Get Started
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};
