import axios from 'axios';

export const getTeams = async () => axios.get('/api/teams');

export const deleteOrganization = async (organizationId) => axios.delete(`/api/teams/${organizationId}`);

export const createOrganization = async (organization) => axios.post('/api/teams', organization);
