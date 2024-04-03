<template>
  <v-container>
    <v-layout style="max-height: 800px">
      <v-navigation-drawer style="width: 323px; height: 800px">
        <p class="text-3xl text-center mt-10 point-font text-stone-900">
          {{ studyStore.studyType }}
        </p>
        <v-list lines="two" density="compact" nav>
          <v-list-item three-line>
            <v-list-item-content class="align-self-center">
              <div class="ml-14 mt-10">
                <div class="text-xl font-bold block">
                  {{ studyStore.studyTitle }}
                </div>
              </div>
              <v-list-item-subtitle class="ml-14 mt-1"
                ><div class="text-base">
                  {{ studyStore.studyCategory }}
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
              @click="GoSummary()"
              prepend-icon="mdi-forum"
              value="summary"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >요약</v-list-item
            >
            <v-list-item
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
          <div v-if="isList" class="remind-quiz">
            <div class="d-flex justify-between">
              <div class="title d-flex flex-column">
                <span class="text-2xl ml-5 font-bold">
                  <span class="tossface text-3xl">📭 </span
                  ><span class="point-color font-bold">{{ studyStore.studyTitle }}</span
                  >의 리마인드 퀴즈</span
                >
                <p class="text-base ml-5 mt-2 italic text-gray-500">
                  <span>{{ '스터디에서 학습한 내용을 복습해보세요.' }}</span>
                </p>
              </div>
            </div>
            <v-divider
              :thickness="2"
              class="border-opacity-50 my-3"
              style="width: 1300px"
              color="info"
            ></v-divider>

            <v-card rounded="0" style="width: 1300px" variant="flat" class="study-list">
              <div class="study-section">
                <v-row class="pt-10 pl-10" v-for="study in studyList" :key="study.remindQuizId">
                  <v-card
                    @click="GoQuizDetail(study.remindQuizId)"
                    width="950"
                    height="72"
                    variant="tonal"
                    class="d-flex items-center justify-between px-6"
                    color=""
                  >
                    <div class="truncate-text">
                      <span>{{ study.topic }}</span>
                    </div>
                    <div class="d-flex items-center">
                      <div>{{ study.studyName }}</div>
                      <v-divider
                        :thickness="2"
                        class="border-opacity-100 mx-3"
                        style="height: 50px"
                        vertical
                        color="info"
                      ></v-divider>
                      <div>미팅: {{ study.studyAt }}</div>
                    </div>
                  </v-card>
                  <div class="date d-flex flex-column ml-5 justify-center">
                    <span>공개일: {{ study.openAt }}</span>
                    <span>최종 수정 일시: {{ study.lastModifiedAt }}</span>
                  </div>
                </v-row>
              </div>
            </v-card>
          </div>

          <!-- 선택한 퀴즈  -->
          <div v-else class="quiz-detail">
            <div class="d-flex justify-between">
              <div class="title d-flex flex-column">
                <span class="text-2xl ml-5 font-bold">
                  <span class="text-2xl font-normal" @click="TogglePage">< </span
                  ><span class="tossface text-3xl">📌 </span
                  ><span class="point-color font-bold">{{ remindQuiz.topic }}</span
                  >에 대한 리마인드 퀴즈</span
                >
                <p class="text-base ml-5 mt-2 italic text-gray-500">
                  <span
                    >{{ remindQuiz.openAt }} 에 공개된 {{ remindQuiz.studyName }}의
                    퀴즈입니다.</span
                  >
                </p>
              </div>
            </div>
            <v-divider
              :thickness="2"
              class="border-opacity-50 my-3"
              style="width: 1300px"
              color="info"
            ></v-divider>

            <!-- <div class="keyword-section">
              <div class="d-flex py-6 pl-12 align-center">
                <h2>키워드</h2>
                <v-btn @click="regenKeyword()" icon="mdi-refresh" variant="text"></v-btn>
                <v-btn @click="editKeyWord()" icon="mdi-pencil-outline" variant="text"></v-btn>
              </div>
              <div class="d-flex justify-space-around">
                <v-chip
                  class="keyword justify-center"
                  v-for="keyword in keyWordList"
                  :key="keyword.id"
                  @click="searchOnGoogle(keyword.keyword)"
                >
                  <p class="text-center">#{{ keyword.keyword }}</p>
                </v-chip>
              </div>
            </div> -->

            <div class="quiz-section mt-16 text-2xl">
              <p>{{ remindQuiz.content }}</p>
            </div>
          </div>
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
import Swal from 'sweetalert2'
import { useLoadStore } from '@/stores/load'

