<template>
  <v-container>
    <v-layout style="max-height: 800px">
      <v-navigation-drawer style="width: 323px; height: 800px">
        <p class="text-3xl text-center mt-10 point-font text-stone-900">같이하마</p>
        <v-list lines="two" density="compact" nav>
          <v-list-item three-line>
            <v-list-item-content class="align-self-center">
              <v-list-item-title class="ml-14 mt-10"
                ><div class="text-2xl font-bold">
                  {{ studyStore.studyTitle }}
                </div></v-list-item-title
              >
              <v-list-item-subtitle class="ml-14 mt-1"
                ><div class="text-base">
                  {{ studyStore.studyDescription }}
                </div></v-list-item-subtitle
              >
            </v-list-item-content>
          </v-list-item>

          <div class="ml-8 mt-8">
            <v-list-item
              @click="GoHome()"
              prepend-icon="mdi-view-dashboard"
              value="home"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >스터디 홈</v-list-item
            >
            <v-list-item
              prepend-icon="mdi-forum"
              value="summary"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >요약</v-list-item
            >
            <v-list-item
              @click="GoQuiz()"
              prepend-icon="mdi-help-box"
              value="quiz"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >리마인드 퀴즈</v-list-item
            >
            <v-list-item
              @click="GoSetting()"
              prepend-icon="mdi-account-key"
              value="setting"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >스터디 관리</v-list-item
            >
          </div>
        </v-list>
      </v-navigation-drawer>
      <v-divider style="height: 900px" class="mr-10" vertical></v-divider>
      <v-main class="ml-10 mt-5" style="min-height: 800px">
        <v-container>
          <div class="title">
            <p class="text-2xl ml-5 font-bold">
              <span class="tossface text-3xl">📝 </span
              ><span class="point-color font-bold">{{ studyStore.studyTitle }}</span> 미팅 요약
              리스트
            </p>
            <p class="text-base ml-5 mt-2 italic text-gray-500">
              <span>{{ studyStore.studyTitle }}</span
              >에서 함께 공부한 내용
            </p>
          </div>
          <v-divider
            :thickness="2"
            class="border-opacity-50 my-3"
            style="width: 1300px"
            color="info"
          ></v-divider>

          <v-card rounded="0" variant="flat" class="note-list">
            <div class="list-section">
              <v-row class="pa-10">
                <v-col
                  cols="12"
                  sm="6"
                  md="4"
                  lg="3"
                  class="mb-8"
                  v-for="summary in summaryList"
                  :key="summary.id"
                >
                  <v-card
                    @click="GoSummaryDetail(summary)"
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
                      <v-card-subtitle class="my-1">
                        <span class="me-1"
                          ><span class="tossface">⏲</span> {{ summary.studyAt }}
                        </span>
                      </v-card-subtitle>
                      <div class="ml-2 mt-2 mb-1 text-3xl leading-normal font-bold note-card">
                        <p>{{ summary.topic }}</p>
                      </div>
                      <div class="grid justify-items-start">
                        <v-card-subtitle class="my-1">
                          <span class="me-1 d-flex align-center justify-center">
                            <span
                              v-for="member in summary.memberInfoDTOList"
                              class="tossface"
                              :key="member.id"
                            >
                              {{ member.name }} </span
                            >참여</span
                          >
                        </v-card-subtitle>
                        <v-chip v-if="summary.study != null" variant="tonal">
                          {{ summary.study }}
                          <img :src="summary.studyImage" class="shared-study-image" end />
                        </v-chip>
                      </div>
                    </v-card-item>
                    <v-divider class="mx-4 mb-1"></v-divider>
                  </v-card>
                </v-col>
              </v-row>
            </div>
          </v-card>
        </v-container>
      </v-main>
    </v-layout>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import instance from '@/api/index'
import { useStudyStore } from '@/stores/study'

const studyStore = useStudyStore()
const router = useRouter()
const route = useRoute()
const studyId = route.params.id

const summaryList = ref([])

function GoSetting() {
  router.push({ name: 'studySetting', params: { id: studyId } })
}
function GoHome() {
  router.push({ name: 'study', params: { id: studyId } })
}
function GoQuiz() {
  router.push({ name: 'studyQuiz', params: { id: studyId } })
}
function GoSummaryDetail(info) {
  studyStore.studyAt = info.studyAt
  studyStore.meetingTopic = info.topic
  studyStore.meetingMembers = info.memberInfoDTOList
  console.log(studyStore.studyAt)
  router.push({
    name: 'studySummaryDetail',
    params: { id: info.id, studyId: studyId }
  })
}

function LoadSummaryList() {
  instance
    .get(`api/studies/${studyId}/meetings/end`)
    .then((res) => {
      if (res.data.status == 200) {
        summaryList.value = res.data.data.meetingStudyDTOList
      }
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  LoadSummaryList()
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
  height: 25px;
  width: 25px;
  border-radius: 50%;
  margin-left: 10px;
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
