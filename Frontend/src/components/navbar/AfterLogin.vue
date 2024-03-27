<template>
  <div class="filter drop-shadow">
    <div class="bg-white">
      <div class="mx-20 flex justify-between">
        <div class="">
          <img @click="goMain" src="@/assets/image/logo.png" alt="LOGO" class="logo h-16" />
        </div>

        <div class="start flex justify-center">
          <v-btn @click="goMakeStudy" id="study-btn" class="mr-7 my-auto" rounded="l">
            <img class="mx-2" src="../icons/nav/homePlus.svg" alt="" /><span class="mr-2"
              >스터디 생성</span
            ></v-btn
          >
          <button v-if="isAlarmExist" class="mr-7 my-auto">
            <img width="32" height="32" src="../icons/nav/alarmExist.svg" alt="alarm" />
          </button>
          <button v-else class="mr-7 my-auto">
            <img width="32" height="32" src="../icons/nav/alarmNone.svg" alt="alarm" />
          </button>

          <div class="my-auto">
            <v-menu offset-y>
              <template v-slot:activator="{ props: activatorProps }">
                <img
                  :src="authStore.userImgUrl"
                  alt="userImg"
                  v-bind="activatorProps"
                  class="h-10"
                />
              </template>

              <v-list>
                <v-list-item @click="goToMyPage">마이페이지</v-list-item>
                <v-list-item @click="authStore.Logout">로그아웃</v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
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
}
.start {
}
</style>
