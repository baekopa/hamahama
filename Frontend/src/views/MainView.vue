<!-- 로그인 후 메인페이지 -->
<template>
  <v-container>
    <Slider class="my-10" />
    <div class="note-list pa-10">
      <v-row align="center" class="mb-2">
        <span class="text-light-blue-accent-2"> {{ authStore.userName }} </span> 님의 노트
        <v-chip class="create-note ml-2" prepend-icon="mdi-plus" @click="CreateNote"
          >노트 생성</v-chip
        >
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="2" v-for="note in NoteList" :key="note.id">
          <v-card class="mb-4" @click="GoNoteDetail(note)" hover>
            <v-img :src="note.imgUrl" height="200px"></v-img>
            <v-card-title>{{ note.title }}</v-card-title>
            <v-card-subtitle>{{ note.description }}</v-card-subtitle>
            <v-card-actions>
              <v-btn text color="blue">이건 뺄까? 어차피 누르면 가지는데?</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>
    <div class="study-list pa-10">
      <v-row justify="center">
        <v-col cols="12">
          <v-row justify="center" align="center" class="mb-2">
            <v-col>
              <span class="text-light-blue-accent-2">{{ authStore.userName }}</span> 님이 참여중인
              스터디
            </v-col>
          </v-row>

          <v-row justify="center">
            <v-col cols="12" md="2" v-for="study in StudyList" :key="study.id">
              <v-card class="mb-4" @click="GoStudyPage(study)" hover>
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
    </div>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import axios from 'axios'
import Slider from '@/components/main/Slider.vue'
import instance from '@/api/index'

const authStore = useAuthStore()
const router = useRouter()

const CreateNote = () => {
  router.push({ name: 'createnote' })
}

const GoNoteDetail = (study) => {
  router.push({ name: 'note', params: { id: study.id } })
}

const NoteList = ref([
  {
    id: 1,
    title: '내 노트 1',
    description: '여기엔 뭐가 들어가면 좋을까? 마지막 수정 시간?',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 2,
    title: 'OAtuh란 무엇인가',
    description: '2024-03-14',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 3,
    title: '프론트엔드 잘 하는법',
    description: 'Understanding the Composition API in Vue 3.',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 4,
    title: '아으 하기싫어',
    description: "Manage your app's state efficiently with Vuex.",
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 5,
    title: '공부 잘 하는법을 공부하는 노트',
    description: 'Learn how to build SPAs using Vue Router.',
    imgUrl: 'https://vuejs.org/images/logo.png'
  }
])

const GoStudyPage = (study) => {
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

function SetAccessToken() {
  const authorizationCookie = document.cookie.match(/Authorization=([^;]+)/)

  if (authorizationCookie) {
    localStorage.setItem('accessToken', authorizationCookie[1])
    document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:01 GMT;'
  }
}

function GetPersonalData() {
  const accessToken = localStorage.getItem('accessToken')
  instance
    .get('http://localhost:8080/members/main', {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    })
    .then((res) => {
      console.log(res)
      // 데이터 넘어오면 노트리스트, 스터디 리스트 데이터 세팅
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  SetAccessToken()
  GetPersonalData()
})
</script>

<style></style>
