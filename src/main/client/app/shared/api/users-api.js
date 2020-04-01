import axios from 'axios';

export const getUsers = async () => {
  const resp = await axios.get('/api/users');
  return resp.data;
};

export const updateUser = async (user) => axios.put(`/api/users/${user.id}`, user);
