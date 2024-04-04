<template>
  <div>
    <img
      class="cursor-pointer"
      @click="Login('kakao')"
      style="width: 300px"
      src="@/assets/image/login/kakao_login_large_narrow.png"
    />
    <img
      class="mt-10 cursor-pointer"
      style="width: 300px"
      @click="Login('naver')"
      src="@/assets/image/login/naverLoginButton.png"
      alt=""
    />
    <img
      @click="Login('google')"
      class="mt-10 cursor-pointer"
      style="width: 300px"
      src="@/assets/image/login/web_neutral_sq_SI.svg"
      alt=""
    />
  </div>
</template>

<script setup>
const URL = import.meta.env.VITE_BASE_URL
function Login(provider) {
  window.location.href = `${URL}/oauth2/authorization/${provider}`
}
</script>

<style scoped></style>
