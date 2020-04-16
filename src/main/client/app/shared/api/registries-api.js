import axios from 'axios';

export const getRegistriesRepositories = async (registry) => axios.get(`/api/registries/${registry}/repositories`);

export const importRegistryRepository = async (registry, id) => axios.post(
  `/api/registries/${registry}/repositories/${id}/import`,
);
