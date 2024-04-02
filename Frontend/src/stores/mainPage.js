import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { defineStore } from 'pinia'

export const useMainPageStore = defineStore('main', () => {
  const router = useRouter()
  const myStudyId = ref(0)
  const recentEditNote = ref(0)
  const myStudyImg = ref('')
  function GoCreateNote() {
    router.push({ name: 'createnote' })
  }
  function GoRecentEditNote() {
    if (recentEditNote.value == -1) {
      GoCreateNote()
    } else {
      router.push({ name: 'note', params: { id: recentEditNote.value } })
    }
  }
  function GoMyStudyRoom() {
    router.push({ name: 'study', params: { id: myStudyId.value } })
  }

  return { myStudyId, recentEditNote, myStudyImg, GoCreateNote, GoRecentEditNote, GoMyStudyRoom }
})
