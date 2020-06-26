import axios from 'axios';

export const getCredentialsList = async () => axios.get('/api/credentials');

export const getCredentials = async (credentialsId) => axios.get(`/api/credentials/${credentialsId}`);

export const updateCredentials = async (credentials) => axios.put(`/api/credentials/${credentials.id}`, credentials);

export const createCredentials = async (credentials) => axios.post('/api/credentials', credentials);
