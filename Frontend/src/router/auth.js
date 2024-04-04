export default [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/auth',
    name: 'auth',
    component: () => import('@/views/AuthView.vue')
  }
]
