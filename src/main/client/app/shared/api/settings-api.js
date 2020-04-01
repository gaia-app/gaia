import axios from 'axios';

export const getSettings = async () => axios.get('/api/settings');

export const saveSettings = async (settings) => axios.put('/api/settings', settings);
