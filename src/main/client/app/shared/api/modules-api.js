import axios from 'axios';

export const getModules = async () => {
  const resp = await axios.get('/api/modules');
  return resp.data;
};

export const getModule = async (moduleId) => {
  const resp = await axios.get(`/api/modules/${moduleId}`);
  return resp.data;
};

export const getModuleReadme = async (moduleId) => {
  const resp = await axios.get(`/api/modules/${moduleId}/readme`);
  return resp.data;
};

export const updateModule = async (module) => axios.put(`/api/modules/${module.id}`, module);

export const createModule = async (module) => {
  const resp = await axios.post('/api/modules', module);
  return resp.data;
};
