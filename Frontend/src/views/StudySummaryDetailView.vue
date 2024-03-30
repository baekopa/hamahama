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
            <span>스터디 주제 : {{ topic }}</span>
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
              <p>{{ summaryContent }}</p>
              <div class="mt-10">
                <p>참여자</p>
                <div class="d-flex">
                  <div class="pa-3" v-for="member in members">{{ member }}</div>
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
                <div class="pa-3" v-for="keyword in keywords" :key="keyword.id">
                  #{{ keyword.keyword }}
                </div>
              </div>
            </div>
          </div>
          <div v-else-if="toggle == '전문'">
            <div>
              <p>{{ scriptContent }}</p>
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
import Swal from 'sweetalert2'

const studyStore = useStudyStore()
const router = useRouter()
const route = useRoute()
const meetingId = route.params.id
const studyId = route.params.studyId
const toggle = ref('요약')
const isEdit = ref(false)

const summaryContent = ref('')
const scriptContent = ref('')
const keywords = ref([])
const topic = ref('')
const members = ref(['김수민', '이수민', '여아정'])

// 사용자가 요약을 수정할 수 있도록
const editedSummary = ref('')
watch(summaryContent, (newValue) => {
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

// 키워드 조회
function LoadKeyword() {
  instance
    .get(`api/studies/${studyId}/meetings/${meetingId}/keyword`)
    .then((res) => {
      if (res.data.status == 201) {
        keywords.value = res.data.data.keyword
      }
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

// 산출물 조회 (주제, 요약, 전문, 키워드 , 참여자는 추가예정)
function LoadAll() {
  instance
    .get(`api/studies/${studyId}/meetings/${meetingId}/all`)
    .then((res) => {
      console.log(res)
      if (res.data.status == 200) {
        topic.value = res.data.data.topic
        summaryContent.value = res.data.data.summaryContent
        scriptContent.value = res.data.data.scriptContent
        keywords.value = res.data.data.keyword
      }
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
      LoadAll()
    })
    .catch((err) => {
      console.log(err)
    })
}

function EditSummary() {
  instance
    .put(`api/studies/${studyId}/meetings/${meetingId}/summary-update`, {
      summaryText: editedSummary.value
    })

    .then((res) => {
      console.log(res)
      if (res.data.status == 201) {
        summaryContent.value = res.data.data.summaryText
      } else {
      }
      isEdit.value = !isEdit.value
    })
    .catch((err) => {
      console.log(err)
    })
}

onMounted(() => {
  LoadAll()
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
