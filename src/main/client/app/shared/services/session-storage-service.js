export const getRequestedUrl = () => sessionStorage.getItem('requested-url');

export const setRequestedUrl = (url) => sessionStorage.setItem('requested-url', url);

export const hasRequestedUrl = () => !!sessionStorage.getItem('requested-url');

export const removeRequestedUrl = () => sessionStorage.removeItem('requested-url');
