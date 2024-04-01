<template>
  <v-container>
    <v-layout style="max-height: 857px">
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
        <div v-if="false">영역</div>
      </v-navigation-drawer>
      <v-divider style="height: 900px" class="mr-10" vertical></v-divider>

      <v-main class="ml-10 mt-5" style="min-height: 800px">
        <div class="content ml-4 mt-4">
          <div class="d-flex">
            <div class="title">
              <span class="text-2xl ml-5 font-bold">
                <span class="tossface text-3xl">📖 </span> 다음 미팅</span
              >
              <p class="text-base ml-5 mt-2 italic text-gray-500">
                <span>{{ studyStore.studyTitle }}</span
                >의 다음 미팅 일정입니다.
              </p>
            </div>
          </div>
          <v-divider
            :thickness="2"
            class="border-opacity-50 my-3"
            style="width: 1300px"
            color="info"
          ></v-divider>
          <div class="d-flex">
            <div class="d-flex flex-column">
              <div class="mt-10 ml-5 text-2xl font-semibold">
                <span class="tossface mr-2">💬</span>
                {{ submittedNotes.topic }}
              </div>
              <div class="mt-5 ml-5 text-2xl font-bold">
                <span class="tossface mr-2">📅</span>
                {{ submittedNotes.studyAt }}
              </div>
            </div>
            <div></div>
          </div>
          <div class="mt-10">
            <div>
              <div v-if="!recording">
                <button
                  class="gradient-btn rounded-lg"
                  @click="startRecording"
                  style="width: 1300px; height: 80px"
                >
                  <span class="text-xl point-font"
                    ><v-icon icon="mdi-account-voice" class="mr-4"></v-icon>스터디 시작</span
                  >
                </button>
              </div>
              <div
                v-else
                class="gradient-btn rounded-lg d-flex flex-column items-center justify-center"
                style="width: 1300px; height: 170px"
              >
                <div class="mb-5">
                  <span class="text-2xl font-bold"
                    ><v-icon icon="mdi-waveform"></v-icon> {{ elapsedTime }}</span
                  >
                </div>
                <div class="d-flex">
                  <v-card v-if="recording && !paused" variant="text" hover class="rounded-lg">
                    <button class="rounded-lg p-3" @click="pauseRecording" style="width: 150px">
                      <span class="text-xl point-font">일시정지</span>
                    </button>
                  </v-card>
                  <v-card v-if="recording && paused" variant="text" hover class="rounded-lg">
                    <button class="rounded-lg p-3" @click="resumeRecording" style="width: 150px">
                      <span class="text-xl point-font">재개</span>
                    </button>
                  </v-card>
                  <v-divider
                    :thickness="3"
                    class="border-opacity-75"
                    style="height: 50px"
                    vertical
                  ></v-divider>
                  <v-card v-if="recording" variant="text" hover class="rounded-lg">
                    <button class="rounded-lg py-3" @click="stopRecording" style="width: 150px">
                      <span class="text-xl point-font">중지</span>
                    </button>
                  </v-card>
                </div>
              </div>
            </div>
          </div>

          <div class="d-flex pr-2 mt-20" style="width: 1300px">
            <div class="">
              <div class="d-flex align-center h-10 text-lg font-bold">
                <p class="text-lg font-bold mr-4">제출된 노트</p>
                <v-chip-group v-model="noteToggle" variant="text" mandatory>
                  <v-chip class="h-10" value="-1">전체요약</v-chip>
                  <v-chip
                    class="h-10"
                    v-for="(note, index) in submittedNotes.submittedNotes"
                    :key="note.id"
                    :value="index"
                    >{{ note.writerName }}</v-chip
                  >
                </v-chip-group>
              </div>
              <div class="d-flex mt-5 mb-">
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
        </div>
      </v-main>
    </v-layout>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStudyStore } from '@/stores/study'
import { useAudioStore } from '@/stores/audioStore'
import instance from '@/api'

const studyStore = useStudyStore()
const audioStore = useAudioStore()

const route = useRoute()
const router = useRouter()
const meetingID = ref()
const studyId = route.params.id
const noteToggle = ref(-1)

const submittedNotes = ref({
  noteSummary:
    '전체 노트 요약과 꼬리 질문이 여기에 기록됩니다. 전전체 노트 요약과 꼬리 질문이 여기에 기록됩니다.'
})

const isNextMeetingExist = ref(false)

function GoSetting() {
  router.push({ name: 'studySetting', params: { id: studyId } })
}
function GoSummary() {
  router.push({ name: 'studySummary', params: { id: studyId } })
}
function GoQuiz() {
  router.push({ name: 'studyQuiz', params: { id: studyId } })
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
    }
  })
}

