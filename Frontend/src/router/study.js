export default [
  {
    path: '/study/:id',
    name: 'study',
    component: () => import('@/views/StudyView.vue')
  },
  {
    path: '/makestudy',
    name: 'makeStudy',
    component: () => import('@/views/MakeStudyView.vue')
  },
  {
    path: '/study/quiz/:id',
    name: 'studyQuiz',
    component: () => import('@/views/StudyQuizView.vue')
  },
  {
    path: '/study/summary/:id',
    name: 'studySummary',
    component: () => import('@/views/StudySummaryView.vue')
  },
  {
    path: '/study/summaryDetail/:studyId/:id',
    name: 'studySummaryDetail',
    component: () => import('@/views/StudySummaryDetailView.vue')
  },
  {
    path: '/study/setting/:id',
    name: 'studySetting',
    component: () => import('@/views/StudySettingView.vue')
  }
]
