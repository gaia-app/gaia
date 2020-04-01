import axios from 'axios';

export default {
  init: () => {
    axios.interceptors.response.use(
      (response) => response.data,
      (error) => Promise.reject(error.response.data),
    );
  },
};