function LoadNextSchedule() {
  instance
    .get(`api/studies/${studyId}`)
    .then((res) => {
      console.log(res.data)
      if (res.data.status == 200 && res.data.data != null) {
        isNextMeetingExist.value = true
        submittedNotes.value = res.data.data
        meetingID.value = res.data.data.id
        console.log(meetingID.value)
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  LoadStudyData()
  LoadNextSchedule()
})

// ------------------------------------ //

// --------------- 녹음 관련 변수와 함수 ------------------ //
const mediaRecorder = ref(null)
const audioChunks = ref([])
// const recordText = ref('');
const startTime = ref(null)
const recording = ref(false)
const elapsedTime = ref('00:00')
const timer = ref(null)
const paused = ref(false) // 일시정지 상태 관리를 위한 참조
const pausedTime = ref(0)
const totalPausedDuration = ref(0)

const updateElapsedTime = () => {
  const now = Date.now()
  const diff = now - startTime.value - totalPausedDuration.value // 일시정지된 시간을 고려
  const minutes = Math.floor(diff / 60000)
  const seconds = Math.floor((diff % 60000) / 1000)

  elapsedTime.value = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
}

const startRecording = async () => {
  console.log('녹음이 시작됨')
  recording.value = true
  startTime.value = Date.now()
  updateElapsedTime()
  timer.value = setInterval(updateElapsedTime, 1000)
  if (navigator.mediaDevices.getUserMedia) {
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
    mediaRecorder.value = new MediaRecorder(stream)
    audioChunks.value = []
    mediaRecorder.value.ondataavailable = (event) => {
      audioChunks.value.push(event.data)
    }
    mediaRecorder.value.start()
    audioStore.setRecordingStatus(true)
  } else {
    alert('오디오 녹음을 지원하지 않는 브라우저입니다.')
  }
}

const pauseRecording = () => {
  if (mediaRecorder.value && recording.value && !paused.value) {
    mediaRecorder.value.pause()
    clearInterval(timer.value)
    paused.value = true
    pausedTime.value = Date.now() // 일시정지 시작 시간 저장
    console.log('녹음이 일시정지됨')
  }
}

const resumeRecording = () => {
  if (mediaRecorder.value && paused.value) {
    mediaRecorder.value.resume()
    const pausedDuration = Date.now() - pausedTime.value
    totalPausedDuration.value += pausedDuration // 총 일시정지 시간 업데이트
    timer.value = setInterval(updateElapsedTime, 1000)
    paused.value = false
    console.log('녹음이 재개됨')
  }
}

const stopRecording = () => {
  console.log('레코딩 멈춰 명령 실행')
  if (mediaRecorder.value) {
    console.log('녹음파일 있으니 녹음 중지할게요')
    mediaRecorder.value.stop()
    mediaRecorder.value.stream.getTracks().forEach((track) => track.stop()) // 스트림의 모든 트랙을 멈춤. 마이크 종료
    clearInterval(timer.value)
    pausedTime.value = 0
    totalPausedDuration.value = 0
    recording.value = false
    mediaRecorder.value.onstop = async () => {
      const audioBlob = new Blob(audioChunks.value, { type: 'audio/wav' })
      console.log('업로드 함수 실행 직전')
      await uploadAudio(audioBlob)
      audioStore.setRecordingStatus(false)
    }
  } else {
    console.log('녹음 시작은 했니?')
  }
}

const uploadAudio = async (audioBlob) => {
  const formData = new FormData()
  formData.append('file', audioBlob, 'recording.wav')

  console.log('트라이 직전')
  for (let [key, value] of formData.entries()) {
    console.log(`${key}:`, value)
  }
  // FastAPI 서버로 오디오 파일 전송
  try {
    console.log('녹음파일 전송')
    const res1 = await instance.post(
      `api/studies/${studyId}/meetings/${meetingID.value}/record`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        timeout: 99999999999
      }
    )
    console.log(res1)
    // 5초 후에 res2를 실행하도록 setTimeout 사용
    setTimeout(async () => {
      console.log('리마인드 퀴즈 생성 요청')
      const res2 = await instance.post(
        `api/studies/${studyId}/meetings/${meetingID.value}/remind-quiz`
      )
      console.log(res2)
    }, 5000) // 5초 (5000 밀리초) 후에 실행

    // const data = response.data;
    // console.log("Transcription result:", data);
    // recordText.value = data.transcription;
    // console.log(recordText.value)
  } catch (error) {
    console.error('오류가 발생했습니다:', error)
  }
}
// --------------------------- 녹음  ---------------------------------------- //
</script>

<style scoped>
.gradient-btn {
  background: linear-gradient(to right, #3fb1fa, #05d4c0);
  color: white;
  padding: 20px 20px;
}
.content {
  height: 840px;
  overflow-y: auto;
}

.submitted-note,
.question {
  border: 1px rgba(242, 242, 242, 1) solid;
  width: 1300px;
}
</style>
