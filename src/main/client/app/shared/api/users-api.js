import axios from 'axios';

export const getUsers = async () => axios.get('/api/users');

export const updateUser = async (user) => axios.put(`/api/users/${user.id}`, user);
