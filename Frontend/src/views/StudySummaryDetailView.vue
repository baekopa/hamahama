<template>
  <v-container>
    <v-layout style="max-height: 800px">
      <v-navigation-drawer style="width: 323px; height: 800px">
        <p class="text-3xl text-center mt-10 point-font text-stone-900">같이하마</p>
        <v-list lines="two" density="compact" nav>
          <v-list-item three-line>
            <v-list-item-content class="align-self-center">
              <v-list-item-title class="ml-14 mt-10"><div class="text-2xl font-bold">하마하마스터디</div></v-list-item-title>
              <v-list-item-subtitle class="ml-14 mt-1"
                ><div class="text-base">CS면접</div></v-list-item-subtitle
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
            >스터디 홈</v-list-item>
            <v-list-item
              @click="GoSummary()"
              prepend-icon="mdi-forum"
             value="summary"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
            >요약</v-list-item>
            <v-list-item
              @click="GoQuiz()"
              prepend-icon="mdi-help-box"
              value="quiz"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
            >리마인드 퀴즈</v-list-item>
            <v-list-item
              @click="GoSetting()"
              prepend-icon="mdi-account-key"
              value="setting"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
            >스터디 관리</v-list-item>
          </div>
        </v-list>
      </v-navigation-drawer>
      <v-divider style="height: 900px" class="mr-10" vertical></v-divider>

      <v-main class="ml-10 mt-5" style="min-height: 800px">
        <v-container>
          <div class="d-flex justify-between">
            <div class="title d-flex flex-column">
              <span class="text-2xl ml-5 font-bold">
                <span class="tossface text-3xl">🗂 </span><span class="point-color font-bold">{{"CS 면접 스터디 3회차 - 네트워크와 OSI 7계층"}}</span> 미팅 정리본</span>
                <p class="text-base ml-5 mt-2 italic text-gray-500"><span>{{"2024-03-30 13:00"}}</span></p>
                <div class="d-flex ml-5 mt-4">참여 - <p class="ml-1" v-for="member in summaryData.members"> {{ member }} </p></div>
            </div>
            <div class="mr-40 mt-14">
              <img @click="" src="@/assets/image/note/download.svg" alt="pencil" />
            </div>
          </div>
          <v-divider :thickness="2" class="border-opacity-50 my-3" style="width:1300px" color="info"></v-divider>

        <!-- 버튼 아래영역을 v-if 로 컨텐츠 분리 -->
        <div class="mt-10 ml-5" style="width:1300px">
          <v-btn-toggle
            v-model="toggle"
            variant="tonal"
            divided
            mandatory
            color="#3FB1FA"
          >
          <div class="rounded-t-2xl">
            <v-btn :variant="toggle=='요약'? 'elevated' : 'tonal'" value="요약" width="125" height="50"><span class="text-lg point-font">요약</span></v-btn>
          </div>
          <div class="rounded-t-2xl">
            <v-btn :variant="toggle=='키워드'? 'elevated' : 'tonal'" value="키워드" width="125" height="50"><span class="text-lg point-font">키워드</span></v-btn>
          </div>
          <div class="rounded-t-2xl">
            <v-btn :variant="toggle=='전문'? 'elevated' : 'tonal'" value="전문" width="125" height="50"><span class="text-lg point-font">전문</span></v-btn>
          </div>
          <div class="rounded-t-2xl">
            <v-btn :variant="toggle=='제출된노트'? 'elevated' : 'tonal'" value="제출된노트" width="125" height="50"><span class="text-lg point-font">제출된 노트</span></v-btn>
          </div>
          </v-btn-toggle>
        </div>
        <div class="content border ml-5 px-7 py-5 rounded-b-xl ">
          <div v-if="toggle == '요약'">
            <div v-if="!isEdit" class="summary-section">
              <div class="d-flex align-center h-10">
                <p class="text-lg font-bold mr-4">요약 내용</p>
                <v-btn @click="RegenSummary()" icon="mdi-refresh" variant="text"></v-btn>
                <v-btn @click="isEdit = !isEdit" icon="mdi-pencil-outline" variant="text"></v-btn>
              </div>
              <div class="mt-5">
                <p>{{ summaryData.summary }}</p>
              </div>
            </div>
            <!-- 요약 수정 -->
            <div v-else>
              <div class="d-flex align-center h-10 justify-between">
                <p class="text-lg font-bold mr-4">요약 내용</p> 
                <v-btn @click="EditSummary" size="large" class="save" variant="tonal"  color="#3fb1fa" rounded="xl">
                   수정완료
                </v-btn>             
              </div>
              <div class="mt-5">
                <textarea style="width:1190px; height:350px" v-model="editedSummary" variant="plain" placeholder="수정할 내용을 작성해주세요. ( •̀ ω •́ )✧" class="modify-content mt-5" rows="20"></textarea>
              </div>
            </div>
          </div>

          <div v-else-if="toggle == '키워드'">
            <div class="d-flex align-center h-10">
              <p class="text-lg font-bold mr-4">키워드</p>
              <v-btn @click="" icon="mdi-refresh" variant="text"></v-btn>
            </div>
            <div class="keywords d-flex mt-5">
              <v-chip-group>
                <v-chip class="mr-5" size="x-large" v-for="keyword in summaryData.keywords">#{{ keyword }}</v-chip>
              </v-chip-group>
            </div>
          </div>
          <div v-else-if="toggle == '전문'">
            <div>
              <div>
                <div class="d-flex align-center h-10">
                  <p class="text-lg font-bold mr-4">전문 내용</p>
                  <v-btn @click="" icon="mdi-refresh" variant="text"></v-btn>
                  <v-btn @click="" icon="mdi-pencil-outline" variant="text"></v-btn>
                </div>
              </div>
              <div class="mt-5">
                <p>{{ summaryData.original }}</p>
              </div>
            </div>
          </div>
          <div v-else-if="toggle == '제출된노트'">
            <div class="d-flex align-center h-10 text-lg font-bold">
              <p class="text-lg font-bold mr-4">제출된 노트</p>
                <v-chip-group v-model="noteToggle" variant="text" mandatory>
                  <v-chip class="h-10" @click="console.log('dd')" value="-1">전체요약</v-chip>
                  <v-chip class="h-10" @click="console.log('dd')" v-for="(note, index) in submittedNotes.submittedNotes" :key="note.id" :value="index">{{note.writerName}}</v-chip>
                </v-chip-group>
              </div>
            <div class="d-flex mt-5">
              <div v-if="noteToggle == -1">
                {{ submittedNotes.noteSummary }}
              </div>
              <div v-else>
                <p class="font-bold">노트</p>
                <div>{{ submittedNotes.submittedNotes[noteToggle].originText }}</div>
                <p class="font-bold mt-5">요약</p>
                <div>{{ submittedNotes.submittedNotes[noteToggle].summaryText }}</div>
              </div>
            </div>

          </div>
        </div>
      </v-container>
      </v-main>
    </v-layout>
  </v-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import instance from '@/api/index'
