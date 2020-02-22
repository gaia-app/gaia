import {
  doLogin,
  doLogout,
  getRoles,
  getUser,
} from '@/shared/api';

const sessionState = {
  login: false,
  authenticated: false,
  user: null,
  roles: null,
};

const sessionGetters = {};

const sessionMutations = {
  login: (state) => {
    state.login = true;
  },
  authenticated: (state, { user, roles }) => {
    state.login = false;
    state.authenticated = true;
    state.user = user;
    state.roles = roles;
  },
  logout: (state) => {
    state.login = false;
    state.authenticated = false;
    state.user = null;
    state.roles = null;
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
    const roles = await getRoles();
    commit('authenticated', { user, roles });
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
