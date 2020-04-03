import axios from 'axios';

export const getState = async (stackId) => axios.get(`/api/state/${stackId}`);
