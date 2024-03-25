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
import instance from '@/api/index'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

const router = useRouter()
const title = ref('')
const content = ref('')

const studyList = ref([])

function share() {
  if (title.value === '') {
    Swal.fire({
      title: '작성한 내용이 없어요',
      text: '제목은 필수 내용은 선택!',
      icon: 'question'
    })
  } else {
    instance
      .get('api/members/meetings')
      .then((res) => {
        studyList.value = res.data
        ShareNote()
      })
      .catch((err) => {
        console.log(err)
      })
  }
}

function CreateNote() {
  if (title.value === '') {
    Swal.fire({
      title: '작성한 내용이 없어요',
      text: '제목은 필수 내용은 선택!',
      icon: 'question'
    })
  } else {
    Swal.fire({
      title: '노트를 저장하시겠습니까?',
      showCancelButton: true,
      confirmButtonText: '저장하기'
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        instance
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

async function ShareNote() {
  const { value: study } = await Swal.fire({
    title: '스터디로 내보내기 하마하마?',
    input: 'select',
    inputOptions: {
      Fruits: {
        apples: 'Apples',
        bananas: 'Bananas',
        grapes: 'Grapes',
        oranges: 'Oranges'
      },
      Vegetables: {
        potato: 'Potato',
        broccoli: 'Broccoli',
        carrot: 'Carrot'
      },
      icecream: 'Ice cream'
    },
    inputPlaceholder: '스터디 모임 전에 내용을 공유해보세요!',
    showCancelButton: true,
    inputValidator: (value) => {
      return new Promise((resolve) => {
        if (value) {
          resolve()
        } else {
          resolve('You need to select oranges :)')
        }
      })
    }
  })
  if (study) {
    Swal.fire(`You selected: ${study}`)
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
