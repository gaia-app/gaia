import Vue from 'vue';

import fontawesomeConfig from '@/shared/config/fontawesome-config';

import App from './app.vue';
import router from './app-routes';

fontawesomeConfig.init();

Vue.config.productionTip = false;

new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
