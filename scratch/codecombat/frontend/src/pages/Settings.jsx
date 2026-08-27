import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { Settings as SettingsIcon, Save, Moon, Bell, Volume2, Shield } from 'lucide-react';

export const Settings = () => {
  const { user } = useAuth();
  const [editorTheme, setEditorTheme] = useState('vs-dark');
  const [soundEffects, setSoundEffects] = useState(true);
  const [notifications, setNotifications] = useState(true);
  const [saved, setSaved] = useState(false);

  const handleSave = (e) => {
    e.preventDefault();
    setSaved(true);
    setTimeout(() => setSaved(false), 2000);
  };

  return (
    <div className="max-w-3xl mx-auto px-4 py-8 space-y-6 animate-fade-in">
      <div>
        <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-indigo-500/10 border border-indigo-500/30 text-cyan-400 text-xs font-mono mb-2">
          <SettingsIcon className="w-4 h-4" />
          <span>Preferences</span>
        </div>
        <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
          Account & Editor Settings
        </h1>
      </div>

      <div className="bg-dark-800 border border-dark-600 rounded-3xl p-6 sm:p-8 shadow-2xl space-y-6">
        <form onSubmit={handleSave} className="space-y-6">
          {/* Editor Preferences */}
          <div className="space-y-4">
            <h3 className="text-sm font-bold text-white uppercase tracking-wider font-mono">
              Code Editor Preferences
            </h3>

            <div className="flex items-center justify-between p-4 rounded-2xl bg-dark-900 border border-dark-700">
              <div>
                <div className="text-xs font-semibold text-white">Monaco Theme</div>
                <div className="text-[11px] text-slate-400">Dark competitive high-contrast theme</div>
              </div>
              <select
                value={editorTheme}
                onChange={(e) => setEditorTheme(e.target.value)}
                className="bg-dark-800 border border-dark-600 rounded-xl px-3 py-1.5 text-xs text-cyan-300 font-mono"
              >
                <option value="vs-dark">VS Dark Modern</option>
                <option value="hc-black">High Contrast Cyber</option>
              </select>
            </div>
          </div>

          {/* Sound & Notifications */}
          <div className="space-y-4 pt-4 border-t border-dark-700">
            <h3 className="text-sm font-bold text-white uppercase tracking-wider font-mono">
              Battle Alerts & Telemetry
            </h3>

            <div className="flex items-center justify-between p-4 rounded-2xl bg-dark-900 border border-dark-700">
              <div className="flex items-center space-x-3">
                <Volume2 className="w-5 h-5 text-indigo-400" />
                <div>
                  <div className="text-xs font-semibold text-white">Match Audio Cues</div>
                  <div className="text-[11px] text-slate-400">Countdown sounds & victory fanfare</div>
                </div>
              </div>
              <input
                type="checkbox"
                checked={soundEffects}
                onChange={(e) => setSoundEffects(e.target.checked)}
                className="w-4 h-4 accent-indigo-600 cursor-pointer"
              />
            </div>

            <div className="flex items-center justify-between p-4 rounded-2xl bg-dark-900 border border-dark-700">
              <div className="flex items-center space-x-3">
                <Bell className="w-5 h-5 text-cyan-400" />
                <div>
                  <div className="text-xs font-semibold text-white">Realtime Popups</div>
                  <div className="text-[11px] text-slate-400">Push notifications for match events & achievements</div>
                </div>
              </div>
              <input
                type="checkbox"
                checked={notifications}
                onChange={(e) => setNotifications(e.target.checked)}
                className="w-4 h-4 accent-cyan-500 cursor-pointer"
              />
            </div>
          </div>

          <div className="flex items-center justify-between pt-4 border-t border-dark-700">
            {saved && (
              <span className="text-xs font-mono text-emerald-400 font-bold">
                ✓ Preferences updated!
              </span>
            )}
            <button
              type="submit"
              className="ml-auto px-6 py-2.5 rounded-xl font-bold text-xs text-white bg-indigo-600 hover:bg-indigo-500 shadow-lg shadow-indigo-600/30 transition-colors"
            >
              Save Changes
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
