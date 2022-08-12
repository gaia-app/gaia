import axios from 'axios';

export const getModules = async () => axios.get('/api/modules');

export const getModule = async (moduleId) => axios.get(`/api/modules/${moduleId}`);

export const getModuleReadme = async (moduleId) => axios.get(`/api/modules/${moduleId}/readme`);

export const refreshModule = async (moduleId) => axios.post(`/api/modules/${moduleId}/refresh`);

export const updateModule = async (module) => axios.put(`/api/modules/${module.id}`, module);

export const createModule = async (module) => axios.post('/api/modules', module);

export const deleteModule = async (moduleId) => axios.delete(`/api/modules/${moduleId}`);
