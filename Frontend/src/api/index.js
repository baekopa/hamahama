import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import { ref } from 'vue'
// accessToken이 필요한 요청은 이걸로다가 편하게 불러서 쓰면될듯?
// function apiInstance() {
//   const accessToken = useAuthStore().accessToken
//   console.log(accessToken)
//   const instance = axios.create({
//     baseURL: import.meta.env.VITE_BASE_URL,

//     headers: {
//       Authorization: `Bearer ${accessToken}`,
//       'Content-Type': 'application/json;charset=utf-8'
//     }
//   })
//   return instance
// }
const accessToken = localStorage.getItem('accessToken')
const isTwice = ref(false)

const instance = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 10000,
  withCredentials: true
})

instance.interceptors.request.use(
  (config) => {
    // config.headers['Content-Type'] = 'application/json'
    config.headers['Authorization'] = `Bearer ${accessToken}`

    return config
  },
  (error) => {
    console.log(error)
    return Promise.reject(error)
  }
)

const tokenRefresh = async () => {
  try {
    console.log('trytokenrefresh')
    const response = await instance.get('/reissue', { withCredentials: true })
    console.log('good')
    const newAccessToken = document.cookie.match(/Authorization=([^;]+)/)
    localStorage.setItem('accessToken', newAccessToken) // 로컬 스토리지에 액세스 토큰 저장
    document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:01 GMT;'
    isTwice.value = false
  } catch (error) {
    console.error('Token refresh failed:', error)
  }
}

instance.interceptors.response.use(
  (response) => {
    if (response.status === 404) {
      window.location.href = '/404'
    }

    return response
  },
  async (error) => {
    if (error.response?.status == 401) {
      if (!isTwice.value) {
        isTwice.value = true
        await tokenRefresh()
      }
      console.log('hi')

      const accessToken = localStorage.getItem('accessToken')

      error.config.headers = {
        Authorization: `Bearer ${accessToken}`
      }

      const response = await axios.request(error.config)
      return response
    }
    return Promise.reject(error)
  }
)

export default instance
