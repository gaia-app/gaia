import Vue from 'vue';
import { Multiselect } from 'vue-multiselect';

import fontawesomeConfig from '@/shared/config/fontawesome-config';
import bootstrapVueConfig from '@/shared/config/bootstrap-vue-config';

import {
  AppDefaultLayout,
  AppErrorLayout,
  AppNoneLayout,
} from '@/layouts';
import App from '@/app.vue';
import router from '@/router';
import store from '@/shared/store';

fontawesomeConfig.init();
bootstrapVueConfig.init();
Vue.use(Multiselect);

// layout definitions
Vue.component('app-default-layout', AppDefaultLayout);
Vue.component('app-error-layout', AppErrorLayout);
Vue.component('app-none-layout', AppNoneLayout);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
