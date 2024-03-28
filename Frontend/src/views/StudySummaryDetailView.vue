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
          <v-list-item
            @click="GoSummary()"
            prepend-icon="mdi-forum"
            title="요약"
            value="summary"
          ></v-list-item>
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
          <p class="text-xl ml-5 mt-2 italic text-gray-500">
            <span>{{ summaryData.title }}</span>
          </p>
        </div>

        <!-- 버튼 아래영역을 v-if 로 컨텐츠 분리 -->
        <div class="buttons">
          <v-btn-toggle
            class="bg-grey-lighten-4"
            text-color="white"
            v-model="toggle"
            variant="outlined"
            divided
            mandatory
            color="primary"
          >
            <v-btn class="rounded-t-lg" value="요약">요약</v-btn>
            <v-btn class="rounded-t-lg" value="키워드">키워드</v-btn>
            <v-btn class="rounded-t-lg" value="전문">전문</v-btn>
            <v-btn class="rounded-t-lg" value="노트요약">노트요약</v-btn>
          </v-btn-toggle>
        </div>
        <div class="content">
          <div v-if="toggle == '요약'">
            <div v-if="!isEdit" class="summary-section">
              <div class="d-flex align-center">
                <p>요약</p>
                <v-btn @click="RegenSummary()" icon="mdi-refresh" variant="text"></v-btn>
                <v-btn @click="isEdit = !isEdit" icon="mdi-pencil-outline" variant="text"></v-btn>
              </div>
              <p>{{ summaryData.summary }}</p>
              <div class="mt-10">
                <p>참여자</p>
                <div class="d-flex">
                  <div class="pa-3" v-for="member in summaryData.members">{{ member }}</div>
                </div>
              </div>
            </div>
            <!-- 요약 수정 -->
            <div v-else>
              <v-btn @click="EditSummary" prepend-icon="$vuetify">수정완료</v-btn>
              <v-textarea
                class="justify-center flex-wrap mx-auto px-4"
                style="font-family: 'Arial', sans-serif; line-height: 2; background-color: #f7f7f7"
                v-model="editedSummary"
                label="수정"
                outlined
                no-resize
                row-height="2"
                rows="20"
              ></v-textarea>
            </div>
          </div>

          <div v-else-if="toggle == '키워드'">
            <div>
              <p>키워드</p>
              <div class="keywords d-flex">
                <div class="pa-3" v-for="keyword in summaryData.keywords">#{{ keyword }}</div>
              </div>
            </div>
          </div>
          <div v-else-if="toggle == '전문'">
            <div>
              <p>{{ summaryData.original }}</p>
            </div>
          </div>
          <div v-else-if="toggle == '노트요약'">
            <div>
              <p>여긴 뭐가 들어가는데?</p>
            </div>
          </div>
        </div>
      </v-main>
    </v-layout>
  </v-card>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import instance from '@/api/index'
import { useStudyStore } from '@/stores/study'

const studyStore = useStudyStore()
const router = useRouter()
const route = useRoute()
const summaryId = route.params.id
const studyId = route.params.studyId
const toggle = ref('요약')
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

function LoadSummaryData() {
  instance
    .get(`api/study/summary/${summaryId}`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

function RegenSummary() {
  instance
    .get(`api/study/summary/regen/${summaryId}`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

function EditSummary() {
  instance
    .put(`api/study/summary/${summaryId}`, {
      content: editedSummary
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
