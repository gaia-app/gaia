import { isUserAuthenticated } from '../../shared/services';

const stacksRoutes = [
  {
    path: '/stacks',
    name: 'stacks',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stacks.vue'),
    beforeEnter: isUserAuthenticated,
  },
  {
    path: '/stacks/:id',
    name: 'stack',
    component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack.vue'),
    beforeEnter: isUserAuthenticated,
    children: [
      {
        path: 'add',
        name: 'stack-creation',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-creation.vue'),
      },
      {
        path: 'edit',
        name: 'stack-edition',
        component: () => import(/* webpackChunkName: "chunk-stacks" */ '@/pages/stacks/stack-edition.vue'),
      },
    ],
  },
];

export default stacksRoutes;
