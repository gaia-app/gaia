import store from '@/shared/store';
import {
  getRequestedUrl,
  hasRequestedUrl,
  removeRequestedUrl,
  setRequestedUrl,
} from '@/shared/services/session-storage-service';
import { isCookieExisting } from '@/shared/services/cookie-service';

const isPageAuthenticated = (to) => to.meta && to.meta.authorities && to.meta.authorities.length > 0;

const isUserAuthenticated = () => store.state.session.authenticated;

const hasCookieSession = () => isCookieExisting('XSRF-TOKEN') || isCookieExisting('remember-me');

const redirectToLogin = (to, next) => {
  setRequestedUrl(to.fullPath);
  next({ name: 'login' });
};

const redirectToRequestedPage = (next) => {
  if (hasRequestedUrl()) {
    const url = getRequestedUrl();
    removeRequestedUrl();
    next(url, { replace: true });
  } else {
    next();
  }
};

const redirectIfAuthorized = (to, next) => {
  // user authorized ?
  if (store.getters['session/hasAuthorities'](to.meta.authorities)) {
    redirectToRequestedPage(next);
  } else {
    next({ name: 'page-forbidden' });
  }
};

export const authenticationGuard = async (to, from, next) => {
  // page exists ?
  if (!to.matched.length) {
    next({ name: 'page-not-found' });
    return;
  }
  // public page ?
  if (!isPageAuthenticated(to)) {
    next();
    return;
  }
  // if no cookie, no need to go further
  if (!hasCookieSession()) {
    redirectToLogin(to, next);
    return;
  }
  // reload authentication if cookie
  if (!isUserAuthenticated()) {
    try {
      await store.dispatch('session/authenticated');
    } catch (e) {
      // in case of expired or invalid cookie
    }
  }
  // check page
  if (isUserAuthenticated()) {
    redirectIfAuthorized(to, next);
  } else {
    redirectToLogin(to, next);
  }
};
