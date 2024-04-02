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
                <button @click="LoadNoti(props['aria-expanded'])" v-if="hasNewNotification" v-bind="props">
                  <img width="32" height="32" src="../icons/nav/alarmExist.svg" alt="alarm" />
                </button>
                <button @click="LoadNoti(props['aria-expanded'])" v-else v-bind="props">
                  <img width="32" height="32" src="../icons/nav/alarmNone.svg" alt="alarm" />
                </button>
              </template>

              <v-card width="340" max-height="400" class="mt-4">
                <div class="mt-6 ml-4">
                    <span class="text-xl font-bold"><span class="tossface">📌</span> 알림</span>
                  <v-divider :thickness="4" class="border-opacity-100" style="width: 310px" color="info"></v-divider>
                </div>
                <v-list class="noti-list">

                  <div v-if="notiList.length == 0">
                    <v-list-item>
                      <p class="text-base font-bold mt-3">
                        모든 알림을 확인했어요. <span class="tossface">😁</span>
                      </p>
                    </v-list-item>
                  </div>
                  <div>
                    <v-list-item v-for="noti in notiList" :key="noti.notificationId" @click=checkNotification(noti)>
                      <p class="text-base font-bold mt-3">
                        {{ noti.notificationContent }}
                      </p>
                      <p class="text-sm">
                        {{ noti.createdAt }}
                      </p>
                      <v-divider :thickness="2" class="border-opacity-100" style="width: 310px" color="info"></v-divider>
                    </v-list-item>
                  </div>
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
import { EventSourcePolyfill } from 'event-source-polyfill';
import { onMounted } from 'vue';

const authStore = useAuthStore()
const router = useRouter()
const isAlarmExist = ref(true)
const hasNewNotification = ref(false);

const menu = ref(false)
var notiSource;
// sse 추후 변경 예정
const baseURL = import.meta.env.VITE_BASE_URL

notiSource = new EventSourcePolyfill(`${baseURL}/api/sse/subscribe`, {
  withCredentials: true,
  heartbeatTimeout: 3600000, // 연결 유지 시간 설정 10분
  connectionTimeout: 30000,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
    "Content-Type": 'text/event-stream;charset=utf-8',
    "Cache-Control": "no-cache",
    'Connection': 'keep-alive',
  },
  // heartbeatTimeout: 86400000,
});


notiSource.addEventListener("NEW", (e) => {
  hasNewNotification.value = true;
})

notiSource.addEventListener("CONNECT", (e) => {
  console.log(e);
})

notiSource.onmessage = (event) => {
  // 알림 있다 없다 들어오면 그거 맞춰서  isAlarmExist 바꿔주면될듯
  console.log(event)
}

notiSource.onerror = (err) => {
  console.error('EventSource failed:', err);
  notiSource.close();
}

const notiList = ref([])

// 알림 종류, 내용, 시간
const LoadNoti = (isOpened) => {
   
  // 알림 리스트 열렸을 때만 알림 조회
  if (isOpened == 'false') {
    instance
    .get(`api/notifications`)
    .then((res) => {
      console.log(res)

      // 알림 불 제거
      hasNewNotification.value = false;

      // 알림 리스트 업데이트
      notiList.value = res.data.data.notificationList;
    })
    .catch((err) => {
      console.log(err)
    })
  }
}

// 알림 확인
const checkNotification = (noti) => {
  instance
    .put(`api/notifications/${noti.notificationId}`)
    .then((res) => {
      console.log(res);
      deleteFromNotiList(noti.notificationId);
      moveTo(noti);
    })
    .catch((err) => {
      console.log(err);
    })
}

// notiList에서 확인 알림 제거
const deleteFromNotiList = (notificationId) => {

  const idx = notiList.value.findIndex(notification => notification.notificationId == notificationId);

  if (idx != -1) {
    notiList.value.splice(idx, 1);
  }
}

// 알림 확인으로 페이지 이동
const moveTo = (noti) => {

  switch (noti.notificationType) {
    case 'INVITE':
      joinStudy(noti.relatedContentId);
      break;
    case 'ENTER': case 'DELEGATE': case 'SCHEDULE': case 'UPCOMING':
      router.push({ name: 'study', params: { id: noti.relatedContentId } });
      break;
    case 'SUMMARY': case 'KEYWORD':
      moveToSummary(noti.relatedContentId);
      break;
    case 'REMIND':
      router.push({ name: 'studyQuiz', params: { id: noti.relatedContentId }});
      break;
  }
}

const moveToSummary = (relatedContentId) => {
  const ids = relatedContentId.split("/");
  router.push({
    name: 'studySummaryDetail',
    params: { id: ids[1], studyId: ids[0] }
  });
}

// 스터디 조인!
const joinStudy = (invitationId) => {
  // 조인 하고 스터디 아이디 반환해서 스터디 페이지로 넘어가도록 하기
  console.log(invitationId);
  instance
    .put(`api/members/me/invitations/${invitationId}`)
    .then((res) => {
      console.log(res);
      router.push({ name: 'study', params: { id: res.data.data.studyId } });
    })
    .catch((err) => {
      console.log(err);
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
  max-height: 280px;
}
.start {
}
</style>
