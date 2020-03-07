export const getCookie = (key) => {
  const cookie = document.cookie.split(';').find((c) => c.trim().startsWith(key));
  return cookie && cookie.substring(cookie.indexOf('=') + 1);
};

export const setCookie = (key, value, maxAgeInMillis) => {
  document.cookie = `${key}=${value}; max-age=${maxAgeInMillis}`;
};

export const isCookieExisting = (key) => document.cookie.includes(key);
