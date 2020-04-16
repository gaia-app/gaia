import axios from 'axios';

export const getBuildInfo = async () => axios.get('/build-info');
