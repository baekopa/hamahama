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
          <v-col cols="12" sm="6" md="4" lg="3" class="mb-8 d-flex align-center">
            <v-card
              @click="AddStudy"
              variant="tonal"
              color="#3FB1FA"
              class="rounded-xl d-flex justify-center items-center"
              hover
              width="250"
              height="350"
            >
              <div class="text-2xl text-center">
                <p>+</p>
                <p>새 노트</p>
              </div>
            </v-card>
          </v-col>
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
              variant="text"
              class="rounded-xl study-card"
              color="#2e2e2e"
              hover
              width="250"
              height="350"
            >
              <template v-slot:loader="{ isActive }">
                <v-progress-linear
                  :active="isActive"
                  color="deep-purple"
                  height="4"
                  indeterminate
                ></v-progress-linear>
              </template>

              <v-card-item class="grid content-between note-card rounded-xl" style="height: 350px">
                <div class="grid" style="height: 320px">
                  <div>
                    <div class="mx-2 mt-2 mb-1 text-xl font-bold line-clamp-2 text-gray-600">
                      {{ note.title }}
                    </div>
                    <span class="mx-2 mt-2 text-gray-400 italic">{{ note.modifiedAt }} 작성</span>
                    <div class="mx-2 mt-3 line-clamp-6 text-gray-500">
                      {{ note.content }}
                    </div>
                  </div>
                  <div class="place-self-end">
                    <v-chip v-if="note.isShared == true" variant="flat" color="#aaaaaa">
                      공유됨
                      <!-- <img :src=note.studyImage class="shared-study-image" end /> -->
                    </v-chip>
                  </div>
                </div>
              </v-card-item>
              <v-divider class="mx-4 mb-1"></v-divider>
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
  height: 350px;
  border: 1px solid #bfbfbf;
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
