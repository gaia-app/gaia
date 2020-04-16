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
    path: '/stacks/new',
    name: 'stack_creation',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-creation.vue'),
    props: true,
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack creation' }],
      title: 'Gaia - Stack creation',
    },
  },
  {
    path: '/stacks/:stackId',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack.vue'),
    meta: {
      authorities: ['ROLE_USER'],
    },
    children: [
      {
        path: 'edit',
        name: 'stack_edition',
        props: true,
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-edition.vue'),
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [{ text: 'Stacks', to: { name: 'stacks' } }, { text: 'Stack edition' }],
          title: 'Gaia - Stack edition',
        },
      },
      {
        path: 'jobs/:jobId',
        name: 'job',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/job/job.vue'),
        props: true,
        meta: {
          authorities: ['ROLE_USER'],
          breadcrumb: [
            { text: 'Stacks', to: { name: 'stacks' } },
            { text: 'Stack', to: { name: 'stack_edition' } },
            { text: 'Job' },
          ],
          title: 'Gaia - Stack job',
        },
      },
    ],
  },
];

export default stacksRoutes;
