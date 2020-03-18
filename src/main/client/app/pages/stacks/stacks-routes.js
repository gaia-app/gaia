const stacksRoutes = [
  {
    path: '/stacks',
    name: 'stacks',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stacks.vue'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Stacks' }],
    },
  },
  {
    path: '/stacks/:stackId',
    name: 'stack',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack.vue'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack' }],
    },
    children: [
      {
        path: 'add',
        name: 'stack_creation',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-creation.vue'),
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [{ text: 'Modules', to: { name: 'modules' } }, { text: 'Stack creation' }],
        },
      },
      {
        path: 'edit',
        name: 'stack_edition',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-edition.vue'),
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack edition' }],
        },
      },
      {
        path: 'jobs/:jobId',
        name: 'stack_job',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-job.vue'),
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [
            { text: 'Stacks', to: { name: 'stacks' } },
            { text: 'Stack', to: { name: 'stack' } },
            { text: 'Job' },
          ],
        },
      },
    ],
  },
];

export default stacksRoutes;
