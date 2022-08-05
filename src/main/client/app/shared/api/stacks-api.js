import axios from 'axios';

export const createStack = async (stack) => axios.post('/api/stacks', stack);

export const getStack = async (stackId) => axios.get(`/api/stacks/${stackId}`);

export const saveStack = async (stack) => axios.put(`/api/stacks/${stack.id}`, stack);

export const deleteStack = async (stackId) => axios.delete(`/api/stacks/${stackId}`);

export const getStacks = async () => axios.get('/api/stacks');

export const runStack = async (stackId) => axios.post(`/api/stacks/${stackId}/RUN`);

export const destroyStack = async (stackId) => axios.post(`/api/stacks/${stackId}/DESTROY`);
