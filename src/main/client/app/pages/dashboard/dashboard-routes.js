import { isUserAuthenticated } from '@/shared/services';

import AppDashboard from '@/pages/dashboard/dashboard.vue';

const dashboardRoutes = [
  {
    path: '/dashboard',
    name: 'dashboard',
    component: AppDashboard,
    beforeEnter: isUserAuthenticated,
  },
];

export default dashboardRoutes;
