<template>
  <div>로그인 중입니다.</div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useLoadStore } from '@/stores/load'

const authStore = useAuthStore()
const loadStore = useLoadStore()
const router = useRouter()

// 서버에서 쿠키에 set한 accessToken(Authorization)을
// 로컬스토리지로 옮기고
// 쿠키에 있는건 삭제한 후
// 로그인 처리 후
// 메인페이지로 라우팅
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

onMounted(SetAccessToken)
</script>

<style></style>
