<template>
  <v-container>
    <div class="title">
      <span class="text-2xl">
        <span class="text-2xl point-font point-color">{{ authStore.userName }}</span>
        님 반가워요! 오늘도 파이팅 <span class="tossface text-3xl">👏</span></span
      >
    </div>

    <div class="mt-10 d-flex justify-start">
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click=""
          class="mr-8 rounded-lg"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          height="200"
          image=""
          width="200"
          theme="dark"
          title="개인스터디 룸"
        ></v-card>
      </v-hover>
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click="goStudyRoom"
          class="mr-8 rounded-lg"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          height="200"
          image=""
          width="200"
          theme="dark"
          title="스터디 룸"
        ></v-card>
      </v-hover>
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click=""
          class="mr-8 rounded-lg"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          image="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          height="200"
          width="200"
          theme="dark"
          title="내 노트들"
        ></v-card>
      </v-hover>
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click=""
          class="mr-8 rounded-lg"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          height="200"
          image="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          width="200"
          theme="dark"
          title="사용 가이드"
        ></v-card>
      </v-hover>
    </div>

    <div class="schedule-group d-flex mt-16 w-full">
      <div class="weekly-study w-3/5">
        <span class="text-2xl"><span class="tossface text-3xl">📅</span> 주간 스터디 일정</span>
        <MyCalendar />
      </div>
      <div class="schedule ml-10 w-2/5">
        <div class="mb-1">
          <span class="text-2xl"><span class="tossface text-3xl">📅</span> 같이하마 일정</span>
        </div>
        <div>
          <span class="text-lg italic ml-2 text-zinc-400">어떤 주제에 대해 공부하나요?</span>
        </div>
        <v-card variant="flat" class="scroll-container overflow-y-auto mt-7" max-height="330">
          <v-list two-line>
            <template v-for="(item, index) in scheduleItems" :key="index">
              <v-list-item class="mb-3">
                <template v-slot:title>{{ item.title }} {{ item.name }}</template>
                <template v-slot:subtitle>
                  <span class="font-weight-bold">{{ item.date }}</span>
                </template>
              </v-list-item>
            </template>
          </v-list>
        </v-card>
      </div>
    </div>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

import instance from '@/api'
import MyCalendar from '@/components/mypage/MyCalendar.vue'
import Swal from 'sweetalert2'

const authStore = useAuthStore()
const router = useRouter()

// 다음 스터디
const nextStudyId = ref('1')

const scheduleItems = ref([
  {
    title: '[토익 스터디]',
    name: 'Ali Connors',
    date: '2024.03.12'
  },
  {
    title: 'Summer BBQ',
    name: 'to Alex, Scott, Jennifer',
    date: '2024.03.12'
  },
  {
    title: 'Oui oui',
    name: 'Sandra Adams',
    date: '2024.03.12'
  },
  {
    title: 'Brunch this weekend?',
    name: 'Ali Connors',
    date: '2024.03.12'
  },
  {
    title: 'Summer BBQ',
    name: 'to Alex, Scott, Jennifer',
    date: '2024.03.12'
  },
  {
    title: 'Oui oui',
    name: 'Sandra Adams',
    date: '2024.03.12'
  },
  {
    title: 'Brunch this weekend?',
    name: 'Ali Connors',
    date: '2024.03.12'
  },
  {
    title: 'Summer BBQ',
    name: 'to Alex, Scott, Jennifer',
    date: '2024.03.12'
  },
  {
    title: 'Oui oui',
    name: 'Sandra Adams',
    date: '2024.03.12'
  }
])

const GetDashBoardInfo = () => {
  instance
    .get('api/members/me/dashboard')
    .then((response) => {
      console.log(response)
    })
    .catch((err) => {
      console.log(err)
    })
}
const GetMyInfo = () => {
  instance
    .get('/api/members/me')
    .then((res) => {
      if (res.data.status == 200) {
        authStore.userName = res.data.data.name
        authStore.userEmail = res.data.data.email
        authStore.userImgUrl = res.data.data.image_url
      } else {
        // Swal.fire()
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

// const GetWeeklySchedule = () => {
//   instance
//     .get('members/me/study-timeline')
//     .then((res) => {
//       console.log(res)
//     })
//     .catch((err) => {
//       console.log(err)
//     })
// }

const EditMyInfo = () => {
  instance
    .put('api/members/me')
    .then((response) => {
      // 여기서 필요한 처리를 수행합니다.
    })
    .catch((error) => {
      // 에러 처리
    })
}

const goSelfStudyRoom = () => {
  // router.push({name:'selfstudy'})
}

const goStudyRoom = () => {
  router.push({ name: 'study', params: { id: nextStudyId.value } })
}

const goMyNotes = () => {
  // router.push({ name: 'notes' })
  console.log('개인스터디룸 ㄱㄱ')
}

const goUserGuide = () => {
  // 이거 모달로할까
  console.log('개인스터디룸 ㄱㄱ')
}

onMounted(() => {
  GetMyInfo()
  GetDashBoardInfo()
})
</script>

<style scoped>
/* 스크롤 기능을 위한 스타일 */
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
