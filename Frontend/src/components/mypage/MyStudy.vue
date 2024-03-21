<template>
  <v-container>
    <span class="title text-h6">내 노트</span>
    <v-row class="study-list">
      <v-col cols="12" sm="6" md="4" lg="3">
        <v-card
          @click="addStudy"
          width="300"
          height="300"
          text="내 노트 추가"
          variant="outlined"
          color="black"
        ></v-card>
      </v-col>

      <v-col cols="12" sm="6" md="4" lg="3" v-for="note in noteList" :key="note.id">
        <v-card
          @click="goNoteDetail(note.id)"
          width="300"
          height="300"
          variant="outlined"
          color="indigo"
        >
          <v-card-title>
            {{ note.title }}
          </v-card-title>
          <v-card-text class="truncate-text">
            {{ note.content }}
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getStudyList } from '@/api/mypage'
const router = useRouter()

const noteList = ref([
  {
    id: 1,
    title: 'Axios란 무엇인가?',
    content:
      'Ajax의 요청을 해주는 라이브러리 입니당. (JSP에서도 사용가능함.) Ajax는 기본적으로 비동기 요청이고 , Front-end에서 Back-end로 요청을 하는 건데Ajax의 요청을 해주는 라이브러리 입니당. (JSP에서도 사용가능함.) Ajax는 기본적으로 비동기 요청이고 , Front-end에서 Back-end로 요청을 하는 건데 Ajax의 요청을 해주는 라이브러리 입니당. (JSP에서도 사용가능함.) Ajax는 기본적으로 비동기 요청이고 , Front-end에서 Back-end로 요청을 하는 건데 Ajax의 요청을 해주는 라이브러리 입니당. (JSP에서도 사용가능함.) Ajax는 기본적으로 비동기 요청이고 , Front-end에서 Back-end로 요청을 하는 건데 Ajax의 요청을 해주는 라이브러리 입니당. (JSP에서도 사용가능함.) Ajax는 기본적으로 비동기 요청이고 , Front-end에서 Back-end로 요청을 하는 건데 ',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 2,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 3,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 4,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 5,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 6,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 7,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 8,
    title: 'CS스터디',
    content: '1시간 후 스터디 시작!',
    imgUrl: 'https://vuejs.org/images/logo.png'
  }
])

const goNoteDetail = (id) => {
  router.push({ name: 'note', params: { id } })
}
const goMyStudy = () => {
  router.push({ name: 'mystudy' })
}

const addStudy = () => {
  alert('뭘로만들지? 모달?')
}

onMounted(() => {
  getStudyList()
})
</script>

<style scoped>
.truncate-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: mormal;
  max-height: 200px;
  /* Adjust max-width if needed */
}
.study-list {
  border-radius: 10px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
  max-height: 650px;
}
.chip {
  background-color: #3fb1fa;
}
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
