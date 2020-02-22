import axios from 'axios';

export const doLogin = async (formData) => axios.post('/auth/classic', formData);

export const listProviders = async () => {
  const resp = await axios.get('/auth/providers');
  return resp.data;
};

export const getUser = async () => {
  const resp = await axios.get('/auth/user');
  return resp.data;
};

export const getAuthorities = async () => {
  const resp = await axios.get('/auth/authorities');
  return resp.data;
};

export const doLogout = async () => axios.post('/auth/logout');
