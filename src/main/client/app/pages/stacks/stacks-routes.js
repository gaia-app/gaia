const stacksRoutes = [
  {
    path: '/stacks',
    name: 'stacks',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stacks.vue'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Stacks' }],
      title: 'Gaia - Stacks',
    },
  },
  {
    path: '/stacks/:stackId',
    name: 'stack',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack.vue'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack' }],
      title: 'Gaia - Stack edition',
    },
    children: [
      {
        path: 'add',
        name: 'stack_creation',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-creation.vue'),
        props: true,
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack creation' }],
        },
        title: 'Gaia - Stack creation',
      },
      {
        path: 'edit',
        name: 'stack_edition',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-edition.vue'),
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack edition' }],
        },
        title: 'Gaia - Stack edition',

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
          title: 'Gaia - Stack job',
        },
      },
    ],
  },
];

export default stacksRoutes;
