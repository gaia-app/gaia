import Vue from 'vue';
import Router from 'vue-router';

import { loginRoutes } from './pages/login';
import { dashboardRoutes } from './pages/dashboard';
import { modulesRoutes } from './pages/modules';
import { stacksRoutes } from './pages/stacks';
import { jobsRoutes } from './pages/jobs';
import { settingsRoutes } from './pages/settings';
import { usersRoutes } from './pages/users';

import { AppPageNotFound } from './shared/components';
import { isUserAuthenticated } from './shared/services';

Vue.use(Router);

const appRoutes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard',
    beforeEnter: isUserAuthenticated,
  },
  {
    path: '*',
    name: 'page-not-found',
    component: AppPageNotFound,
    beforeEnter: isUserAuthenticated,
  },
];

export default new Router({
  mode: 'history',
  routes: [
    ...loginRoutes,
    ...dashboardRoutes,
    ...modulesRoutes,
    ...stacksRoutes,
    ...jobsRoutes,
    ...settingsRoutes,
    ...usersRoutes,
    ...appRoutes,
  ],
});
