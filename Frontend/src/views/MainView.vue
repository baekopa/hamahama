<!-- 로그인 후 메인페이지 -->
<template>
  <v-container>
    <Slider class="my-10" />
    <div class="note-list pa-10">
      <v-row align="center" class="mb-2 ml-16 text-2xl">
        <span class="text-light-blue-accent-2"> {{ authStore.userName }} </span> 님의 노트
        <v-chip class="create-note ml-5" prepend-icon="mdi-plus" @click="CreateNote"
          >노트 생성</v-chip
        >
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="2" v-for="note in NoteList" :key="note.id">
          <v-card class="mb-4" @click="GoNoteDetail(note)" hover>
            <v-img :src="noteBasicImage" alt="." height="200px"></v-img>
            <v-card-title>{{ note.title }}</v-card-title>
            <v-card-subtitle>{{ note.createdAt }}</v-card-subtitle>
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
import Slider from '@/components/main/Slider.vue'
import instance from '@/api/index'
import noteBasicImage from '@/assets/image/home/NoteBasic.jpg';

const authStore = useAuthStore()
const router = useRouter()


const CreateNote = () => {
  router.push({ name: 'createnote' })
}

const GoNoteDetail = (study) => {
  router.push({ name: 'note', params: { id: study.id } })
}

const NoteList = ref(null)

const GoStudyPage = (study) => {
  router.push({ name: 'study', params: { id: study.id } })
}

const StudyList = ref(null)

function GetPersonalData() {
  const accessToken = localStorage.getItem('accessToken')
  const config = {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`
    }
  };
  instance
    .get('api/members/me', config)
    .then((res) => {
      console.log(res.data)
      const userData = res.data;
      if (userData.status === 200) {
        authStore.userName = userData.data.name
        authStore.userEmail = userData.data.email
        authStore.userImgUrl = userData.data.image_url
      }
      return instance.get('api/members/me/studies', config)
      
    })
    .then((res) => {
      console.log(res.data)
      StudyList.value = res.data.data
    })
    .then((res) => {
      console.log(res)
      return instance.get('api/members/me/notes', config)
    })
    .then((res) => {
      console.log(res.data)
      NoteList.value = res.data.data
    })
    .catch((err) => {
      console.log(err)
    })
}



onMounted(() => {
  GetPersonalData()
  
})
</script>

<style></style>
