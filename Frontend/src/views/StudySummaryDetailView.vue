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
          <div class="d-flex justify-between">
            <div class="title d-flex flex-column">
              <span class="text-2xl ml-5 font-bold">
                <span class="tossface text-3xl">🗂 </span
                ><span class="point-color font-bold">{{ studyStore.meetingTopic }}</span> 미팅
                정리본</span
              >
              <p class="text-base ml-5 mt-2 italic text-gray-500">
                <span>{{ studyStore.studyAt }}</span>
              </p>
              <div class="d-flex ml-5 mt-4">
                참여 -
                <p class="ml-1" v-for="member in studyStore.meetingMembers">
                  {{ member.name }}
                </p>
              </div>
            </div>
            <div class="mr-40 mt-14">
              <div class="mr-5 d-flex items-center">
                <v-chip @click="CreateRemindQuiz()" class="mr-4" variant="elevated" color="#3FB1FA"
                  >리마인드 퀴즈 생성</v-chip
                >
                <!-- <button>
                  <img
                    @click="DownloadAudio()"
                    src="@/assets/image/note/download.svg"
                    alt="download"
                  />
                </button> -->
              </div>
            </div>
          </div>
          <v-divider
            :thickness="2"
            class="border-opacity-50 my-3"
            style="width: 1300px"
            color="info"
          ></v-divider>

          <!-- 버튼 아래영역을 v-if 로 컨텐츠 분리 -->
          <div class="mt-10 ml-5" style="width: 1300px">
            <v-btn-toggle v-model="toggle" variant="tonal" divided mandatory color="#3FB1FA">
              <div class="rounded-t-2xl">
                <v-btn
                  :variant="toggle == '전문' ? 'elevated' : 'tonal'"
                  value="전문"
                  width="125"
                  height="50"
                  ><span class="text-lg point-font">전문</span></v-btn
                >
              </div>
              <div class="rounded-t-2xl">
                <v-btn
                  :variant="toggle == '요약' ? 'elevated' : 'tonal'"
                  value="요약"
                  width="125"
                  height="50"
                  ><span class="text-lg point-font">요약</span></v-btn
                >
              </div>
              <div class="rounded-t-2xl">
                <v-btn
                  :variant="toggle == '키워드' ? 'elevated' : 'tonal'"
                  value="키워드"
                  width="125"
                  height="50"
                  ><span class="text-lg point-font">키워드</span></v-btn
                >
              </div>
              <div class="rounded-t-2xl">
                <v-btn
                  :variant="toggle == '제출된노트' ? 'elevated' : 'tonal'"
                  value="제출된노트"
                  width="125"
                  height="50"
                  ><span class="text-lg point-font">제출된 노트</span></v-btn
                >
              </div>
            </v-btn-toggle>
          </div>
          <div class="content border ml-5 px-7 py-5 rounded-b-xl">
            <div v-if="toggle == '요약'">
              <div v-if="!isEdit" class="summary-section">
                <div class="d-flex align-center h-10">
                  <p class="text-lg font-bold mr-4">요약 내용</p>
                  <v-chip
                    v-if="!isSummaryExist"
                    @click="CreateMeetingSummary()"
                    class="mr-4"
                    variant="elevated"
                    color="#3FB1FA"
                    >미팅 전문 요약 생성</v-chip
                  >
                  <div v-else>
                    <v-btn @click="RegenSummary()" icon="mdi-refresh" variant="text"></v-btn>
                    <v-btn
                      @click="isEdit = !isEdit"
                      icon="mdi-pencil-outline"
                      variant="text"
                    ></v-btn>
                  </div>
                </div>
                <div class="mt-5">
                  <p v-html="addLineBreaks(meetingContents.summaryContent)"></p>
                  <!-- <p>{{ meetingContents.summaryContent }}</p> -->
                </div>
              </div>
              <!-- 요약 수정 -->
              <div v-else>
                <div class="d-flex align-center h-10 justify-between">
                  <p class="text-lg font-bold mr-4">요약 내용</p>
                  <v-btn
                    @click="EditSummary()"
                    size="large"
                    class="save"
                    variant="tonal"
                    color="#3fb1fa"
                    rounded="xl"
                  >
                    수정완료
                  </v-btn>
                </div>
                <div class="mt-5">
                  <textarea
                    style="width: 1190px; height: 350px"
                    v-model="editedSummary"
                    variant="plain"
                    placeholder="수정할 내용을 작성해주세요. ( •̀ ω •́ )✧"
                    class="modify-content mt-5"
                    rows="20"
                  ></textarea>
                </div>
              </div>
            </div>

            <div v-else-if="toggle == '키워드'">
              <div class="d-flex align-center h-10">
                <p class="text-lg font-bold mr-4">키워드</p>
                <v-chip
                  v-if="!isKeywordExist"
                  @click="CreateKeyword"
                  class="mr-4"
                  variant="elevated"
                  color="#3FB1FA"
                  >키워드 생성</v-chip
                >
                <v-btn v-else @click="CreateKeyword" icon="mdi-refresh" variant="text"></v-btn>
              </div>
              <div class="keywords d-flex mt-5">
                <v-chip-group>
                  <v-chip
                    @click="SearchKeyword(keyword.keyword)"
                    class="mr-5"
                    size="x-large"
                    v-for="keyword in meetingContents.keyword"
                    :key="keyword.keywordId"
                    >#{{ keyword.keyword }}</v-chip
                  >
                </v-chip-group>
                <p v-if="!isKeywordExist">키워드를 생성해 보세요 !</p>
              </div>
            </div>
            <div v-else-if="toggle == '전문'">
              <div v-if="!isEditScript">
                <div>
                  <div class="d-flex align-center h-10">
                    <p class="text-lg font-bold mr-4">전문 내용</p>
                    <v-btn @click="" icon="mdi-refresh" variant="text"></v-btn>
                    <v-btn
                      @click="isEditScript = true"
                      icon="mdi-pencil-outline"
                      variant="text"
                    ></v-btn>
                  </div>
                </div>
                <div class="mt-5">
                  <p v-html="addLineBreaks(scriptContent)"></p>
                </div>
              </div>

              <div>
                <div v-if="isEditScript">
                  <div class="d-flex align-center h-10 justify-between">
                    <p class="text-lg font-bold mr-4">요약 내용</p>
                    <v-btn
                      @click="EditScript()"
                      size="large"
                      class="save"
                      variant="tonal"
                      color="#3fb1fa"
                      rounded="xl"
                    >
                      수정완료
                    </v-btn>
                  </div>
                  <div class="mt-5">
                    <textarea
                      style="width: 1190px; height: 350px"
                      v-model="editedScript"
                      variant="plain"
                      placeholder="수정할 내용을 작성해주세요. ( •̀ ω •́ )✧"
                      class="modify-content mt-5"
                      rows="20"
                    ></textarea>
                  </div>
                </div>
              </div>
            </div>
            <div v-else-if="toggle == '제출된노트'">
              <div class="d-flex align-center h-10 text-lg font-bold">
                <p class="text-lg font-bold mr-4">제출된 노트</p>

                <v-chip-group v-model="noteToggle" variant="text" mandatory>
                  <v-chip class="h-10">전체</v-chip>
                  <v-chip
                    class="h-10"
                    @click="noteToggle = index + 1"
                    v-for="(note, index) in meetingContents.submittedNoteSummary.submittedNotes"
                    :key="note.id"
                    :value="index"
                    >{{ note.writerName }}</v-chip
                  >
                </v-chip-group>
              </div>
              <div v-if="noteToggle == 0" class="d-flex mt-5">
                <div>
                  <p class="font-bold">미팅에 제출된 노트 전체 요약</p>
                  <div class="mt-3">
                    <p
                      v-html="addLineBreaks(meetingContents.submittedNoteSummary.entireSummary)"
                    ></p>
                    <!-- {{ meetingContents.submittedNoteSummary.entireSummary }} -->
                  </div>
                </div>
              </div>
              <div v-else class="d-flex mt-5">
                <div>
                  <p class="font-bold">노트</p>
                  <div>
                    <p
                      v-html="
                        addLineBreaks(
                          meetingContents.submittedNoteSummary.submittedNotes[noteToggle - 1]
                            .originText
                        )
                      "
                    ></p>
                    <!-- {{
                      meetingContents.submittedNoteSummary.submittedNotes[noteToggle - 1].originText
                    }} -->
                  </div>
                  <p class="font-bold mt-5">요약</p>
                  <div>
                    <p
                      v-html="
                        addLineBreaks(
                          meetingContents.submittedNoteSummary.submittedNotes[noteToggle - 1]
                            .summaryText
                        )
                      "
                    ></p>
                  </div>
                  <div
                    v-if="
                      meetingContents.submittedNoteSummary.submittedNotes[noteToggle - 1]
                        .writerName == authStore.userName
                    "
                  >
                    <div v-if="isDifferenceExist">
                      <p class="font-bold mt-5">미팅 내용과 내 노트와 차이점</p>
                      {{ diffrence }}
                    </div>
                    <div v-else>
                      <p class="font-bold mt-5">미팅 내용과 내 노트와 차이점을 생성해 보세요</p>
                      <v-btn v-if="!isDifferenceExist" @click="CreateDifference()">
                        <p>요약 차이 생성</p>
                      </v-btn>
                    </div>
                  </div>
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
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import instance from '@/api/index'
import { useStudyStore } from '@/stores/study'
import Swal from 'sweetalert2'
import { useLoadStore } from '@/stores/load'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const loadStore = useLoadStore()
const studyStore = useStudyStore()
const router = useRouter()
const route = useRoute()
const meetingId = route.params.id
const studyId = route.params.studyId
const toggle = ref('전문')
const noteToggle = ref(0)
const isEdit = ref(false)

