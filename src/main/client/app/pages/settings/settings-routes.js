import { isUserAuthenticated } from '../../shared/services';

const settingsRoutes = [
  {
    path: '/settings',
    name: 'settings',
    component: () => import(/* webpackChunkName: "chunk-settings" */ '@/pages/settings/settings.vue'),
    beforeEnter: isUserAuthenticated,
  },
];

export default settingsRoutes;
