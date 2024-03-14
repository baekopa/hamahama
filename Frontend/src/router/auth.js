export default [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/auth/kakao',
    name: 'kakao',
    component: () => import('@/components/login/KakaoJoin.vue')
  }
]
