import {
  doLogin,
  doLogout,
  getAuthorities,
  getUser,
} from '@/shared/api/authentication-api';

const sessionState = {
  login: false,
  authenticated: false,
  user: {},
  authorities: [],
};

const sessionGetters = {
  hasAuthorities: (state) => (authorities) => {
    if (!authorities) return true;
    if (!state.authenticated || !state.authorities) return false;
    let authoritiesToCheck = authorities;
    if (typeof authorities === 'string') {
      authoritiesToCheck = [authorities];
    }
    return authoritiesToCheck.some((role) => state.authorities.includes(role));
  },
};

const sessionMutations = {
  login: (state) => {
    state.login = true;
  },
  authenticated: (state, { user, authorities }) => {
    state.login = false;
    state.authenticated = true;
    state.user = user;
    state.authorities = authorities;
  },
  logout: (state) => {
    state.login = false;
    state.authenticated = false;
    state.user = {};
    state.authorities = [];
  },
};

const sessionActions = {
  login: async ({ commit, dispatch }, formData) => {
    await doLogin(formData);
    commit('login');
    await dispatch('authenticated');
  },
  authenticated: async ({ commit }) => {
    const user = await getUser();
    const authorities = await getAuthorities();
    commit('authenticated', { user, authorities });
  },
  logout: async ({ commit }) => {
    await doLogout();
    commit('logout');
  },
};

export default {
  namespaced: true,
  state: sessionState,
  getters: sessionGetters,
  mutations: sessionMutations,
  actions: sessionActions,
};
