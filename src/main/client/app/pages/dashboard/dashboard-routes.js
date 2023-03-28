import AppDashboard from '@/pages/dashboard/dashboard.vue';

const dashboardRoutes = [
  {
    path: '/dashboard',
    name: 'dashboard',
    component: AppDashboard,
    meta: {
      authorities: ['ROLE_STUDENT'],
      breadcrumb: [{ text: 'Dashboard' }],
      title: 'Gaia - Dashboard',
    },
  },
];

export default dashboardRoutes;
