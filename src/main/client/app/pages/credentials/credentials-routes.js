const credentialsRoutes = [
  {
    path: '/credentials',
    name: 'credentialsList',
    component: () => import(/* webpackChunkName: "chunk-credentials" */ '@/pages/credentials/credentials-list.vue'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Credentials' }],
      title: 'Gaia - Credentials',
    },
  },
  {
    path: '/credentials/new',
    name: 'new_credentials',
    component: () => import(/* webpackChunkName: "chunk-credentials" */ '@/pages/credentials/new-credentials'),
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Credentials', to: { name: 'credentialsList' } }, { text: 'New Credentials' }],
      title: 'Gaia - New credentials edition',
    },
  },
  {
    path: '/credentials/:credentialsId',
    name: 'credentials',
    component: () => import(/* webpackChunkName: "chunk-credentials" */ '@/pages/credentials/credentials.vue'),
    props: true,
    meta: {
      authorities: ['ROLE_USER'],
      breadcrumb: [{ text: 'Credentials', to: { name: 'credentialsList' } }, { text: 'Credentials edition' }],
      title: 'Gaia - Credentials edition',
    },
  },
];

export default credentialsRoutes;
