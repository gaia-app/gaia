const stacksRoutes = [
  {
    path: '/stacks',
    name: 'stacks',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stacks.vue'),
    meta: { authorities: ['ROLE_USER'] },
  },
  {
    path: '/stacks/:id',
    name: 'stack',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack.vue'),
    meta: { authorities: ['ROLE_USER'] },
    children: [
      {
        path: 'add',
        name: 'stack-creation',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-creation.vue'),
      },
      {
        path: 'edit',
        name: 'stack-edition',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-edition.vue'),
      },
      {
        path: 'jobs/:jobId',
        name: 'stack_job',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-job.vue'),
      },
    ],
  },
];

export default stacksRoutes;
