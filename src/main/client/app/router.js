import Vue from 'vue';
import VueRouter from 'vue-router';

import { loginRoutes } from '@/pages/login';
import { dashboardRoutes } from '@/pages/dashboard';
import { modulesRoutes } from '@/pages/modules';
import { stacksRoutes } from '@/pages/stacks';
import { credentialsRoutes } from '@/pages/credentials';
import { settingsRoutes } from '@/pages/settings';
import { usersRoutes } from '@/pages/users';

import {
  AppPageForbidden,
  AppPageNotFound,
} from '@/shared/components';
import { authenticationGuard } from '@/shared/services/authentication-guard';
import { updatePageTitleGuard } from '@/shared/services/update-page-title-guard';

Vue.use(VueRouter);

const appRoutes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard',
    meta: { title: 'Gaia - A terraform UI' },
  },
  {
    path: '/not-found',
    name: 'page-not-found',
    component: AppPageNotFound,
    meta: { layout: 'error' },
  },
  {
    path: '/forbidden',
    name: 'page-forbidden',
    component: AppPageForbidden,
    meta: { layout: 'error' },
  },
];

const router = new VueRouter({
  mode: 'history',
  routes: [
    ...loginRoutes,
    ...dashboardRoutes,
    ...modulesRoutes,
    ...stacksRoutes,
    ...credentialsRoutes,
    ...settingsRoutes,
    ...usersRoutes,
    ...appRoutes,
  ],
});
router.beforeEach(authenticationGuard);
router.beforeEach(updatePageTitleGuard);

export default router;
