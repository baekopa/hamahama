<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import AfterLogin from '@/components/navbar/AfterLogin.vue'
import BeforeLogin from '@/components/navbar/BeforeLogin.vue'
const auth = useAuthStore()
</script>

<template>
  <v-app>
    <!-- 로그인 상태에 따라 AfterLogin 또는 BeforeLogin 컴포넌트 렌더링 -->
    <AfterLogin v-if="auth.isLogin" />
    <BeforeLogin v-else />

    <!-- 메인 컨텐츠 영역 -->

    <!-- RouterView를 이용해 현재 라우트에 맞는 컴포넌트를 렌더링 -->
    <RouterView />
  </v-app>
</template>

<style>
::-webkit-scrollbar {
  border-radius: 30px;
  width: 8px;
}

::-webkit-scrollbar-track {
  background-color: white;
  border-radius: 30px;
}

::-webkit-scrollbar-thumb {
  background-color: #a5b4fc;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #818cf8;
}
</style>
