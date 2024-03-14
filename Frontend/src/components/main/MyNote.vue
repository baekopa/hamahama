<template>
  <v-container>
    <v-row align="center" class="mb-2">
      <span class="text-light-blue-accent-2"> {{ authStore.userName }} </span> 님의 노트
      <v-chip prepend-icon="mdi-brightness-5" @click="createNote" class="ml-2"> 노트 만들기</v-chip>
    </v-row>

    <v-row justify="center">
      <v-col cols="12" md="2" v-for="study in StudyList" :key="study.id">
        <v-card class="mb-4" @click="handleCardClick(study)" hover>
          <v-img :src="study.imgUrl" height="200px"></v-img>
          <v-card-title>{{ study.title }}</v-card-title>
          <v-card-subtitle>{{ study.description }}</v-card-subtitle>
          <v-card-actions>
            <v-btn text color="blue">Learn More</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const router = useRouter()

const createNote = () => {
  router.push({ name: 'createnote' })
}

const handleCardClick = (study) => {
  console.log(study.id)
  alert('아직 안만듬 ㅇㅇ', study.id)
}

const StudyList = ref([
  {
    id: 1,
    title: '공부하자',
    description: 'Learn the basics of Vue.js, a progressive JavaScript framework.',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 2,
    title: 'OAtuh란 무엇인가',
    description: 'Dive deeper into Vue.js and explore advanced concepts and techniques.',
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

onMounted(() => {
  // 스터디 리스트 get 요청 -> StudyList에 응답 넣기
})
</script>

<style scoped></style>
