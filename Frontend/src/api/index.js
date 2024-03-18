import axios from 'axios'

function apiInstance() {
  const instance = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL,

    headers: {
      Authorization: 'fa',
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  return instance
}

export { apiInstance }
