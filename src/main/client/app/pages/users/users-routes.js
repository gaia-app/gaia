const usersRoutes = [
  {
    path: '/users',
    name: 'users',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/users/users.vue'),
    meta: {
      authorities: ['ROLE_ADMIN'],
      breadcrumb: [{ text: 'Users' }],
    },
  },
];

export default usersRoutes;
