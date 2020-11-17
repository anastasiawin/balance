let backendHost;

const hostname = window && window.location && window.location.hostname;

if(hostname === 'budgetwin.herokuapp.com') {
  backendHost = 'https://budgetwin.herokuapp.com';
} else {
  backendHost = process.env.REACT_APP_BACKEND_HOST || 'http://localhost:8080';
}

export const API_ROOT = `${backendHost}/api`;