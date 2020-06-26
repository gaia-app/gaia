const credentialsRoutes = [
  {
    path: '/credentials',
    name: 'credentials',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/credentials/credentials.vue'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Credentials' }],
      title: 'Gaia - Credentials',
    },
  },
];

export default credentialsRoutes;
