import axios from 'axios'
import { useAuthStore } from '@/stores/auth'

// accessToken이 필요한 요청은 이걸로다가 편하게 불러서 쓰면될듯?
function apiInstance() {
  const accessToken = useAuthStore().accessToken
  console.log(accessToken)
  const instance = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL,

    headers: {
      Authorization: `Bearer ${accessToken}`,
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  return instance
}

export { apiInstance }
