import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { defineStore } from 'pinia'

export const useMainPageStore = defineStore('main', () => {
  const router = useRouter()
  const myStudy = ref(0)
  const recentEditNote = ref(0)
  const myStudyImg = ref('')
  function GoCreateNote() {
    router.push({ name: 'createnote' })
  }
  function GoRecentEditNote(id) {
    router.push({ name: 'note', params: { id } })
  }
  function GoMyStudyRoom(id) {
    router.push({ name: 'study', params: { id } })
  }

  return { myStudy, recentEditNote, GoCreateNote, GoRecentEditNote, GoMyStudyRoom }
})
