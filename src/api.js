import axios from 'axios';

// Create a central Axios instance
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Use an interceptor to add the JWT token to every protected request
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      // Add the Authorization header
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;

