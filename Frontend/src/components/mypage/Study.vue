<template>
  <v-container class="">
    <span class="title text-h6">참여중인 스터디</span>
    <!-- <v-chip class="chip" variant="flat"> 전체 </v-chip>
    <v-chip class="chip" variant="flat"> 내가 만든 스터디 </v-chip> -->

    <v-card class="study-list">
      <v-row class="pa-10">
        <v-col cols="12" sm="6" md="4" lg="3">
          <v-card @click="goMyStudy" width="240" height="350" text="개인 스터디 룸"></v-card>
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="3">
          <v-card @click="addStudy" width="240" height="350" text="스터디 추가"></v-card>
        </v-col>

        <v-col cols="12" sm="6" md="4" lg="3" v-for="study in StudyList" :key="study.id">
          <v-card
            @click="goStudyHome(study.id)"
            width="240"
            height="350"
            :text="study.studyName"
          ></v-card>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import * as myPageApi from '@/api/mypage'
const router = useRouter()

const defaultList = ref([
  {
    id: -1,
    time: '',
    studyName: '개인 스터디 룸',
    imgUrl: 'https://vuejs.org/images/logo.png'
  }
])
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
  },
  {
    id: 6,
    time: '1시간 후 스터디 시작!',
    studyName: 'CS스터디',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 7,
    time: '24/03/15 20:00',
    studyName: '면접스터디',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 8,
    time: '24/03/18 20:00',
    studyName: '오픽스터디',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 9,
    time: '24/03/21 20:00',
    studyName: '독서모임',
    imgUrl: 'https://vuejs.org/images/logo.png'
  },
  {
    id: 10,
    time: '24/03/24 20:00',
    studyName: '뭐하지.',
    imgUrl: 'https://vuejs.org/images/logo.png'
  }
])

const goStudyHome = (id) => {
  router.push({ name: 'study', params: { id } })
}
const goMyStudy = () => {
  router.push({ name: 'myStudy' })
}

const addStudy = () => {
  router.push({ name: 'makeStudy' })
}

const getStudyList = () => {
  myPageApi
    .getStudyList()
    .then((response) => {
      console.log(response)
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  // getStudyList()
})
</script>

<style scoped>
.study-list {
  border-radius: 30px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
  width: 1130px;
  height: 820px;
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
