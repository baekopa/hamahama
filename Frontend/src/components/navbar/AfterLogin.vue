<template>
  <v-container class="d-flex align-center bg-white w-screen">
    <v-row class="justify-space-between">
      <img
        @click="goMain"
        src="@/assets/image/logo.png"
        alt="LOGO"
        width="50px"
        class="logo align-center cursor-pointer"
      />

      <div class="start d-flex align-center">
        <v-btn @click="goMakeStudy" id="study-btn" class="mr-10" rounded="l">
          <img class="mx-2" src="../icons/nav/homePlus.svg" alt="" /><span class="mr-2"
            >스터디 생성</span
          ></v-btn
        >
        <button v-if="isAlarmExist" class="mr-5">
          <img width="32" height="32" src="../icons/nav/alarmExist.svg" alt="alarm" />
        </button>
        <button v-else class="mr-5">
          <img width="32" height="32" src="../icons/nav/alarmNone.svg" alt="alarm" />
        </button>

        <v-menu offset-y>
          <template v-slot:activator="{ props: activatorProps }">
            <img
              :src="authStore.userImgUrl"
              alt="userImg"
              width="40px"
              class="mr-5"
              v-bind="activatorProps"
            />
          </template>

          <v-list>
            <v-list-item @click="goToMyPage">마이페이지</v-list-item>
            <v-list-item @click="LogOut">로그아웃</v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

const router = useRouter()
const isAlarmExist = ref(false)

const LogOut = () => {
  sessionStorage.setItem('isLoginHAMAHAMA', false)
  router.push({ name: 'home' })
}

const goToMyPage = () => {
  router.push({ name: 'mypage' })
}
const goMain = () => {
  router.push({ name: 'main' })
}

const goMakeStudy = () => {
  router.push({ name: 'makeStudy' })
}
</script>

<style scoped>
#study-btn {
  width: 126px;
  height: 40px;
  color: white;
  background: linear-gradient(to right, rgba(63, 177, 250, 1), rgba(5, 212, 192, 1));
}

.logo {
  margin-left: 220px;
}
.start {
  margin-right: 220px;
}
</style>
