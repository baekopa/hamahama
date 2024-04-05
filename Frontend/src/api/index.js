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

const tokenRefresh = async () => {
  const accessToken = localStorage.getItem('accessToken')
  if (accessToken == null || !accessToken) {
    router.push({ name: 'login' })
  }
  try {
    console.log('reissue보냄')
    const response = await instance.post('/reissue', { withCredentials: true })
    console.log('res_data', response.data)
    console.log('res', response)
    const newAccessToken = response.data.accessToken // 새로운 액세스 토큰
    localStorage.setItem('accessToken', newAccessToken) // 로컬 스토리지에 새로운 액세스 토큰 저장
    instance.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}` // 새로운 토큰 설정
    return response
  } catch (error) {
    router.push({ name: 'login' }) // 토큰 갱신에 실패한 경우 로그인 페이지로 이동
    console.error('Token refresh failed:', error)
    throw error
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
        await tokenRefresh()
        return instance(originalConfig)
      } catch (error) {
        return Promise.reject(error)
      }
    }
    return Promise.reject(error)
  }
)

export default instance
