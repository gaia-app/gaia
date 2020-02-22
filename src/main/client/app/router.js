import Vue from 'vue';
import VueRouter from 'vue-router';

import { loginRoutes } from '@/pages/login';
import { dashboardRoutes } from '@/pages/dashboard';
import { modulesRoutes } from '@/pages/modules';
import { stacksRoutes } from '@/pages/stacks';
import { jobsRoutes } from '@/pages/jobs';
import { settingsRoutes } from '@/pages/settings';
import { usersRoutes } from '@/pages/users';

import {
  AppPageForbidden,
  AppPageNotFound,
} from '@/shared/components';
import { authenticationGuard } from '@/shared/services';

Vue.use(VueRouter);

const appRoutes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard',
  },
  {
    path: '/not-found',
    name: 'page-not-found',
    component: AppPageNotFound,
  },
  {
    path: '/forbidden',
    name: 'page-forbidden',
    component: AppPageForbidden,
  },
];

const router = new VueRouter({
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
router.beforeEach(authenticationGuard);

export default router;
