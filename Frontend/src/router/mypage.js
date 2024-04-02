export default [
  {
    path: '/mypage',
    name: 'mypage',
    component: () => import('@/views/MyPageView.vue')
  },
  {
    path: '/mypage/:where',
    name: 'mypagewhere',
    component: () => import('@/views/MyPageView.vue')
  }
]
