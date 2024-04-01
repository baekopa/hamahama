<template>
  <v-container>
    <div v-if="isList" class="remind-quiz">
      <span class="title text-h6">리마인드 퀴즈</span>

      <v-card rounded="0" elevation="3" class="study-list">
        <div class="study-section">
          <v-row class="pt-10 pl-10" v-for="study in studyList" :key="study.id">
            <v-card
              @click="GoQuizDetail(study)"
              elevation="4"
              width="810"
              height="70"
              variant="outlined"
              class="d-flex"
            >
              <div class="quiz-data d-flex align-center">
                <span class="truncate-text">{{ study.topic }}</span>
                <span>{{ study.studyName }}</span>
                <span>미팅 일시 : {{ study.studyAt }}</span>
              </div>
            </v-card>
            <div class="date d-flex flex-column">
              <span>공개일: {{ study.openAt }}</span>
              <span>최종 수정 일시:{{ study.lastModifiedAt }}</span>
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
import instance from '@/api'
import { onMounted } from 'vue'

const isList = ref(true)

const studyList = ref([
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  },
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  },
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  },
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  },
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  },
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  },
  {
    remindQuizId: 1,
    topic: '스터디 주제는 어쩌구저쩌구구절절교회교회성당성당1',
    studyName: '스터디 명',
    studyAt: '2024.03.09',
    openAt: '2024.03.11',
    lastModifiedAt: '2024.03.22',
    opened: true
  }
])
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

function GetQuizList() {
  instance
    .get('api/members/me/remind-quiz')
    .then((res) => {
      if (res.data.status === 200) {
        studyList.value = res.data.data
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

function searchOnGoogle(keyword) {
  // 검색어에 대한 구글 검색 URL을 생성합니다.
  const googleSearchURL = `https://www.google.com/search?q=${encodeURIComponent(keyword)}`

  // 새 창으로 구글 검색을 엽니다.
  window.open(googleSearchURL, '_blank')
}

const quizSubject = ref('')
const selectedStudyId = ref()

function getCurrentTime() {
  return new Date()
}

// openAt 속성의 값을 분석하는 함수
function parseOpenAt(openAt) {
  return new Date(openAt)
}

// openAt과 현재 시간을 비교하는 함수
function isOpen(openAt) {
  const currentTime = getCurrentTime()
  const openTime = parseOpenAt(openAt)

  // openAt이 현재 시간보다 이후인지 비교합니다.
  return openTime > currentTime
}

async function GoQuizDetail(study) {
  if (study.opened === true || isOpen(study.openAt)) {
    try {
      const keywordResponse = await instance.get(`api/study/${id}/keywords`)
      const remindQuizResponse = await instance.get(
        `api/studies/${studyId}/remind-quiz/${study.remindQuizId}`
      )
      console.log(keywordResponse)
      console.log(remindQuizResponse)
      // keyWordList.value = keywordResponse.data
      // remindQuizList.value = remindQuizResponse.data
      isList.value = !isList.value
      const matchedStudy = studyList.value.find((item) => item.id === id)
      quizSubject.value = matchedStudy.subject
      selectedStudyId.value = id.value
    } catch (error) {
      console.log('Error fetching data:', error)
      isList.value = !isList.value
      const matchedStudy = studyList.value.find((item) => item.id === id)
      quizSubject.value = matchedStudy.subject
      selectedStudyId.value = id
    }
  } else {
    console.log('hi')
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

onMounted(() => {
  GetQuizList()
})
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
