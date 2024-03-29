<!-- 로그인 후 메인페이지 -->
<template>
  <v-container>
    <Slider class="my-10" />
    <div class="note-list pa-10">
      <v-row align="center" class="mb-2 ml-16 text-2xl">
        <span class="text-light-blue-accent-2"> {{ authStore.userName }} </span> 님의 노트
        <v-chip
          class="create-note ml-5"
          prepend-icon="mdi-plus"
          @click="mainPageStore.GoCreateNote()"
          >노트 생성</v-chip
        >
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="2" v-for="note in noteList" :key="note.id">
          <v-card class="mb-4" @click="GoNoteDetail(note.id)" hover>
            <v-img :src="noteBasicImage" alt="." height="200px"></v-img>
            <v-card-title>{{ note.title }}</v-card-title>
            <v-card-subtitle>{{ note.createdAt }}</v-card-subtitle>
            <v-card-actions>
              <!-- <v-btn text color="blue">이건 뺄까? 어차피 누르면 가지는데?</v-btn> -->
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
            <v-col cols="12" md="2" v-for="study in studyList" :key="study.id">
              <v-card class="mb-4" @click="GoStudyPage(study.id)" hover>
                <v-card-title>{{ study.scheduledTime }}</v-card-title>
                <v-img :src="study.backgroundImage" height="150px"></v-img>
                <v-card-title>{{ study.title }}</v-card-title>
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
import { useMainPageStore } from '@/stores/mainPage'
import Slider from '@/components/main/Slider.vue'
import instance from '@/api/index'
import noteBasicImage from '@/assets/image/home/NoteBasic.jpg'

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
    .get('api/members/me/main')
    .then((res) => {
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

<style></style>
