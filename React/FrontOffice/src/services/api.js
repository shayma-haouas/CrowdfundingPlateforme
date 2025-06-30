// pfa-project/src/services/api.js
import axios from 'axios';
import campaigns from '../data/campaigns';

export const API_URL = 'http://localhost:3000'; // Added export here

// Create axios instance with default config
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Accept': 'application/json',
  }
});

// Add request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    // Get token from authData JSON object
    let token = null;
    const authData = localStorage.getItem('authData');

    if (authData) {
      try {
        const parsed = JSON.parse(authData);
        token = parsed.token;
      } catch (error) {
        console.error('Error parsing authData:', error);
      }
    }

/*  console.log('Token from localStorage:', token);
    console.log('authData:', authData); */

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      //console.log('Request headers:', config.headers);
    } else {
      console.warn('No authentication token found');
    }

    return config;

  },
  (error) => {
    return Promise.reject(error);
  }
);

// Disabled projectService: all methods throw errors or return static data
export const projectService = {
  getAllProjects: async () => campaigns,
  getProjectById: async (id) => {
    const project = campaigns.find(p => p.id.toString() === id.toString());
    if (!project) throw new Error('Project not found (mocked)');
    return project;
  },
  updateProjectAmount: async () => { throw new Error('Donations are disabled in demo mode.'); },
  createProject: async () => { throw new Error('Project creation is disabled in demo mode.'); },
};

// Disabled commentService: all methods throw errors or return empty arrays
export const commentService = {
  getCommentsForProject: async () => [],
  createComment: async () => { throw new Error('Commenting is disabled in demo mode.'); },
};

// Disabled notificationService: all methods throw errors or return empty arrays
export const notificationService = {
  getUserNotifications: async () => [],
  markAllAsRead: async () => { throw new Error('Notifications are disabled in demo mode.'); },
  sendNotification: async () => { throw new Error('Notifications are disabled in demo mode.'); },
};