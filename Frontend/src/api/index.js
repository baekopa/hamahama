import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const isTwice = ref(false)
const router = useRouter()

const instance = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 10000,
  withCredentials: true
})

instance.interceptors.request.use(
  async (config) => {
    config.headers['Authorization'] = `Bearer ${localStorage.getItem('accessToken')}`
    return config
  },
  (error) => {
    console.log(error)
    return Promise.reject(error)
  }
)

async function TokenRefresh() {
  const accessToken = localStorage.getItem('accessToken')
  if (accessToken == null || !accessToken) {
    router.push({ name: 'login' })
  } else {
    try {
      console.log('reissue보냄')
      const response = await instance.post('/reissue')
      console.log('res_data', response.data)
      console.log('res', response)
      const newAccessToken = document.cookie.match(/Authorization=([^;]+)/) // 새로운 액세스 토큰
      if (newAccessToken) {
        localStorage.setItem('accessToken', newAccessToken[1]) // 로컬 스토리지에 새로운 액세스 토큰 저장
        document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:01 GMT;'
        sessionStorage.setItem('isLoginHAMAHAMA', true)
      } else {
        authStore.isLogin = false
        localStorage.clear
        sessionStorage.clear
      }
    } catch (error) {
      router.push({ name: 'login' }) // 토큰 갱신에 실패한 경우 로그인 페이지로 이동
      console.error('Token refresh failed:', error)
      throw error
    }
  }
}

instance.interceptors.response.use(
  (response) => {
    return response
  },
  async (error) => {
    console.log(error)
    const { config, response } = error

    const reissue = await TokenRefresh()
    console.log(reissue)

    return instance(config)
  }
)

export default instance
