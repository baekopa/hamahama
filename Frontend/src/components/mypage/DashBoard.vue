<template>
  <v-container>
    <div class="title d-flex flex-column">
      <span class="text-h5">{{ authStore.userName }}</span>
      <span class="text-h6">님은 오늘도 열공중!</span>
    </div>

    <div class="mt-10 d-flex">
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click=""
          class="mx-auto"
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
          class="mx-auto"
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
          class="mx-auto"
          :class="{ 'on-hover': isHovering }"
          :elevation="isHovering ? 16 : 2"
          v-bind="props"
          height="200"
          image="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          width="200"
          theme="dark"
          title="내 노트들"
        ></v-card>
      </v-hover>
      <v-hover v-slot="{ isHovering, props }">
        <v-card
          @click=""
          class="mx-auto"
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

    <div class="schedule-group d-flex mt-16">
      <div class="weekly-study">
        <span class="text-h6">주간 스터디 일정</span>
        <div></div>
      </div>
      <div class="schedule">
        <span class="text-h6">일정</span>
        <v-card class="scroll-container overflow-y-auto" max-width="425" max-height="300">
          <v-list two-line>
            <template v-for="(item, index) in scheduleItems" :key="index">
              <v-list-item class="mt-3">
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
// import * as myPageApi from '@/api/mypage'
import instance from '@/api'

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

const GetWeeklySchedule = () => {
  instance
    .get('members/me/study-timeline')
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

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
  GetDashBoardInfo()
  GetWeeklySchedule()
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
