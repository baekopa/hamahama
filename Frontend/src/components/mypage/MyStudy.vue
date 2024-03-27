<template>
  <v-container>
    <div class="title">
      <p class="text-2xl ml-5 font-bold">
        <span class="tossface text-3xl">📝</span> 공부하마</p>
        <p class="text-xl ml-5 mt-2 italic text-gray-500">백오파님께서 정리한 노트입니다.</p>
    </div>

    <v-card rounded="0" variant="flat" class="note-list">
      <div class="list-section">
        <v-row class="pa-10">
          <v-col cols="12" sm="6" md="4" lg="3" class="mb-8" v-for="note in noteList" :key="note.id">
            <v-card
              @click="goNoteDetail(note.id)"
              variant="outlined"
              class="rounded-lg study-card"
              color="#4e4e4e"
              hover
              width="250"
              height="380"
            >
              <template v-slot:loader="{ isActive }">
                <v-progress-linear
                  :active="isActive"
                  color="deep-purple"
                  height="4"
                  indeterminate
                ></v-progress-linear>
              </template>

              <v-card-item class="grid content-between">
                <div class="ml-2 mt-2 mb-1 text-3xl leading-normal font-bold note-card">
                  {{ note.title }}
                </div>
                <div class="grid justify-items-start">
                  <v-card-subtitle class="my-1">
                    <span class="me-1"><span class="tossface">⏲</span> {{ note.time }} 작성</span>
                  </v-card-subtitle>
                  <v-chip v-if="note.study != null"
                    variant="tonal"
                  >
                    {{ note.study }}
                    <img :src=note.studyImage class="shared-study-image" end />
                  </v-chip>
                </div>
              </v-card-item>
              <v-divider class="mx-4 mb-1"></v-divider>
            </v-card>
          </v-col>
          <v-col cols="12" sm="6" md="4" lg="3" class="mb-8 d-flex align-center">
            <v-card
              @click="addStudy"
              variant="tonal"
              color="#3FB1FA"
              class="rounded-lg d-flex justify-center items-center"
              hover
              width="250"
              height="380"
            >
              <div class="text-2xl text-center">
                <p>+</p>
                <p>새 노트</p>
              </div>
            </v-card>
          </v-col>
        </v-row>
      </div>
    </v-card>
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
    time: '2024-03-22',
    study: 'CS면접',
    studyImage: 'https://vuejs.org/images/logo.png',
  },
  {
    id: 2,
    title: 'CS 면접 대비 : Network TCP/IP 개념과 OSI 7계층',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22',
  },
  {
    id: 3,
    title: 'CS 면접 대비 : Network TCP/IP 개념과 OSI 7계층 글자 수를 더 채우기!',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22',
    study: '공유스터디 명',
    studyImage: 'https://vuejs.org/images/logo.png',
  },
  {
    id: 4,
    title: 'Study CS for interview : [Subject] Network TCP/IP ',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22',
    study: '공유스터디 명',
    studyImage: 'https://vuejs.org/images/logo.png',
  },
  {
    id: 5,
    title: 'CS스터디',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22',
    study: '공유스터디명',
    studyImage: 'https://vuejs.org/images/logo.png',
  },
  {
    id: 6,
    title: 'CS스터디',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22',
    study: '공유스터디명',
    studyImage: 'https://vuejs.org/images/logo.png',
  },
  {
    id: 7,
    title: 'CS스터디',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22',
    study: '공유스터디명',
    studyImage: 'https://vuejs.org/images/logo.png',
  },
  {
    id: 8,
    title: 'CS스터디',
    content: '내가혼자공부한 내용은 다음과같습니다. 주저리 주저리',
    time: '2024-03-22'
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

.v-card {
  border-radius: 30px;
}

.note-list {
  width: 1320px;
  height: 700px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
}

.shared-study-image {
  height:25px; 
  width:25px; 
  border-radius:50%;
  margin-left:10px;
  object-fit: cover;
}

.note-card {
  height: 280px;
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
  background-color: #dbdbdb;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #afafaf;
}
</style>
