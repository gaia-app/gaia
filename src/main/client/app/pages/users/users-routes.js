import { isUserAuthenticated } from '../../shared/services';

const usersRoutes = [
  {
    path: '/users',
    name: 'users',
    component: () => import(/* webpackChunkName: "chunk-users" */ '@/pages/users/users.vue'),
    beforeEnter: isUserAuthenticated,
  },
];

export default usersRoutes;
