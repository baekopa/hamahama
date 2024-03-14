<template>
  <v-container class="entire">
    <div>공부하마 노트 작성 <span>혼자 공부한 내용을 정리해보세요!!</span></div>
    <div>
      <v-text-field v-model="title" label="제목"></v-text-field>
      <v-textarea v-model="content" label="내용" variant="outlined"></v-textarea>
      <div class="d-flex justify-end">
        <v-chip @click="share" class="export" variant="flat">내보내기</v-chip>
        <v-chip @click="CreateNote" class="save" variant="flat">저장</v-chip>
      </div>
    </div>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { apiInstance } from '@/api/index'
import { useRouter } from 'vue-router'
const api = apiInstance()
const router = useRouter()
const title = ref('')
const content = ref('')

function share() {
  if (title.value === '') {
    alert('제목은 필수 내용은 선택')
  } else {
    //여기에 어디로 보낼지 모달 만들어서 해야할듯
  }
}

function CreateNote() {
  api
    .post(
      `api/notes`,
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
      // router.push({name:''})
    })
    .catch((err) => {
      alert('저장실패')
      console.log('저장실패', err)
    })
}

function kakaoLogin(param) {
  return new Promise((resolve, reject) => {
    api
      .post(`api/auth/kakao?redirect_uri=${param}`)
      .then((response) => {
        // 여기서 필요한 처리를 수행합니다.
        resolve(response.data) // 예시: 성공 시 데이터를 resolve합니다.
      })
      .catch((error) => {
        // 에러 처리
        reject(error) // 에러를 reject합니다.
      })
  })
}
</script>

<style scoped>
.export {
  background-color: #05d4c0;
}
.save {
  background-color: #3fb1fa;
}
.v-container {
  width: 1000px;
}
</style>
