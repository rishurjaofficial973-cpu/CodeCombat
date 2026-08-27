import api from './client';

export const authApi = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
  getMe: () => api.get('/auth/me'),
};

export const matchApi = {
  findMatch: (preferences) => api.post('/matches/find', preferences),
  cancelMatch: () => api.post('/matches/cancel'),
  getQueueStatus: () => api.get('/matches/status'),
  getMatch: (id) => api.get(`/matches/${id}`),
  getMatchResult: (id) => api.get(`/matches/${id}/result`),
  getHistory: (page = 0, size = 15) => api.get(`/matches/history?page=${page}&size=${size}`),
};

export const problemApi = {
  getProblems: (params) => api.get('/problems', { params }),
  getProblemById: (id) => api.get(`/problems/${id}`),
  getRecommendations: () => api.get('/problems/recommendations'),
};

export const submissionApi = {
  submitCode: (data) => api.post('/submissions', data),
};

export const leaderboardApi = {
  getLeaderboard: (limit = 50) => api.get(`/leaderboard?limit=${limit}`),
};

export const analyticsApi = {
  getMyAnalytics: () => api.get('/analytics/me'),
  getUserAnalytics: (id) => api.get(`/analytics/user/${id}`),
};

export const notificationApi = {
  getNotifications: (limit = 20) => api.get(`/notifications?limit=${limit}`),
  markAllAsRead: () => api.post('/notifications/read-all'),
};

export const adminApi = {
  getStats: () => api.get('/admin/stats'),
  createProblem: (data) => api.post('/admin/problems', data),
  updateProblem: (id, data) => api.put(`/admin/problems/${id}`, data),
  deleteProblem: (id) => api.delete(`/admin/problems/${id}`),
  getUsers: (page = 0, size = 20) => api.get(`/admin/users?page=${page}&size=${size}`),
  toggleBan: (userId) => api.post(`/admin/users/${userId}/ban`),
};
