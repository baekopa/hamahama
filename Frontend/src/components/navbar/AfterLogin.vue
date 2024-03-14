<template>
  <v-container class="d-flex align-center">
    <v-row class="justify-space-between">
      <img
        @click="goMain"
        src="@/assets/image/logo.png"
        alt="LOGO"
        width="50px"
        class="align-center cursor-pointer"
      />

      <div class="d-flex align-center">
        <v-btn class="mr-10" rounded="l">스터디 생성</v-btn>
        <button class="mr-5">
          <img
            width="32"
            height="32"
            src="https://img.icons8.com/office/16/alarm.png"
            alt="alarm"
          />
        </button>

        <v-menu offset-y>
          <template v-slot:activator="{ props: activatorProps }">
            <img
              :src="authStore.userImgUrl"
              alt="userImg"
              width="40px"
              class="mr-5"
              v-bind="activatorProps"
              v-on="on"
            />
          </template>

          <v-list>
            <v-list-item @click="goToMyPage">마이페이지</v-list-item>
            <v-list-item @click="authStore.Logout">로그아웃</v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-row>
  </v-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

const router = useRouter()

const goToMyPage = () => {
  router.push({ name: 'mypage' })
}
const goMain = () => {
  router.push({ name: 'main' })
}
</script>

<style scoped></style>
