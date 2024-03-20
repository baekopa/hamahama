<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12">
        <v-row justify="start" align="center" class="mb-2">
          <v-col>
            <span class="text-light-blue-accent-2">{{ authStore.userName }}</span> 님이 참여중인
            스터디
          </v-col>
        </v-row>

        <v-row justify="center">
          <v-col cols="12" md="2" v-for="study in StudyList" :key="study.id">
            <v-card class="mb-4" @click="goStudyPage(study)" hover>
              <v-card-title>{{ study.time }}</v-card-title>
              <v-img :src="study.imgUrl" height="150px"></v-img>
              <v-card-title>{{ study.studyName }}</v-card-title>
              <!-- <v-card-actions>
                <v-btn text color="blue">Learn More</v-btn>
              </v-card-actions> -->
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const router = useRouter()

const goStudyPage = (study) => {
  router.push({ name: 'study', params: { id: study.id } })
}

const StudyList = ref([
  {
    id: 1,
    time: '1시간 후 스터디 시작!',
    studyName: 'CS스터디',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 2,
    time: '24/03/15 20:00',
    studyName: '면접스터디',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 3,
    time: '24/03/18 20:00',
    studyName: '오픽스터디',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 4,
    time: '24/03/21 20:00',
    studyName: '독서모임',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 5,
    time: '24/03/24 20:00',
    studyName: '뭐하지.',
    imgUrl: 'https://vuejs.org/images/logo.png'
  }
])

onMounted(() => {
  // 스터디 리스트 get 요청 -> StudyList에 응답 넣기
})
</script>

<style scoped></style>
