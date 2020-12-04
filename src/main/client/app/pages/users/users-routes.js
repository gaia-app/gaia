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
    path: '/users/new',
    name: 'new_user',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/users/user-new.vue'),
    meta: {
      authorities: ['ROLE_ADMIN'],
      breadcrumb: [{ text: 'Users' }],
      title: 'Gaia - New User',
    },
  },
  {
    path: '/users/:username',
    name: 'user_edition',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/users/user-edit.vue'),
    meta: {
      authorities: ['ROLE_ADMIN'],
      breadcrumb: [{ text: 'Users', to: { name: 'users' } }, { text: 'User edition' }],
      title: 'Gaia - Edit User',
    },
    props: true,
  },
  {
    path: '/organizations',
    name: 'organizations',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/users/organizations.vue'),
    meta: {
      authorities: ['ROLE_ADMIN'],
      breadcrumb: [{ text: 'Organizations' }],
      title: 'Gaia - Organization',
    },
    props: true,
  },
];

export default usersRoutes;
