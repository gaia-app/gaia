import axios from 'axios';

export const createStack = async (stack) => {
  const resp = await axios.post('/api/stacks', stack);
  return resp.data;
};
