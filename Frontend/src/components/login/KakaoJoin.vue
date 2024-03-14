<template>
  <div>로그인중하마</div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import * as authApi from '@/api/auth'
const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const code = ref('')
onMounted(() => {
  code.value = route.query.code
  console.log(code.value)
  getToken()
})

const goMainPage = () => {
  router.push({ name: 'main' })
}

function getToken() {
  // BE 서버에 code를 주고 서비스에 로그인하는 과정을 거쳐보자
  console.log('kakao 코드 -> BE')
  authApi
    .kakaoLogin(code.value)
    .then((res) => {
      console.log(res)
      console.log('로그인성공일걸?')
      authStore.isLogin = true
      goMainPage()
    })
    .catch((err) => {
      console.log(err)
      authStore.isLogin = true
      goMainPage()
      // router.push({ name: 'home' })
    })
}
</script>

<style scoped></style>
