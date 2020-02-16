import { isUserAuthenticated } from '@/shared/services';

import AppLoginForm from '@/pages/login/login.vue';

const loginRoutes = [
  {
    path: '/login',
    name: 'login',
    component: AppLoginForm,
    beforeEnter: isUserAuthenticated,
  },
];

export default loginRoutes;
