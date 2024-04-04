import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
  const isLogin = ref(sessionStorage.getItem('isLoginHAMAHAMA'))
  const router = useRouter()
  const userName = ref('백오파')
  const userImgUrl = ref('https://vuejs.org/images/logo.png')
  const userEmail = ref('baekOpa@ssafy.com')
  const accessToken = ref('i10j105d')
  const provider = ref('')

  function Login() {
    isLogin.value = true
    router.push({ name: 'main' })
  }
  function Logout() {
    isLogin.value = false
    router.push({ name: 'home' })
  }

  return { isLogin, Login, Logout, userName, userImgUrl, userEmail, accessToken, provider }
})
