import axios from 'axios';

export const getBuildInfo = async () => {
  const resp = await axios.get('/build-info');
  return resp.data;
};
