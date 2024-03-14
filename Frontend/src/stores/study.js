import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { defineStore } from 'pinia'

export const useStudyStore = defineStore('study', () => {
  const router = useRouter()
  const studyName = ref('')
  const studyTheme = ref('')
  const studyParticipants = ref([])

  const userName = ref('백오파')
  const studyImgUrl = ref('https://vuejs.org/images/logo.png')
  //   const userEmail = ref('baekOpa@ssafy.com')

  //   const goLogin = function () {
  //     router.push({ name: 'login' })
  //   }
  //   function Login() {
  //     isLogin.value = true
  //     router.push({ name: 'main' })
  //   }
  //   function Logout() {
  //     isLogin.value = false
  //     router.push({ name: 'home' })
  //   }

  // 만들어야 할 것
  // 1. 유저 정보 불러오는 함수 (불러와서 userName, userImgUrl, userEmail에 넣기)

  return { studyName, studyTheme, studyParticipants, userName, studyImgUrl }
})
