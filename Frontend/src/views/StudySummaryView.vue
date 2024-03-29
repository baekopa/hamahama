<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer floating permanent>
        <v-list density="compact" nav>
          <v-list-item
            @click="GoHome()"
            prepend-icon="mdi-view-dashboard"
            title="홈"
            value="home"
          ></v-list-item>
          <v-list-item prepend-icon="mdi-forum" title="요약" value="summary"></v-list-item>
          <v-list-item
            @click="GoQuiz()"
            prepend-icon="mdi-forum"
            title="리마인드 퀴즈"
            value="quiz"
          ></v-list-item>
          <v-list-item
            @click="GoSetting()"
            prepend-icon="mdi-forum"
            title="스터디 관리"
            value="setting"
          ></v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-main>
        <div class="title">
          <p class="text-2xl ml-5 font-bold"><span class="tossface text-3xl">📝</span> 같이하마</p>
          <p class="text-xl ml-5 mt-2 italic text-gray-500">
            <span>{{ studyStore.studyTitle }}</span
            >에서 함께 공부한 내용
          </p>
        </div>

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
                  @click="GoSummaryDetail(summary.id)"
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
                      <span class="me-1"><span class="tossface">⏲</span> {{ summary.time }} </span>
                    </v-card-subtitle>
                    <div class="ml-2 mt-2 mb-1 text-3xl leading-normal font-bold note-card">
                      {{ summary.title }}
                    </div>
                    <div class="grid justify-items-start">
                      <v-card-subtitle class="my-1">
                        <span class="me-1">
                          <span v-for="member in summary.members" class="tossface">
                            {{ member }}, </span
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
      </v-main>
    </v-layout>
  </v-card>
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

const summaryList = ref([
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜라미어캣타워',
    members: ['김가네수민', '이가네수민'],
    time: '2024/03/08 PM 3:00'
  }
])

function GoSetting() {
  router.push({ name: 'studySetting', params: { id: studyId } })
}
function GoHome() {
  router.push({ name: 'study', params: { id: studyId } })
}
function GoQuiz() {
  router.push({ name: 'studyQuiz', params: { id: studyId } })
}
function GoSummaryDetail(id) {
  router.push({ name: 'studySummaryDetail', params: { id: id, studyId: studyId } })
}

function LoadSummaryList() {
  instance
    .get(`api/studies/${studyId}/summary`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(LoadSummaryList)
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
