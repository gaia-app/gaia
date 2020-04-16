import axios from 'axios';

export const getJobs = async (stackId) => axios.get(`/api/jobs?stackId=${stackId}`);

export const getJob = async (jobId) => axios.get(`/api/jobs/${jobId}`);

export const planJob = async (jobId) => axios.post(`/api/jobs/${jobId}/plan`);

export const applyJob = async (jobId) => axios.post(`/api/jobs/${jobId}/apply`);

export const retryJob = async (jobId) => axios.post(`/api/jobs/${jobId}/retry`);

export const deleteJob = async (jobId) => axios.delete(`/api/jobs/${jobId}`);
