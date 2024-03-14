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
  ...StudyRoutes,
  ...MyPageRoutes,
  ...AuthRoutes
  // 라우트 추가
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
