import axios from 'axios';

export const getOrganizations = async () => axios.get('/api/organizations');

// export const getUserOrganizations = async () => axios.get('/api/organizations/user-organization');

export const deleteOrganization = async (organizationId) => axios.delete(`/api/organizations/${organizationId}`);

export const createOrganization = async (organization) => axios.post('/api/organizations', organization);
