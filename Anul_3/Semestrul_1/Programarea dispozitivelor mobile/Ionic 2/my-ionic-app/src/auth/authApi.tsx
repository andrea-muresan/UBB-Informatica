import axios from 'axios';
import { baseUrl, config, withLogs } from '../core';

const authUrl = `http://${baseUrl}/api/auth/login`;

export interface AuthProps {
  token: string;
}

export const login: (username?: string, password?: string) => Promise<AuthProps> = (username, password) => {
  return withLogs(axios.post(authUrl, { username, password }, config), 'login');
}

const signupUrl = `http://${baseUrl}/api/auth/signup`;

export const signup: (username?: string, password?: string) => Promise<AuthProps> = (username, password) => {
  return withLogs(axios.post(signupUrl, { username, password }, config), 'signup');
}