import { useStudyStore } from '@/stores/study'

const studyStore = useStudyStore()
const router = useRouter()
const route = useRoute()
const meetingId = route.params.id
const studyId = route.params.studyId
const toggle = ref('요약')
const noteToggle = ref(-1)
const isEdit = ref(false)

const summaryData = ref({
  id: 1,
  title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀',
  summary:
    '스터디 결과 요약문은 다음과 같다.스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다스터디 결과 요약문은 다음과 같다.',
  members: ['김수민', '이수민', '여아정'],
  keywords: ['네트워크', 'OSI 7계층', 'IP', 'TCP'],
  original:
    '울 시내버스 노동조합이 12년 만에 파업에 들어간 28일 출근길 시민들이 적잖은 불편을 겪었다.이른 아침부터 비까지 내리면서 불편이 가중됐다. 새벽까지 이어진 협상이 끝내 결렬되면서 파업에 돌입한 터라 파업 소식을 모르고 출근길에 나섰다가 당황한 시민들도 있었다.이날 오전 8시께 서울 마포구 마포구청역 인근 버스정류장에서 버스를 기다리던 황모(56)씨는 버스 파업으로 회사에 지각할까 봐 걱정스러워했다.마포구청역 인근에서 강서구 화곡동으로 매일 출근한다는 황씨는 "지하철을 타면 세 번은 갈아타야 하고 시간도 훨씬 오래 걸리는데 큰일"이라며 한숨을 쉬었다.기자에게 버스 파업 소식을 처음 들었다며 당황하는 시민도 여럿 보였다.강남 고속터미널 인근으로 출근하는 조민상(37)씨는 "뉴스를 잘 안 봐 버스가 파업하는지 몰랐다"며 "정류장 전광판에 운행 예정 버스가 없어서 어리둥절하던 찰나였다"고 말했다.지하철 4호선 혜화역 인근 정류장에서 만난 한주현(31)씨는 "파업한다는 이야기를 못 들었다"며 "버스를 30분 넘게 기다려야 된다고 하니 어떻게 해야 될지 모르겠다. 출근해야 되는데 마음이 급하다"며 급히 발걸음을 옮겼다.'
})

