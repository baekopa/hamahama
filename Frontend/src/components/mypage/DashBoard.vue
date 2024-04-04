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
          @click="GoUserGuide()"
          class="mr-8 rounded-lg pa-4"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          height="200"
          image="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          width="800"
        >
          <div class="font-weight-black">
            <p class="text-h5 font-weight-black">영어로 배우는 오늘의 명언</p>
            <p class="mt-2 text-h6 font-weight-black">
              {{ quote[randomIndex].eng }}
            </p>
            <p class="mt-2 text-h6 font-weight-black">
              {{ quote[randomIndex].kor }}
            </p>
            <p class="mt-2 text-h6 font-weight-black">{{ quote[randomIndex].talker }}</p>
          </div>
        </v-card>
      </v-hover>
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click="GoUserGuide()"
          class="mr-8 rounded-lg"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          height="200"
          image="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          width="200"
          theme="dark"
          title="사용자 설명서"
        >
        </v-card>
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
            <template v-for="(study, index) in scheduleItems" :key="index">
              <div v-for="(meet, idx) in study.meetings" :key="idx">
                <v-list-item class="mb-3">
                  <template v-slot:title>{{ study.studyName }} - {{ meet.topic }}</template>
                  <template v-slot:subtitle>
                    <span class="font-weight-bold">{{ meet.studyAt }}</span>
                  </template>
                </v-list-item>
              </div>
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
import axios from 'axios'

const authStore = useAuthStore()
const router = useRouter()

// 다음 스터디
const nextStudyId = ref('1')

const scheduleItems = ref([])

const quote = ref([
  {
    id: 1,
    eng: 'To live is to suffer, to survive is to find some meaning in the suffering.',
    kor: '사는 것은 고통 받는 것이고, 살아남는 것은 고통 속에서 어떤 의미를 찾는 것이다.',
    talker: '프리드리히 니체'
  },
  {
    id: 2,
    eng: 'This thing that we call failure is not the falling down, but the staying down.',
    kor: '우리가 실패라고 부르는 것은 추락하는 것이 아니라 추락한 채로 있는 것이다.',
    talker: '메리 픽퍼드'
  },
  {
    id: 3,
    eng: `In the end, we only regret the chances we didn't take.`,
    kor: '결국, 우리는 우리가 잡지 않았던 기회들을 후회하게 된다.',
    talker: '루이스 캐럴'
  },
  {
    id: 4,
    eng: 'the soul would have no rainbow had the eyes no tears.',
    kor: '눈물을 흘리지 않으면 영혼에 무지개를 품을 수 없다.',
    talker: '존 반스 체이니'
  },
  {
    id: 5,
    eng: 'Laugh, and the world laughs with you Weep, and you weep alone',
    kor: '웃어라, 세상이 너와 함께 웃을 것이다. 울어라, 너 혼자 울게 될 것이다.',
    talker: '엘라 윌콕스'
  },
  {
    id: 6,
    eng: `Something filled up my heart with nothing, someone told me not to cry. Now that I'm older, my heart is colder, I can see that it's a lie.`,
    kor: '뭔가가 내 마음을 공허로 가득 채웠고, 누군가 나에게 슬퍼하지 말라고 했다. 하지만 나는 이제 더 나이가 들었고, 더 냉정해졌기에, 그것이 거짓말이었다는 것을 안다.',
    talker: '아케이드 파이어'
  }
])

const randomIndex = Math.floor(Math.random() * 6)

const GetDashBoardInfo = () => {
  instance
    .get('api/members/me/dashboard')
    .then((res) => {
      console.log(res.data.message)
      scheduleItems.value = res.data.data.weekStudies
    })
    .catch((err) => {
      console.log(err)
    })
}
const GetMyInfo = () => {
  instance
    .get('api/members/me')
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

function GoMyNotes() {
  router.push({ name: 'mypagewhere', params: { where: 'MyStudy' } })
}

function GoUserGuide() {
  router.push({ name: 'manual' })
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
