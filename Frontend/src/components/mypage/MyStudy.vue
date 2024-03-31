<template>
  <v-container>
    <div class="title">
      <p class="text-2xl ml-5 font-bold"><span class="tossface text-3xl">📝</span> 공부하마</p>
      <p class="text-xl ml-5 mt-2 italic text-gray-500">
        {{ authStore.userName }}님께서 정리한 노트입니다.
      </p>
    </div>

    <v-card rounded="0" variant="flat" class="note-list">
      <div class="list-section">
        <v-row class="pa-10">
          <v-col
            cols="12"
            sm="6"
            md="4"
            lg="3"
            class="mb-8"
            v-for="note in noteList"
            :key="note.id"
          >
            <v-card
              @click="GoNoteDetail(note.id)"
              variant="outlined"
              class="rounded-lg study-card"
              color="#4e4e4e"
              hover
              width="250"
              height="380"
            >
              <template v-slot:loader="{ isActive }">
                <v-progress-linear
                  :active="isActive"
                  color="deep-purple"
                  height="4"
                  indeterminate
                ></v-progress-linear>
              </template>

              <v-card-item class="grid content-between">
                <div class="ml-2 mt-2 mb-1 text-3xl leading-normal font-bold note-card">
                  {{ note.title }}
                </div>
                <div class="grid justify-items-start">
                  <v-card-subtitle class="my-1">
                    <span class="me-1"
                      ><span class="tossface">⏲</span> {{ note.createAt }} 작성</span
                    >
                  </v-card-subtitle>
                  <v-chip v-if="note.studies != null" variant="tonal">
                    {{ note.studies }}
                    <img
                      :src="note.studyImage ? note.studyImage : emptynote"
                      class="shared-study-image"
                      end
                    />
                  </v-chip>
                  <v-chip v-else variant="tonal">
                    제출한 스터디 없음
                    <img :src="emptynote" class="shared-study-image" end />
                  </v-chip>
                </div>
              </v-card-item>
              <v-divider class="mx-4 mb-1"></v-divider>
            </v-card>
          </v-col>
          <v-col cols="12" sm="6" md="4" lg="3" class="mb-8 d-flex align-center">
            <v-card
              @click="AddStudy"
              variant="tonal"
              color="#3FB1FA"
              class="rounded-lg d-flex justify-center items-center"
              hover
              width="250"
              height="380"
            >
              <div class="text-2xl text-center">
                <p>+</p>
                <p>새 노트</p>
              </div>
            </v-card>
          </v-col>
        </v-row>
      </div>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import instance from '@/api'
import { useAuthStore } from '@/stores/auth'
import emptynote from '@/assets/image/main/emptynote.jpg'
const authStore = useAuthStore()
const router = useRouter()

const noteList = ref([])

const GoNoteDetail = (id) => {
  router.push({ name: 'note', params: { id } })
}
const GetNoteList = () => {
  instance
    .get(`api/members/me/notes`)
    .then((res) => {
      if (res.data.status === 200) {
        noteList.value = res.data.data
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

const AddStudy = () => {
  router.push({ name: 'createnote' })
}

onMounted(() => {
  GetNoteList()
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

.v-card {
  border-radius: 30px;
}

.note-list {
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

.note-card {
  height: 280px;
}

.chip {
  background-color: #3fb1fa;
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