const loadStore = useLoadStore()
const studyStore = useStudyStore()
const route = useRoute()
const router = useRouter()
const studyId = route.params.id

function GoSetting() {
  router.push({ name: 'studySetting', params: { id: studyId } })
}
function GoHome() {
  router.push({ name: 'study', params: { id: studyId } })
}
function GoSummary() {
  router.push({ name: 'studySummary', params: { id: studyId } })
}

const isList = ref(true)

const keyWordList = ref([
  { id: 1, keyword: '네트워크' },
  { id: 2, keyword: 'OSI 7계층' },
  { id: 3, keyword: 'IP' },
  { id: 4, keyword: 'TCP' }
])
const remindQuiz = ref({})

function searchOnGoogle(keyword) {
  // 검색어에 대한 구글 검색 URL을 생성합니다.
  const googleSearchURL = `https://www.google.com/search?q=${encodeURIComponent(keyword)}`

  // 새 창으로 구글 검색을 엽니다.
  window.open(googleSearchURL, '_blank')
}

const quizSubject = ref('')
const selectedStudyId = ref()

const studyList = ref([])

const LoadQuizList = () => {
  loadStore.isLoading = true
  instance
    .get(`api/studies/${studyId}/remind-quiz`)
    .then((res) => {
      console.log(res.data.message)
      if (res.data.status == 200) {
        loadStore.isLoading = false
        studyList.value = res.data.data
      }
      loadStore.isLoading = false
    })
    .catch((err) => {
      console.log(err)
      loadStore.isLoading = false
    })
}

async function GoQuizDetail(id) {
  try {
    const keywordResponse = await instance.get(`api/study/${id}/keywords`)
    const remindQuizResponse = await instance.get(`api/studies/${studyId}/remind-quiz/${id}`)
    console.log(keywordResponse)
    console.log(remindQuizResponse)
    // keyWordList.value = keywordResponse.data
    // remindQuizList.value = remindQuizResponse.data

    const matchedStudy = studyList.value.find((item) => item.remindQuizId === id)
    // if (matchedStudy && (new Date(matchedStudy.openAt) < new Date() || matchedStudy.opened))
    // if (matchedStudy && (new Date(matchedStudy.openAt) < new Date() || matchedStudy.opened)) {
    remindQuiz.value = remindQuizResponse.data.data
    isList.value = !isList.value
    // } else {
    //   Swal.fire({
    //     title: 'Error!',
    //     text: 'Do you want to continue',
    //     icon: 'error',
    //     confirmButtonText: 'Cool'
    //   })
    // }
  } catch (error) {
    console.log('Error fetching data:', error)
    isList.value = !isList.value
    const matchedStudy = studyList.value.find((item) => item.id === id)
    quizSubject.value = matchedStudy.subject
    selectedStudyId.value = id
  }
}

function TogglePage() {
  isList.value = !isList.value
}

const isEditKeyWord = ref(false)

function ToggleKeyword() {
  isEditKeyWord.value = !isEditKeyWord.value
}

async function editKeyWord() {
  try {
    // 키워드 수정
  } catch (error) {}
}

async function regenKeyword() {
  try {
    // 키워드 재요청
  } catch (error) {}
}

function LoadStudyData() {
  instance.get(`api/studies/${studyId}/settings`).then((res) => {
    const data = res.data.data
    if (res.data.status == 200) {
      console.log(data)
      studyStore.studyTitle = data.title
      studyStore.studyDescription = data.description
      studyStore.studyBackgroundImage = data.backgroundImage
      studyStore.studyCategory = data.category
      studyStore.studyMembers = data.members
      studyStore.studyType = data.type
    }
  })
}

onMounted(() => {
  LoadStudyData()
  LoadQuizList()
})
</script>

<style scoped>
.truncate-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 320px;
}

.v-card {
  border-radius: 30px;
}

.study-list {
  width: 1130px;
  height: 700px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
}

.keyword-section {
  width: 1300px;
  height: 200px;
}
.keyword {
  width: 262px;
  height: 65px;
  font-size: 28px;
}
.quiz-section {
  width: 1300px;
  height: 330px;
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
