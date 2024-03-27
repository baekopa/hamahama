<template>
  <v-container>
    <v-btn icon="mdi-arrow-left"></v-btn>
    <div class="title">{{ title }}</div>
    <div class="d-flex">
      <img :src="userImgUrl" alt="userImg" />
      <div class="d-flex flex-column ml-4">
        <span>{{ userName }}</span>
        <span>생성일 : {{ createdAt }}</span>
        <span>수정일 : {{ modifiedAt }}</span>
      </div>

      <img
        v-if="!isEdit"
        class="cursor-pointer"
        @click="isEdit = !isEdit"
        src="@/assets/image/note/edit.svg"
        alt="pencil"
      />
      <v-btn @click="EditNote" v-else prepend-icon="$vuetify">수정완료</v-btn>
    </div>

    <!-- 작성한노트 -->
    <v-sheet
      v-if="isEdit === false"
      class="d-flex justify-center flex-wrap mx-auto px-4"
      elevation="4"
      height="1000"
      width="1300"
      rounded
    >
      <div class="text-area text-h5 font-weight-medium mb-2">
        <p>{{ content }}</p>
      </div>
    </v-sheet>

    <!--노트수정-->
    <v-textarea
      v-else
      class="justify-center flex-wrap mx-auto px-4"
      style="font-family: 'Arial', sans-serif; line-height: 2; background-color: #f7f7f7"
      v-model="editContent"
      label="수정"
      outlined
      no-resize
      row-height="2"
      rows="20"
    ></v-textarea>

    <div class="summary d-flex flex-column justify-center">
      <div class="d-flex">
        <p>요약</p>
        <v-btn @click="MakeSummary">요약생성</v-btn>
      </div>

      <div class="summary-content">
        <p>{{ noteSummary }}</p>
      </div>
    </div>

    <!-- 노트 스터디에 공유 -->
    <v-sheet
      v-if="!isEdit"
      height="550"
      width="950"
      border
      class="share-study align-center justify-center flex-wrap text-center mx-auto px-4 my-10"
    >
      <div class="d-flex flex-column">
        <span>게시된 스터디</span>
        <span>공부한 내용을 스터디원들에게 공유해보세요</span>
      </div>
      <div class="d-flex align-center">
        <v-select
          class="study-select"
          v-model="selectedStudy"
          label="Select"
          :items="studyList"
          variant="outlined"
        ></v-select>
        <v-btn @click="ShareNote">내보내기</v-btn>
      </div>

      <v-row style="overflow-y: auto; max-height: 380px">
        <v-col v-for="study in sharedStudy" :key="study" cols="12" sm="6">
          <v-card
            class="mx-auto my-2"
            max-width="344"
            subtitle="prepend and append"
            :title="study"
            elevation="2"
          >
            <template v-slot:prepend>
              <v-avatar size="24">
                <v-img alt="John" src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
              </v-avatar>
            </template>
            <v-card-text>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-sheet>
  </v-container>
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

const userImgUrl = ref('')
const userName = ref('백오파')
const createdAt = ref('')
const modifiedAt = ref('')
const studyList = ref(['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming'])
const selectedStudy = ref('')
const isEdit = ref(false)
const sharedStudy = ref(['a', 'b', 'c', 'd', 'e', 'f', 'e'])
const noteSummary = ref('')

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
