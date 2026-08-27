import React, { createContext, useContext, useState, useEffect } from 'react';
import { authApi } from '../api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('codeduel_token'));
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initAuth = async () => {
      const storedToken = localStorage.getItem('codeduel_token');
      if (storedToken) {
        try {
          const res = await authApi.getMe();
          if (res.data) {
            setUser(res.data);
          }
        } catch (err) {
          console.error('Session restore failed:', err);
          logout();
        }
      }
      setLoading(false);
    };
    initAuth();
  }, []);

  const login = (authData) => {
    localStorage.setItem('codeduel_token', authData.token);
    setToken(authData.token);
    setUser({
      id: authData.id,
      username: authData.username,
      email: authData.email,
      role: authData.role,
      rating: authData.rating,
      globalRank: authData.globalRank
    });
  };

  const logout = () => {
    localStorage.removeItem('codeduel_token');
    localStorage.removeItem('codeduel_user');
    setToken(null);
    setUser(null);
  };

  const updateUser = (updatedFields) => {
    setUser(prev => ({ ...prev, ...updatedFields }));
  };

  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated: !!token, loading, login, logout, updateUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
