const usersRoutes = [
  {
    path: '/users',
    name: 'users',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/users/users.vue'),
    meta: {
      authorities: ['ROLE_ADMIN'],
      breadcrumb: [{ text: 'Users' }],
      title: 'Gaia - Users',
    },
  },
  {
    path: '/users/:username',
    name: 'user_edition',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/users/user-edit.vue'),
    meta: {
      authorities: ['ROLE_ADMIN'],
      breadcrumb: [{ text: 'Users', to: { name: 'users' } }, { text: 'User edition' }],
    },
    props: true,
  },
];

export default usersRoutes;
