<template>
  <v-container>
    <div class="title">{{ title }}</div>
    <div class="d-flex">
      <img src="" alt="userImg" />
      <div class="d-flex flex-column ml-4">
        <span>{{ userName }}</span>
        <span>{{ date }}</span>
      </div>
      <img
        class="cursor-pointer"
        @click="isEdit = !isEdit"
        src="@/assets/image/note/edit.svg"
        alt="User Image"
      />
    </div>

    <v-sheet
      v-if="isEdit === false"
      class="d-flex align-center justify-center flex-wrap text-center mx-auto px-4"
      elevation="4"
      height="700"
      width="1000"
      rounded
    >
      <div>
        <div class="text-area text-h5 font-weight-medium mb-2">
          {{ content }}
        </div>
        <v-btn color="orange" variant="text">Go to Login</v-btn>
      </div>
    </v-sheet>

    <v-textarea
      v-else
      class="justify-center flex-wrap mx-auto px-4"
      style="
        width: 1000px;
        height: 700px;
        font-family: 'Arial', sans-serif;
        line-height: 2;
        background-color: #f7f7f7;
      "
      v-model="content"
      label="수정"
      outlined
      no-resize
      row-height="2"
      rows="20"
    ></v-textarea>

    <v-sheet
      height="600"
      width="650"
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
        <v-btn>내보내기</v-btn>
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
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const noteId = ref(route.params.id)
const fetchNote = () => {
  // axios를 사용하여 GET 요청을 수행합니다.
  axios
    .get(`/api/note/${noteId.value}`)
    .then((response) => {
      console.log(response)
    })
    .catch((error) => {
      console.error('Error fetching note:', error)
    })
}

// onMounted(fetchNote())

const title = ref('title')
const content = ref(
  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
)

const userImgUrl = ref('')
const userName = ref('백오파')
const date = ref('2024.03.13')
const studyList = ref(['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming'])
const selectedStudy = ref('')
const isEdit = ref(false)
const sharedStudy = ref(['a', 'b', 'c', 'd', 'e', 'f', 'e'])

function edit() {
  console.log(selectedStudy.value)
  api
    .put(
      `api/notes/${noteId}`,
      {
        title,
        content
      },
      {
        headers: {
          AUTHORIZATION: 'a'
        }
      }
    )
    .then((res) => {
      console.log('저장성공')
    })
    .catch((err) => {
      alert('저장실패')
      console.log('저장실패', err)
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
.share-study {
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
