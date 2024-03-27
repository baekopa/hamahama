<template>
  <!-- 같이하마 -->
  <v-container>
    <div class="title">
      <span class="text-2xl ml-5 font-bold">
        <span class="tossface text-3xl">📖</span> 같이하마</span>
        <p class="text-xl ml-5 mt-2 italic text-gray-500">백오파님께서 참여 중인 스터디 입니다.</p>
    </div>
    <!-- <v-chip class="chip" variant="flat"> 전체 </v-chip>
    <v-chip class="chip" variant="flat"> 내가 만든 스터디 </v-chip> -->

    <v-card rounded="0" variant="flat" class="study-list">
      <div class="list-section">
        <v-row class="pa-10">
          <v-col cols="12" sm="6" md="4" lg="3" class="mb-8">
            <v-card
              @click="GoMyStudy"
              elevation="4"
              width="240"
              height="350"
              text="개인 스터디 룸"
            ></v-card>
          </v-col>
          <v-col cols="12" sm="6" md="4" lg="3">
            <v-card
              @click="AddStudy"
              elevation="4"
              variant="outlined"
              class="rounded-lg study-card"
              color="#3FB1FA"
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
              <v-card-item>
                <v-card-subtitle class="mt-1">
                  <span class="me-1 text-gray-900">오늘도 파이팅하세요! (•̀ᴗ•́)و ̑̑</span>
                </v-card-subtitle>
              </v-card-item>
              <img
                src="@/assets/image/main/karina.jpg"
                cover
                class="rounded-sm items-center mx-auto study-card-image"
              />
              <v-divider class="mx-4 mb-1"></v-divider>
              <div class="px-4">
                <v-chip-group v-model="selection">
                  <v-chip>혼자하마</v-chip>
                </v-chip-group>
              </div>
              <v-card-title>개인스터디</v-card-title>
            </v-card>
          </v-col>
          <v-col cols="12" sm="6" md="4" lg="3" class="mb-8" v-for="study in StudyList" :key="study.id">
            <v-card
              @click="GoStudyHome(study.id)"
              variant="outlined"
              class="rounded-lg study-card"
              color="indigo-darken-3"
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

              <v-card-item>
                <v-card-subtitle class="mt-1">
                  <span class="me-1"><span class="tossface">⏲</span> {{ study.time }}</span>
                </v-card-subtitle>
              </v-card-item>
              <img
                :src=study.imgUrl
                cover
                class="rounded-sm items-center mx-auto study-card-image"
              />
              <v-divider class="mx-4 mb-1"></v-divider>
              <div class="px-4">
                <v-chip-group v-model="selection">
                  <v-chip>{{ study.category }}</v-chip>
                </v-chip-group>
              </div>
              <v-card-title>{{ study.studyName }}</v-card-title>
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
                <p>새 스터디 생성</p>
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
import instance from '@/api'
const router = useRouter()

const defaultList = ref([
  {
    id: -1,
    time: '',
    studyName: '개인 스터디 룸',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  }
])
const StudyList = ref([
  {
    id: 1,
    time: '1시간 후 스터디 시작!',
    studyName: 'CS스터디',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 2,
    time: '24/03/15 20:00',
    studyName: '면접스터디',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 3,
    time: '24/03/18 20:00',
    studyName: '오픽스터디',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 4,
    time: '24/03/21 20:00',
    studyName: '독서모임',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 5,
    time: '24/03/24 20:00',
    studyName: '뭐하지.',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 6,
    time: '1시간 후 스터디 시작!',
    studyName: 'CS스터디',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 7,
    time: '24/03/15 20:00',
    studyName: '면접스터디',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 8,
    time: '24/03/18 20:00',
    studyName: '오픽스터디',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 9,
    time: '24/03/21 20:00',
    studyName: '독서모임',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  },
  {
    id: 10,
    time: '24/03/24 20:00',
    studyName: '뭐하지.',
    imgUrl: 'https://vuejs.org/images/logo.png',
    category: 'CS'
  }
])

const GoStudyHome = (id) => {
  router.push({ name: 'study', params: { id } })
}
const GoMyStudy = () => {
  router.push({ name: 'myStudy' })
}

const AddStudy = () => {
  router.push({ name: 'makeStudy' })
}

const GetStudyList = () => {
  instance
    .get(`/members/me/studies`)
    .then((response) => {
      // StudyList 채우기
      console.log(response)
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  GetStudyList()
})
</script>

<style scoped>
.v-card {
  border-radius: 30px;
}
.study-list {
  width: 1320px;
  height: 700px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
}

.study-card {
  border-color: rgb(203, 203, 203);
}

.study-card-image {
  height: 220px; 
  width: 248px;
  object-fit: cover;
}

.chip {
  background-color: #3fb1fa;
}
::-webkit-scrollbar {
  background-color: red;
}
::-webkit-scrollbar:hover {
  background-color: blue;
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
