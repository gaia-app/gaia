import axios from 'axios';

export const getTeams = async () => {
  const resp = await axios.get('/api/teams');
  return resp.data;
};
