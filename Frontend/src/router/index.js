import { createRouter, createWebHistory } from 'vue-router'
import StudyRoutes from './study'
import MyPageRoutes from './mypage'
import AuthRoutes from './auth'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import MainView from '@/views/MainView.vue'
import ErrorView from '@/views/ErrorView.vue'
import CreateNoteView from '@/views/CreateNoteView.vue'
import NoteDetailView from '@/views/NoteDetailView.vue'
import { useAuthStore } from '@/stores/auth'
import ManualView from '@/views/ManualView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },

  {
    path: '/main',
    name: 'main',
    component: MainView
  },
  {
    path: '/404',
    name: 'notFound',
    component: ErrorView
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404'
  },
  {
    path: '/createnote',
    name: 'createnote',
    component: CreateNoteView
  },
  {
    path: '/note/:id',
    name: 'note',
    component: NoteDetailView
  },
  {
    path: '/manual',
    name: 'manual',
    component: ManualView
  },
  ...StudyRoutes,
  ...MyPageRoutes,
  ...AuthRoutes
  // 라우트 추가
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})
router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0)
  next()
})
// const authStore = useAuthStore()

// router.beforeEach((to, from) => {
//   if (!authStore.isLogin && to.name !== 'login') {
//     return { name: 'login' }
//   }
// })

export default router
