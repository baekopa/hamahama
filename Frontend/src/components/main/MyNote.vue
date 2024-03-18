<template>
  <v-container>
    <v-row align="center" class="mb-2">
      <span class="text-light-blue-accent-2"> {{ authStore.userName }} </span> 님의 노트
      <v-chip class="create-note ml-2" prepend-icon="mdi-plus" @click="createNote"
        >노트 생성</v-chip
      >
    </v-row>

    <v-row justify="center">
      <v-col cols="12" md="2" v-for="note in NoteList" :key="note.id">
        <v-card class="mb-4" @click="goNoteDetail(note)" hover>
          <v-img :src="note.imgUrl" height="200px"></v-img>
          <v-card-title>{{ note.title }}</v-card-title>
          <v-card-subtitle>{{ note.description }}</v-card-subtitle>
          <v-card-actions>
            <v-btn text color="blue">이건 뺄까? 어차피 누르면 가지는데?</v-btn>
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

const goNoteDetail = (study) => {
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

onMounted(() => {
  // 스터디 리스트 get 요청 -> StudyList에 응답 넣기
})
</script>

<style scoped>
.create-note {
  background-color: #3fb1fa;
  color: white;
}
</style>
