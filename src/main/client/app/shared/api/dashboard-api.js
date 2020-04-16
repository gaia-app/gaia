import axios from 'axios';

export const getSummary = async () => axios.get('/api/dashboard/summary');