const isEditScript = ref(false)

const summaryContent = ref('')
const scriptContent = ref('')
const scriptContentId = ref()
const diffrence = ref('')

const isSummaryExist = ref(false)
const isKeywordExist = ref(false)
const isDifferenceExist = ref(false)

const meetingContents = ref({
  meetingId: 0,
  topic: '네트워크와 OSI 7계층',
  scriptContent: 'string',
  summaryContent: '아직 요약을 생성하지 않았어요! 요약 생성을 해보세요',
  keyword: [
    {
      keywordId: 0,
      keyword: '키워드가 생성되지 않았어요 키워드 생성 버튼을 눌러보세요'
    }
  ],
  memberInfoList: [
    {
      memberId: 0,
      name: '백오파',
      profile_image: 'string'
    }
  ],
  submittedNoteSummary: {
    submittedNotes: [
      {
        id: 0,
        originText: '',
        summaryText: '',
        writerId: 1,
        writerName: '',
        writerImage: ''
      }
    ],
    entireSummary: ''
  }
})

// 요약을 수정관련
const editedSummary = computed({
  get: () => meetingContents.value.summaryContent,
  set: (newValue) => {
    meetingContents.value.summaryContent = newValue
  }
})
// 전문 수정관련
const editedScript = ref(scriptContent)
watch(scriptContent, (newValue) => {
  editedScript.value = newValue
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

// 전문 조회
function LoadEntireScript() {
  instance
    .get(`api/studies/${studyId}/meetings/${meetingId}/entire`)
    .then((res) => {
      console.log(res.data.message)
      if (res.data.status == 200) {
        scriptContent.value = res.data.data.scriptContent
        scriptContentId.value = res.data.data.meetingScriptId
      } else {
        Swal.fire({
          title: '잠깐만요!',
          text: '아직 녹음본이 변환되지 않았어요! 잠시만 기다려 주세요',
          icon: 'question',
          confirmButtonColor: '#3085d6',
          confirmButtonText: '돌아기기'
        }).then((result) => {
          if (result.isConfirmed) {
            router.go(-1)
          }
        })
      }
    })
    .catch((err) => {
      if (err.response.data.message === '미팅 스크립트가 존재하지 않습니다') {
        Swal.fire({
          title: '잠깐만요!',
          text: '아직 녹음본이 변환되지 않았어요! 잠시만 기다려 주세요',
          icon: 'question',
          confirmButtonColor: '#3085d6',
          confirmButtonText: '돌아가기'
        }).then((result) => {
          if (result.isConfirmed) {
            router.go(-1)
          }
        })
      }
    })
}

// 키워드 생성
async function CreateKeyword() {
  loadStore.isLoading = true
  instance
    .post(`api/studies/${studyId}/meetings/${meetingId}/keyword`)
    .then((res) => {
      if (res.data.status == 201) {
        meetingContents.value.keyword = res.data.data.keyword
        LoadAll()
        loadStore.isLoading = false
      }
      loadStore.isLoading = false
    })
    .catch((err) => {
      loadStore.isLoading = false
      console.log(err)
    })
}

function SearchKeyword(keyword) {
  const searchUrl = `https://www.google.com/search?q=${encodeURIComponent(keyword)}`
  window.open(searchUrl, '_blank')
}

// 산출물 조회 (주제, 요약, 전문, 키워드 , 참여자는 추가예정)
function LoadAll() {
  loadStore.isLoading = true
  instance
    .get(`api/studies/${studyId}/meetings/${meetingId}/all`)
    .then((res) => {
      if (res.data.status == 200) {
        console.log(res.data.message)
        meetingContents.value = res.data.data
        isSummaryExist.value = true

        if (res.data.data.keyword.length === 0) {
          isKeywordExist.value = false
        } else {
          isKeywordExist.value = true
        }
        loadStore.isLoading = false
      }
      loadStore.isLoading = false
    })
    .catch((err) => {
      loadStore.isLoading = false
    })
}

// 미팅 요약 재생성
function RegenSummary() {
  instance
    .put(`api/studies/${studyId}/meetings/${meetingId}/summary`)
    .then((res) => {
      if (res.data.status === 201) {
        console.log(res.data.message)
        LoadAll()
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

// 전문요약 수정
function EditSummary() {
  instance
    .put(`api/studies/${studyId}/meetings/${meetingId}/summary-update`, {
      summaryText: editedSummary.value
    })

    .then((res) => {
      console.log(res)
      if (res.data.status == 201) {
        summaryContent.value = res.data.data.summaryText
        LoadAll()
      } else {
      }
      isEdit.value = !isEdit.value
    })
    .catch((err) => {
      console.log(err)
    })
}

// 리마인드 퀴즈생성
function CreateRemindQuiz() {
  loadStore.isLoading = true
  instance
    .post(`api/studies/${studyId}/meetings/${meetingId}/remind-quiz`)
    .then((res) => {
      if (res.data.status == 201) {
        loadStore.isLoading = false
      }
      console.log(res.data.message)
    })
    .catch((err) => {
      console.log(err)
    })
}

// 전문 요약 생성
async function CreateMeetingSummary() {
  loadStore.isLoading = true
  await instance
    .post(`api/studies/${studyId}/meetings/${meetingId}/summary`)
    .then((res) => {
      console.log(res.data.message)
      if (res.data.status === 201) {
        LoadAll()
        loadStore.isLoading = false
      } else {
        loadStore.isLoading = false
      }
    })
    .catch((err) => {
      loadStore.isLoading = false
      console.log(err)
    })
}

// 차이점 조회
function LoadDiffrence() {
  instance
    .get(`api/studies/${studyId}/meetings/${meetingId}/difference`)
    .then((res) => {
      const data = res.data.data.difference
      if (data != null) {
        diffrence.value = data
        isDifferenceExist.value = true
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

// 내 노트와 차이점 생성
function CreateDifference() {
  loadStore.isLoading = true
  instance
    .post(`api/studies/${studyId}/meetings/${meetingId}/difference`)
    .then((res) => {
      loadStore.isLoading = false
      LoadDiffrence()
    })
    .catch((err) => {
      loadStore.isLoading = false
      console.log(err)
    })
}

// 전문 수정
function EditScript() {
  instance
    .put(`api/studies/${studyId}/meetings/${meetingId}/entire`, {
      meetingScriptId: scriptContentId.value,
      scriptContent: editedScript.value
    })
    .then((res) => {
      if (res.data.status === 204) {
        Swal.fire({
          title: '전문 수정 성공!',
          icon: 'success',
          confirmButtonColor: '#3085d6',
          confirmButtonText: '확인'
        })

        isEditScript.value = false
      }
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

function addLineBreaks(text) {
  if (text === null || text === undefined) {
    return '' // null 또는 undefined인 경우 빈 문자열 반환
  }
  return text.replace(/\n/g, '<br>')
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

// function DownloadAudio() {
//   instance
//     .get(`api/studies/${studyId}/meetings/${meetingId}/recordfile`)
//     .then((res) => {
//       console.log(res)
//     })
//     .catch((err) => {
//       console.log(err)
//     })
//}

onMounted(() => {
  LoadStudyData()
  LoadAll()
  LoadEntireScript()
  LoadDiffrence()
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
