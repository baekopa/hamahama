<template>
  <div>
    <img @click="KakaoLogin" src="@/assets/image/login/kakao_login_medium_wide.png"></img>
  </div>
</template>

<script setup>
const KakaoLogin = () => {
  window.location.href = "http://localhost:8080/oauth2/authorization/kakao"
}
</script>

<style scoped></style>
