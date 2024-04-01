<template>
  <div class="filter drop-shadow">
    <div class="bg-white">
      <div class="mx-20 flex justify-between">
        <div class="">
          <img @click="GoMain" src="@/assets/image/logo.png" alt="LOGO" class="logo h-16" />
        </div>

        <div class="start flex justify-center">
          <v-btn @click="MakeStudy" id="study-btn" class="mr-7 my-auto" rounded="l">
            <img class="mx-2" src="../icons/nav/homePlus.svg" alt="" /><span class="mr-2"
              >스터디 생성</span
            ></v-btn
          >

          <div class="text-center justify-center d-flex align-center mr-5">
            <v-menu v-model="menu" :close-on-content-click="false" location="bottom">
              <template #activator="{ props }">
                <button @click="LoadNoti" v-if="isAlarmExist" v-bind="props">
                  <img width="32" height="32" src="../icons/nav/alarmExist.svg" alt="alarm" />
                </button>
                <button @click="LoadNoti" v-else v-bind="props">
                  <img width="32" height="32" src="../icons/nav/alarmNone.svg" alt="alarm" />
                </button>
              </template>

              <v-card min-width="300" class="mt-4">
                <v-list>
                  <v-list-item
                    prepend-avatar="https://cdn.vuetifyjs.com/images/john.jpg"
                    subtitle="Founder of Vuetify"
                    title="John Leider"
                  >
                  </v-list-item>
                </v-list>

                <v-divider></v-divider>

                <v-list class="noti-list">
                  <v-list-item v-for="noti in notiList" :key="noti.id">
                    <p>
                      {{ noti.content }}
                    </p>
                    <p>
                      {{ noti.time }}
                    </p>
                  </v-list-item>
                </v-list>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" variant="text" @click="menu = false">닫기</v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </div>

          <div class="my-page my-auto">
            <v-menu offset-y>
              <template v-slot:activator="{ props: activatorProps }">
                <button class="flex">
                  <img
                    :src="authStore.userImgUrl"
                    alt="userImg"
                    v-bind="activatorProps"
                    class="h-10 rounded-circle"
                  />
                </button>
              </template>

              <v-list>
                <v-list-item @click="GoMyPage">마이페이지</v-list-item>
                <v-list-item @click="LogOut">로그아웃</v-list-item>
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
import instance from '@/api'

const authStore = useAuthStore()
const router = useRouter()
const isAlarmExist = ref(true)

const menu = ref(false)

// sse 추후 변경 예정
const baseURL = import.meta.env.VITE_BASE_URL
const notiSource = new EventSource(`${baseURL}/api/sse/subscribe`, {
  withCredentials: true,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
    'Content-Type': 'text/event-stream;charset=utf-8'
  }
})

notiSource.onmessage = (event) => {
  // 알림 있다 없다 들어오면 그거 맞춰서  isAlarmExist 바꿔주면될듯
  console.log(event)
}
notiSource.onerror = (err) => {
  console.error('EventSource failed:', err)
}

// evtSource.close();

const notiList = ref([
  { id: 1, type: 1, content: '카리나 님이 스터디에 초대 했어요', time: '24-03-27' },
  { id: 2, type: 1, content: '카리나 님이 스터디에 초대 했어요', time: '24-03-27' },
  { id: 3, type: 1, content: '카리나 님이 스터디에 초대 했어요', time: '24-03-27' },
  { id: 4, type: 1, content: '카리나 님이 스터디에 초대 했어요', time: '24-03-27' },
  { id: 5, type: 1, content: '카리나 님이 스터디에 초대 했어요', time: '24-03-27' },
  { id: 6, type: 1, content: '카리나 님이 스터디에 초대 했어요', time: '24-03-27' }
])

// 알림 종류, 내용, 시간
const LoadNoti = () => {
  instance
    .get(`api/alarm`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

const LogOut = () => {
  sessionStorage.removeItem('isLoginHAMAHAMA')
  authStore.isLogin = false
  router.push({ name: 'home' })
}

const GoMyPage = () => {
  router.push({ name: 'mypage' })
}
const GoMain = () => {
  router.push({ name: 'main' })
}

const MakeStudy = () => {
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

.noti-list {
  overflow-y: scroll;
  max-height: 200px;
}
.start {
}
</style>
