import axios from 'axios';

export const getRegistriesRepositories = async (registry) => {
  const resp = await axios.get(`/api/registries/${registry}/repositories`);
  return resp.data;
};

export const importRegistryRepository = async (registry, id) => {
  const resp = await axios.get(`/api/registries/${registry}/repositories/${id}/import`);
  return resp.data;
};
