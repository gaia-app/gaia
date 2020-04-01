import axios from 'axios';

export const createStack = async (stack) => axios.post('/api/stacks', stack);
