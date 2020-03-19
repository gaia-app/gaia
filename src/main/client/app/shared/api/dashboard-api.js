import axios from 'axios';

export const getSummary = async () => {
  const resp = await axios.get('/api/dashboard/summary');
  return resp.data;
};
