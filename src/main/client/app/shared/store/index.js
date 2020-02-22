import Vue from 'vue';
import Vuex from 'vuex';

import sessionStoreModule from '@/shared/store/modules/session-store-module';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    session: sessionStoreModule,
  },
  strict: process.env.NODE_ENV !== 'production',
});

export default store;
