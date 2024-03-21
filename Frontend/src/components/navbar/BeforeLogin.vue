<template>
  <v-container class="d-flex align-center bg-white w-screen">
    <v-row class="justify-space-between">
      <img
        @click="goHome"
        src="@/assets/image/logo.png"
        alt="LOGO"
        width="50px"
        class="logo align-center"
      />

      <div class="start d-flex align-center">
        <p @click="goLogin">시작하기</p>
      </div>
    </v-row>
  </v-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

const router = useRouter()

const goLogin = function () {
  router.push({ name: 'login' })
}
const goHome = () => {
  router.push({ name: 'home' })
}
</script>

<style scoped>
.logo {
  margin-left: 220px;
}
.start {
  margin-right: 220px;
}
</style>
