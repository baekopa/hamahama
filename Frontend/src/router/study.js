export default [
  {
    path: '/study',
    name: 'study',
    component: () => import('@/views/StudyView.vue')
  },
  {
    path: '/study/schedule/:id',
    name: 'studySchedule',
    component: () => import('@/components/study/StudySchedule.vue')
  },
  {
    path: '/study/summary/:id',
    name: 'studySummary',
    component: () => import('@/components/study/StudySummary.vue')
  }
]
