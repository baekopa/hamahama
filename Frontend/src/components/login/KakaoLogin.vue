<template>
  <div>
    <img @click="KakaoLogin" src="@/assets/image/login/kakao_login_medium_wide.png"></img>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
const REDIRECT_URI = 'http://localhost:5173/auth/kakao'
const KakaoLogin = () => {
  Kakao.Auth.authorize({
    redirectUri : `${REDIRECT_URI}`

  })
  // const REDIRECT_URI = 'http://localhost:8080/auth/kakao'
  // const REST_API_KEY = 'REST_API_KEY'
  // const AuthUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`
  // window.location.href = AuthUrl
}
</script>

<style scoped></style>
