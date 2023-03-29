const labsRoutes = [  
  {
    path: '/labs',
    name: 'labs',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/labs/labs.vue'),
    meta: {
      authorities: ['ROLE_ADMIN', 'ROLE_PROFESSOR'],
      breadcrumb: [{ text: 'Labs' }],
      title: 'AWS Cloud - Labs',
    },
    props: true,
  },
];

export default labsRoutes;
