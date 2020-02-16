import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const appRoutes = [];

export default new Router({
    mode: 'history',
    routes: [...appRoutes],
});
