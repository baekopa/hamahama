<!-- 로그인 후 메인페이지 -->
<template>
  <v-container>
    <Slider class="my-3" />

    <!-- 노트 리스트 -->
    <div class="flex justify-center mt-20">
      <v-card variant="flat" style="width:1400px">
        <div class="d-flex justify-between">
          <div class="title d-flex items-center">
            <span class="text-3xl font-bold">
              <span class="tossface text-3xl">📚 </span>
              <span class="point-color font-bold">{{ authStore.userName }}</span>님의 노트</span>
              <v-chip class="create-note ml-5" prepend-icon="mdi-plus" size="large" @click="mainPageStore.GoCreateNote()" >노트 작성</v-chip>
              <v-chip class="create-note ml-5" prepend-icon="mdi-format-list-bulleted" size="large" @click="mainPageStore.GoCreateNote()" >전체 노트</v-chip>
          </div>
        </div>
        <v-divider :thickness="2" class="border-opacity-50 my-3" style="width: 1400px" color="info"></v-divider>
        
        <div class="flex" style="width:1500px">
          <div class="d-flex ml-5 mt-5">
            <div class="mr-6 mb-8" v-for="note in noteList" :key="note.id">
              <v-card
                @click="GoNoteDetail(note.id)"
                variant="text"
                class="rounded-xl study-card"
                color="#2e2e2e"
                hover
                width="250"
                height="350"
              >
                <template v-slot:loader="{ isActive }">
                  <v-progress-linear
                    :active="isActive"
                    color="deep-purple"
                    height="4"
                    indeterminate
                  ></v-progress-linear>
                </template>

                <v-card-item class="grid content-between note-card rounded-xl" style="height:350px">
                  <div class="grid" style="height:320px">
                    <div>
                      <div class="mx-2 mt-2 mb-1 text-xl font-bold line-clamp-2 text-gray-600">
                      {{ note.title }}
                      </div>
                      <span class="mx-2 mt-2 text-gray-400 italic">{{ note.modifiedAt }} 작성</span>
                      <div class="mx-2 mt-3 line-clamp-6 text-gray-500">
                        {{ note.content }}
                      </div>
                    </div>
                    <div class="place-self-end">
                    <v-chip v-if="note.isShared == true"
                      variant="flat"
                      color="#aaaaaa"
                    >
                      공유중
                      <!-- <img :src=note.studyImage class="shared-study-image" end /> -->
                    </v-chip>
                  </div>
                  </div>
                </v-card-item>
              </v-card>
            </div>
          </div>
        </div>
      </v-card>
    </div>

    <!-- 스터디 리스트 -->
    <div class="flex justify-center mt-20">
      <v-card variant="flat" style="width:1400px">
        <div class="d-flex justify-between">
          <div class="title d-flex items-center">
            <span class="text-3xl font-bold">
              <span class="tossface text-3xl">👥 </span>
              <span class="point-color font-bold">{{ authStore.userName }}</span>님이 참여중인 스터디</span>
              <!-- <v-chip class="create-note ml-5" prepend-icon="mdi-plus" size="large" @click="mainPageStore.GoCreateNote()" ></v-chip> -->
              <v-chip class="create-note ml-5" prepend-icon="mdi-format-list-bulleted" size="large" @click="mainPageStore.GoCreateNote()" >전체 스터디</v-chip>
          </div>
        </div>
        <v-divider :thickness="2" class="border-opacity-50 my-3" style="width: 1400px" color="info"></v-divider>
        <div class="" style="width:1500px">
          <div class="d-flex ml-5 mt-5">
            <div class="mr-6 mb-8">
              <!-- 개인스터디 -->
              <v-card
                @click="GoStudyPage(study.id)"
                variant="flat"
                class="rounded-lg study-card"
                color="#3FB1FA33"
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
            </div>
            <!-- 그룹스터디 -->
            <div class="mr-6 mb-8" v-for="study in studyList" :key="study.id">
              <v-card
                @click="GoStudyPage(study.id)"
                variant="flat"
                class="rounded-lg study-card"
                color="#f1f1f1"
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
                    <span v-if="study.scheduledTime" class="me-1"><span class="tossface">⏲ </span> {{ study.scheduledTime }}</span>
                    <span v-else class="me-1"><span class="tossface">⏲ </span>예정된 일정 없음</span>
                  </v-card-subtitle>
                </v-card-item>
                <img
                  :src="study.backgroundImage"
                  cover
                  class="rounded-sm items-center mx-auto study-card-image"
                />
                <v-divider class="mx-4 mb-1"></v-divider>
                <div class="px-4">
                  <v-chip-group v-model="selection">
                    <v-chip>{{ study.category }}</v-chip>
                  </v-chip-group>
                </div>
                <v-card-title>{{ study.title }}</v-card-title>
              </v-card>
            </div>
          </div>
        </div>
      </v-card>
    </div>
    <div class="mb-32"></div>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useMainPageStore } from '@/stores/mainPage'
import Slider from '@/components/main/Slider.vue'
import instance from '@/api/index'
import noteBasicImage from '@/assets/image/home/NoteBasic.png'

const authStore = useAuthStore()
const mainPageStore = useMainPageStore()
const router = useRouter()
const noteList = ref(null)
const studyList = ref([])

function GoNoteDetail(id) {
  router.push({ name: 'note', params: { id } })
}

function GoStudyPage(id) {
  router.push({ name: 'study', params: { id } })
}

async function GetPersonalData() {
  await instance
    .get('/api/members/me/main')
    .then((res) => {
      console.log(res)
      const personalData = res.data
      if (personalData.status === 200) {
        noteList.value = personalData.data.notes
        studyList.value.push(personalData.data.personalStudy)
        studyList.value.push(...personalData.data.studies)

        mainPageStore.myStudy = studyList.value[0].id
        mainPageStore.myStudyImg = studyList.value[0].backgroundImage
        mainPageStore.recentEditNote = noteList.value[0].id
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

function GetMyInfo() {
  instance
    .get(`/api/members/me`)
    .then((res) => {
      if (res.data.status == 200) {
        const userInfo = res.data.data
        authStore.userName = userInfo.name
        authStore.userImgUrl = userInfo.image_url
        authStore.userEmail = userInfo.email
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  GetPersonalData()
  GetMyInfo()
})
</script>

<style>
.carousel-container {
  width: 1300px;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 중앙 정렬을 위한 추가 */
  align-items: center;
}
.note-card {
  height:350px;
  border: 1px solid #bfbfbf;
}
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
</style>
