import { isUserAuthenticated } from '../../shared/services';

const modulesRoutes = [
  {
    path: '/modules',
    name: 'modules',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/modules.vue'),
    beforeEnter: isUserAuthenticated,
  },
  {
    path: '/modules/import',
    name: 'module-import',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/module-import.vue'),
    beforeEnter: isUserAuthenticated,
  },
  {
    path: '/modules/:id',
    name: 'module',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/module.vue'),
    beforeEnter: isUserAuthenticated,
  },
  {
    path: '/modules/:id/description',
    name: 'module-description',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/module-description.vue'),
    beforeEnter: isUserAuthenticated,
  },
];

export default modulesRoutes;
