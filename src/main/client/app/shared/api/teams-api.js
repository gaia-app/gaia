import axios from 'axios';

export const getTeams = async () => axios.get('/api/teams');
