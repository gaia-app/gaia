import { isUserAuthenticated } from '@/shared/services';

const jobsRoutes = [
  {
    path: '/jobs/:id',
    name: 'job',
    component: () => import(/* webpackChunkName: "chunk-jobs" */ '@/pages/jobs/job.vue'),
    beforeEnter: isUserAuthenticated,
  },
];

export default jobsRoutes;
