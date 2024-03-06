import { createRouter, createWebHistory } from 'vue-router'
import StudyRoutes from './study'
import MyPageRoutes from './mypage'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  ...StudyRoutes,
  ...MyPageRoutes

  // 라우트 추가
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
