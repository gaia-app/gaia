import axios from 'axios';

export const doLogin = async (formData) => axios.post('/auth/classic', formData);

export const listProviders = async () => axios.get('/auth/providers');

export const getUser = async () => axios.get('/auth/user');

export const getAuthorities = async () => axios.get('/auth/authorities');

export const doLogout = async () => axios.post('/auth/logout');
