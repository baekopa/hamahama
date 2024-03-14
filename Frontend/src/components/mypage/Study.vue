<template>
  <div>
    <span class="title text-h6">내 스터디</span>
    <v-chip class="chip" variant="flat"> 전체 </v-chip>
    <v-chip class="chip" variant="flat"> 내가 만든 스터디 </v-chip>
    <div class="study-list">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6" md="4" lg="3" v-for="study in StudyList" :key="study.id">
            <v-card
              class="mx-auto"
              max-width="300"
              height="300"
              :text="study.studyName"
              variant="outlined"
              color="indigo"
            ></v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

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
</script>

<style scoped>
.study-list {
  border-radius: 10px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
  max-height: 700px;
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
