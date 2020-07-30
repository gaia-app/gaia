import axios from 'axios';

export const getProviders = async () => axios.get('/api/credentials/providers');

export const getCredentialsList = async () => axios.get('/api/credentials');

export const getCredentials = async (credentialsId) => axios.get(`/api/credentials/${credentialsId}`);

export const updateCredentials = async (credentials) => axios.put(`/api/credentials/${credentials.id}`, credentials);

export const createCredentials = async (credentials) => axios.post('/api/credentials', credentials);

export const deleteCredentials = async (credentials) => axios.delete(`/api/credentials/${credentials.id}`);
