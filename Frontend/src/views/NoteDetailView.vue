<template>
  <div class="mb-32">
  <div class="bg-white d-flex flex-column items-center mt-15">
    <div class="d-flex flex-column" style="width: 1300px">
      <div class="text-gray-500 point-font"><span class="text-xl mr-2"><</span><span class="tossface text-xl">📝</span> 공부하마 노트</div>
      <div class="note-title">{{ title }}</div>
      <div class="d-flex justify-between">
        <div class="d-flex items-center">
          <div>
            <img :src="userImgUrl" alt="userImg" class="profile-img"/>
          </div>
          <div class="mx-2">{{ userName }}</div>
        </div>
        <div class="d-flex items-center">
          <div>
            <div>생성일 : {{ createdAt }}</div>
            <div>수정일 : {{ modifiedAt }}</div>
          </div>
          <div class="ml-5">
            <img
              v-if="!isEdit"
              class="cursor-pointer"
              @click="isEdit = !isEdit"
              src="@/assets/image/note/edit.svg"
              alt="pencil"
            />
            <v-btn @click="EditNote" v-else prepend-icon="$vuetify">수정완료</v-btn>
          </div>
        </div>
      </div>
      <textarea v-if="isEdit === false" readonly v-model="content" variant="plain" class="note-content mt-5" rows="15"></textarea>
      <textarea v-else v-model="content" variant="plain" placeholder="공부한 내용을 작성해주세요. ( •̀ ω •́ )✧" class="note-content mt-5" rows="20"></textarea>
    </div>
    <div class="d-flex flex-column mt-20" style="width: 1300px">
      <div class="d-flex items-end justify-between">
        <div class="note-title point-font"> 요약 <span class="tossface">💻</span></div>
        <v-btn @click="MakeSummary" size="large" class="save" variant="flat" color="#3fb1fa" rounded="xl">요약생성</v-btn>
      </div>
      <textarea readonly v-model="noteSummary" variant="plain" class="note-content mt-5" rows="8"></textarea>
      
    </div>
    <div class="d-flex flex-column mt-20" style="width: 1300px">
      <div class="d-flex items-end justify-between">
        <div class="note-title point-font">공유하기 <span class="tossface">👥</span></div>
      </div>
      <div class="note-content text-gray-500">공부한 내용을 스터디 미팅으로 공유해보세요. </div>
      <!-- 노트 스터디에 공유 -->
      <v-sheet
        v-if="!isEdit"
        height="550"
        width="1300"
        class="share-study"
      >
        <div class="d-flex align-end mt-3">
          <v-select
            class="study-select"
            v-model="selectedStudy"
            label="예정된 미팅"
            :items="studyList"
            :item-props="true"
            variant="underlined"
          ></v-select>
          <v-btn @click="ShareNote" size="large" class="save ml-5 mb-6" variant="flat" color="#3fb1fa" rounded="xl">내보내기</v-btn>
          <div class="w-1/2"></div>
        </div>

        <v-row>
          <v-col v-for="study in sharedStudy" :key="study" cols="12" sm="4">
            <v-card
              class="mr-2 my-2 rounded-md"
              max-width="440"
              subtitle="2024-03-20 22:00"
              :title="study"
              variant="tonal"
              color="gray"
              hover
            >
              <template v-slot:prepend>
                <v-avatar size="25">
                  <img alt="studyImg" src="@/assets/image/mypage/hama.png" />
                </v-avatar>
              </template>
              <v-card-text>
                CS 면접 대비 2차수 - 네트워크
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-sheet>
    </div>
  </div>
</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import instance from '@/api/index'

const route = useRoute()
const noteId = route.params.id

// 원본 제목, 내용
const title = ref('제목')
const content = ref('')
// 수정 할 내용 (초기값은 원본 내용임)
const editContent = ref('')

watch(content, (newValue) => {
  editContent.value = newValue
})

const userImgUrl = ref('https://cdn.vuetifyjs.com/images/john.png')
const userName = ref('백오파')
const createdAt = ref('2024-01-20 14:00')
const modifiedAt = ref('2024-01-20 14:00')
const studyList = ref([
  {
    title: 'CS 면접 대비 1',
    subtitle: '공부하마하마스터디 @2024-05-01 14:00',
  },
  {
    title: 'CS 면접 대비 2',
    subtitle: '공부하마하마스터디 @2024-05-01 14:00',
  },
  {
    title: 'CS 면접 대비 3',
    subtitle: '공부하마하마스터디 @2024-05-01 14:00',
  },
])
const selectedStudy = ref('')
const isEdit = ref(false)
const sharedStudy = ref(['a', 'b', 'c', 'd', 'e', 'f', 'e'])
const noteSummary = ref('현재 노트에 대한 요약이 없어요. 요약 생성 버튼으로 요약된 노트 내용을 확인해보세요! 😊')

// 노트 내용 조회
const LoadNoteData = () => {
  instance
    .get(`api/notes/${noteId}`)
    .then((res) => {
      if (res.data.status == 200) {
        title.value = res.data.data.title
        content.value = res.data.data.content
        userImgUrl.value = res.data.data.writerImage
        userName.value = res.data.data.writerName
        createdAt.value = res.data.data.createdAt
        modifiedAt.value = res.data.data.modifiedAt
        noteSummary.value = res.data.data.summary
        console.log(res)
      }
    })
    .catch((error) => {
      console.error('Error fetching note:', error)
    })
}

onMounted(LoadNoteData)

// 노트 수정하기
function EditNote() {
  instance
    .put(`api/notes/${noteId}`, {
      title,
      editContent
    })
    .then((res) => {
      console.log(res)
      console.log('수정성공')
      content.value = editContent.value
      isEdit.value = false
    })
    .catch((err) => {
      isEdit.value = false
      alert('저장실패')
      console.log('저장실패', err)
    })
}

// 노트 내보내기
const ShareNote = () => {
  instance
    .post(`api/notes/${noteId}/meetings`, {
      title,
      editContent
    })
    .then((res) => {
      console.log('수정성공')
      content.value = editContent.value
      isEdit.value = false
    })
    .catch((err) => {
      isEdit.value = false
      alert('저장실패')
      console.log('저장실패', err)
    })
}

const MakeSummary = () => {
  instance
    .post(`api/notes/${noteId}/summary`)
    .then((res) => {
      if (res.data.status == 201) {
        noteSummary.value = res.data.data.noteSummary
      }
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}
</script>

<style scoped>
.text-area {
  border-radius: 10px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
  max-height: 600px;
}
.study-select {
  margin-top: 50px;
}

.summary-content {
  width: 1300px;
  height: 400px;
  border: solid 1px black;
}

.note-title {
  font-size: x-large;
  outline: none;
  margin: 20px 0px;
  font-weight: bold;
}

.note-content {
  font-size: large;
  outline: none;
  /* line-height: 30px; */
}

.profile-img {
  width: 40px;
  border-radius: 50%;
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
