const jobsRoutes = [
  {
    path: '/jobs/:id',
    name: 'job',
    component: () => import(/* webpackChunkName: "chunk-jobs" */ '@/pages/jobs/job.vue'),
    meta: { authorities: ['ROLE_USER'] },
  },
];

export default jobsRoutes;
