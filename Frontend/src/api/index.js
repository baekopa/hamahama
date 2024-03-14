import axios from 'axios'

function apiInstance() {
  const instance = axios.create({
    // baseURL: process.env.VUE_APP_API_BASE_URL,
    baseURL: 'http://localhost:8080/',
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  return instance
}

export { apiInstance }
