<script setup>
import { ref, watchEffect, onMounted } from 'vue'
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useLoadStore } from '@/stores/load'
import AfterLogin from '@/components/navbar/AfterLogin.vue'
import BeforeLogin from '@/components/navbar/BeforeLogin.vue'
import instance from '@/api/index'

const authStore = useAuthStore()
const loadStore = useLoadStore()
const router = useRouter()

function GetMyInfo() {
  instance
    .get(`api/members/me`)
    .then((res) => {
      if (res.data.status == 200) {
        const userInfo = res.data.data
        authStore.userName = userInfo.name
        authStore.userImgUrl = userInfo.image_url
        authStore.userEmail = userInfo.email
        authStore.provider = userInfo.provider
      }
    })
    .catch((err) => {
      router.push({ name: 'login' })
    })
}

onMounted(() => {
  GetMyInfo()
})
</script>

<template>
  <v-app>
    <v-overlay :model-value="loadStore.isLoading" class="align-center justify-center" persistent>
      <v-progress-circular color="primary" size="64" indeterminate></v-progress-circular>
    </v-overlay>
    <!-- 로그인 상태에 따라 AfterLogin 또는 BeforeLogin 컴포넌트 렌더링 -->
    <AfterLogin v-if="authStore.isLogin" />
    <BeforeLogin v-else />

    <!-- 메인 컨텐츠 영역 -->

    <!-- RouterView를 이용해 현재 라우트에 맞는 컴포넌트를 렌더링 -->
    <RouterView :style="{ filter: loadStore.isLoading ? 'blur(5px)' : 'none' }" />
  </v-app>
</template>

<style>
::-webkit-scrollbar {
  border-radius: 30px;
  width: 1px;
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

* {
  font-family: 'NanumSquareNeo';
}

@font-face {
  font-family: 'UhBeeSe_hyun';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_five@.2.0/UhBeeSe_hyun.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.point-font {
  font-family: 'UhBeeSe_hyun';
}

.point-color {
  color: #3fb1fa;
}

.tossface {
  font-family: Tossface;
}

.cursor-pointer {
  cursor: pointer;
}
</style>
