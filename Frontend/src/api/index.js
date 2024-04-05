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
    // config.headers['Content-Type'] = 'application/json'
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
  }
  try {
    console.log('reissue보냄')
    const response = await instance.post('/reissue', { withCredentials: true })
    console.log('res_data', response.data)
    console.log('res', response)
    const newAccessToken = document.cookie.match(/Authorization=([^;]+)/) // 새로운 액세스 토큰
    localStorage.setItem('accessToken', newAccessToken) // 로컬 스토리지에 새로운 액세스 토큰 저장
  } catch (error) {
    router.push({ name: 'login' }) // 토큰 갱신에 실패한 경우 로그인 페이지로 이동
    console.error('Token refresh failed:', error)
    throw error
  }
}

const SetAccessToken = () => {
  loadStore.isLoading = true
  const authorizationCookie = document.cookie.match(/Authorization=([^;]+)/)

  if (authorizationCookie) {
    localStorage.setItem('accessToken', authorizationCookie[1])
    document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:01 GMT;'
    sessionStorage.setItem('isLoginHAMAHAMA', true)
    authStore.isLogin = true
    loadStore.isLoading = false
    setTimeout(() => {
      loadStore.isLoading = false
      router.push({ name: 'main' })
    }, 1000)
  } else {
    loadStore.isLoading = false
  }
}

instance.interceptors.response.use(
  (response) => {
    if (response.status === 404) {
      router.push({ name: 'notFound' })
    }
    return response
  },
  async (error) => {
    console.log(error)
    const originalConfig = error.config
    if (error.response?.status === 401 && !originalConfig._isRetry) {
      originalConfig._isRetry = true
      try {
        await TokenRefresh()
        return instance(originalConfig)
      } catch (error) {
        return Promise.reject(error)
      }
    }
    return Promise.reject(error)
  }
)

export default instance
