<template>
  <div>
    <img @click="KakaoLogin" src="@/assets/image/login/kakao_login_medium_wide.png"></img>
  </div>
</template>

<script setup>
const URL = import.meta.env.VITE_BASE_URL
const KakaoLogin = () => {
  window.location.href = `${URL}/api/oauth2/authorization/kakao`
}
</script>

<style scoped></style>
