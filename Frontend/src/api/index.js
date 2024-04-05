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
  (config) => {
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
    const newAccessToken = document.cookie.match(/Authorization=([^;]+)/)
    localStorage.setItem('accessToken', newAccessToken[1]) // 로컬 스토리지에 액세스 토큰 저장
    document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:01 GMT;'
  } catch (error) {
    router.push({ name: 'login' }) // 토큰 갱신에 실패한 경우 로그인 페이지로 이동
    console.error('Token refresh failed:', error)
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
    if (error.response?.status == 401) {
      const reissue = await instance.post('/reissue', { withCredentials: true })
      console.log('reissue 끝 토큰세팅 시작')
      console.log(reissue)
      const newAccessToken = document.cookie.match(/Authorization=([^;]+)/)
      localStorage.setItem('accessToken', newAccessToken[1]) // 로컬 스토리지에 액세스 토큰 저장
      document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:01 GMT;'
      const accessToken = localStorage.getItem('accessToken')
      error.config.headers = {
        Authorization: `Bearer ${accessToken}`
      }
      const response = await instance.request(originalConfig)
      return response
    }
    return Promise.reject(error)
  }
)

export default instance
