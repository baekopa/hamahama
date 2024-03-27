<template>
  <div class="filter drop-shadow">
    <div class="bg-white">
      <div class="mx-20 flex justify-between">
        <div class="">
          <img
            @click="goHome"
            src="@/assets/image/logo.png"
            alt="LOGO"
            class="logo h-16"
          />
        </div>

        <div class="start my-auto">
          <p @click="goLogin">시작하기</p>
        </div>
      </div>
    </div>
  </div>
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
.navbar {
  box-shadow: 3px;
}
</style>
