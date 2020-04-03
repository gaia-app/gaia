import axios from 'axios';

export const getJobs = async (stackId) => axios.get(`/api/jobs?stackId=${stackId}`);

export const retryJob = async (jobId) => axios.post(`/api/jobs/${jobId}/retry`);

export const deleteJob = async (jobId) => axios.delete(`/api/jobs/${jobId}`);
