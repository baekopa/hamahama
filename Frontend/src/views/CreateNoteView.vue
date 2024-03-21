<template>
  <div class="bg-gray d-flex flex-column align-center justify-center">
    <div class="d-flex flex-column align-center justify-center bg-white">
      <div class="justify">
        <img width="50" src="@/components/icons/note/pencil.svg" alt="연필 이미지" />
        <span class="text-h6 ml-4">공부하마 노트 작성</span>
      </div>
      <div style="width: 1000px">
        <v-text-field v-model="title" label="제목"></v-text-field>
        <v-textarea v-model="content" label="내용" outlined rows="20"></v-textarea>
      </div>
      <div class="justify-end">
        <v-chip @click="share" class="export" flat>내보내기</v-chip>
        <v-chip @click="CreateNote" class="save" flat>저장</v-chip>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { apiInstance } from '@/api/index'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

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
  if (title.value === '') {
    alert('제목은 필수 내용은 선택')
  } else {
    Swal.fire({
      title: '노트를 저장하시겠습니까?',
      showCancelButton: true,
      confirmButtonText: '저장하기'
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
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
            Swal.fire('Saved!', '', 'success')
            console.log('저장성공')
            noteId = 1
            // noteId = res.data.noteId
            router.push({ name: 'note', params: { id: noteId } })
          })
          .catch((err) => {
            const noteId = 1
            router.push({ name: 'note', params: { id: noteId } })
            Swal.fire('저장에 실패했어요<br>잠시 후 다시 시도해주세요', '', 'info')
            // alert('저장에 실패했어요 잠시 후 다시 시도해주세요')
            console.log('저장실패', err)
            router.push({ name: 'note', params: { id: noteId } })
          })
      }
    })
  }
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
