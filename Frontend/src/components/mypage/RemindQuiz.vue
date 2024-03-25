<template>
  <v-container>
    <div v-if="isList" class="remind-quiz">
      <span class="title text-h6">리마인드 퀴즈</span>

      <v-card rounded="0" elevation="3" class="study-list">
        <div class="study-section">
          <v-row class="pt-10 pl-10" v-for="study in studyList" :key="study.id">
            <v-card
              @click="GoQuizDetail(study.id)"
              elevation="4"
              width="810"
              height="70"
              variant="outlined"
              class="d-flex"
            >
              <div class="quiz-data d-flex align-center">
                <span class="truncate-text">{{ study.subject }}</span>
                <span>{{ study.studyName }}</span>
                <span>미팅 일시 : {{ study.meetingDate }}</span>
              </div>
            </v-card>
            <div class="date d-flex flex-column">
              <span>공개일: {{ study.openDate }}</span>
              <span>최종 수정 일시:{{ study.lastUpdate }}</span>
            </div>
          </v-row>
        </div>
      </v-card>
    </div>

    <div v-else class="quiz-detail">
      <v-btn @click="TogglePage">돌아가기</v-btn>
      <h1 class="title my-4">{{ quizSubject }}</h1>
      <div class="keyword-section">
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
      </div>

      <div class="quiz-section mt-16">
        <h3>리마인드 퀴즈</h3>
        <v-list>
          <v-list-item v-for="(quiz, index) in remindQuizList" :key="quiz.id"
            ><p>Q{{ index + 1 }}.{{ quiz.quiz }}</p>
          </v-list-item>
        </v-list>
      </div>
    </div>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const isList = ref(true)

const keyWordList = ref([
  { id: 1, keyword: '네트워크' },
  { id: 2, keyword: 'OSI 7계층' },
  { id: 3, keyword: 'IP' },
  { id: 4, keyword: 'TCP' }
])
const remindQuizList = ref([
  { id: 1, quiz: '첫번째 질문!' },
  { id: 2, quiz: '두번째 질문!' },
  { id: 3, quiz: '세번째 질문!' },
  { id: 4, quiz: '네번째 질문!' },
  { id: 5, quiz: '다섯번째 질문!' }
])

function searchOnGoogle(keyword) {
  // 검색어에 대한 구글 검색 URL을 생성합니다.
  const googleSearchURL = `https://www.google.com/search?q=${encodeURIComponent(keyword)}`

  // 새 창으로 구글 검색을 엽니다.
  window.open(googleSearchURL, '_blank')
}

const quizSubject = ref('')
const selectedStudyId = ref()

async function GoQuizDetail(id) {
  try {
    const keywordResponse = await axios.get(`/api/study/${id}/keywords`)
    const remindQuizResponse = await axios.get(`/api/study/${id}/remindQuizzes`)
    // keyWordList.value = keywordResponse.data
    // remindQuizList.value = remindQuizResponse.data
    isList.value = !isList.value
    const matchedStudy = studyList.value.find((item) => item.id === id)
    quizSubject.value = matchedStudy.subject
    selectedStudyId = id.value
  } catch (error) {
    console.error('Error fetching data:', error)
    isList.value = !isList.value
    const matchedStudy = studyList.value.find((item) => item.id === id)
    quizSubject.value = matchedStudy.subject
    selectedStudyId = id.value
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

const studyList = ref([
  {
    id: 1,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  },
  {
    id: 2,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당2',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  },
  {
    id: 3,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당3',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  },
  {
    id: 4,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  },
  {
    id: 5,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  },
  {
    id: 6,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  },
  {
    id: 7,
    subject: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당',
    studyName: '스터디 명',
    meetingDate: '2024.03.09',
    openDate: '2024.03.11',
    lastUpdate: '2024.03.22'
  }
])
</script>

<style scoped>
.truncate-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 310px;
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
  border: 1px solid #d9d9d9;
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
  border: 1px solid #d9d9d9;
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
