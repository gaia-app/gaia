import axios from 'axios';

export const getSettings = async () => {
  const resp = await axios.get('/api/settings');
  return resp.data;
};

export const saveSettings = async (settings) => axios.put('/api/settings', settings);
