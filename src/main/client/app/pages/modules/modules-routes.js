const modulesRoutes = [
  {
    path: '/modules',
    name: 'modules',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/modules.vue'),
    meta: { authorities: ['ROLE_USER'] },
  },
  {
    path: '/modules/import',
    name: 'module-import',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/module-import.vue'),
    meta: { authorities: ['ROLE_USER'] },
  },
  {
    path: '/modules/:id',
    name: 'module',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/module.vue'),
    meta: { authorities: ['ROLE_USER'] },
  },
  {
    path: '/modules/:id/description',
    name: 'module-description',
    component: () => import(/* webpackChunkName: "chunk-modules" */ '@/pages/modules/module-description.vue'),
    meta: { authorities: ['ROLE_USER'] },
  },
];

export default modulesRoutes;
