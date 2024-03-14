<template>
  <v-container class="d-flex align-center">
    <v-row>
      <img src="@/assets/image/Main/logo.png" alt="LOGO" width="50px" />

      <p>Link</p>

      <p @click="goLogin">로그인</p>
    </v-row>
  </v-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const goLogin = function () {
  router.push({ name: 'login' })
}
</script>

<style scoped></style>