const editedSummary = ref('')
watch(summaryData.summary, (newValue) => {
  editedSummary.value = newValue
})

const submittedNotes = ref({
  id: 1,
  topic: '스터디 주제',
  studyAt: '2024-03-22 14:00',
  submittedNotes: [
    {
      id: 1,
      originText: "김이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. ",
      summaryText: "이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. ",
      writerName: "김수민",
      writerImage: "https://isplus.com/data/isp/image/2020/08/20/isp95ee4006-d53b-4d8c-8205-cb7d81752b07.jpg"
    },
    {
      id: 2,
      originText: "이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. ",
      summaryText: "이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. ",
      writerName: "이수민",
      writerImage: "https://isplus.com/data/isp/image/2020/08/20/isp95ee4006-d53b-4d8c-8205-cb7d81752b07.jpg"
    },
    {
      id: 3,
      originText: "여 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. 이 노트의 내용은 다음과 같다. ",
      summaryText: "이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. 이 노트의 요약 내용. ",
      writerName: "여아정",
      writerImage: "https://isplus.com/data/isp/image/2020/08/20/isp95ee4006-d53b-4d8c-8205-cb7d81752b07.jpg"
    },
  ],
  noteSummary: "전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. "
})

function GoSetting() {
  router.push({ name: 'studySetting', params: { id: studyId } })
}
function GoHome() {
  router.push({ name: 'study', params: { id: studyId } })
}
function GoQuiz() {
  router.push({ name: 'studyQuiz', params: { id: studyId } })
}
function GoSummary() {
  router.push({ name: 'studySummary', params: { id: studyId } })
}

// 미팅 요약 조회
function LoadSummaryData() {
  instance
    .get(`api/studies/${studyId}/meetings/${meetingId}/summary`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

// 미팅 요약 재생성
function RegenSummary() {
  instance
    .put(`api/studies/${studyId}/meetings/${meetingId}/summary`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

function EditSummary() {
  instance
    .put(`api/studies/${studyId}/meetings/${meetingId}/summary-update`, {
      summaryText: editedSummary
    })

    .then((res) => {
      console.log(res)

      isEdit.value = !isEdit.value
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(LoadSummaryData)
</script>

<style scoped>
.truncate-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: mormal;
  max-height: 200px;
  /* Adjust max-width if needed */
}

.content {
  width: 1250px;
  height: 500px;
  overflow-y: auto;
}

.modify-content {
  font-size: large;
  outline: none;
}

.shared-study-image {
  height: 45px;
  width: 45px;
  border-radius: 50%;
  margin-left: 10px;
  object-fit: cover;
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
