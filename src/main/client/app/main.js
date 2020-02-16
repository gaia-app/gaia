import Vue from 'vue';
import App from './app.vue';
import router from './app-routes';

Vue.config.productionTip = false;

new Vue({
    router,
    render: (h) => h(App),
}).$mount('#app');
