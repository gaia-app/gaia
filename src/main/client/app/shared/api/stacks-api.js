import axios from 'axios';

export const createStack = async (stack) => axios.post('/api/stacks', stack);

export const getStack = async (stackId) => axios.get(`/api/stacks/${stackId}`);

export const saveStack = async (stack) => axios.put(`/api/stacks/${stack.id}`, stack);

export const getStacks = async () => axios.get('/api/stacks');
