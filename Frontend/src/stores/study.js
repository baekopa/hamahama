import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { defineStore } from 'pinia'

export const useStudyStore = defineStore('study', () => {
  const studyTitle = ref('')
  const studyDescription = ref('')
  const studyBackgroundImage = ref('')
  const studyCategory = ref('')
  const studyMembers = ref([])

  const studyAt = ref('')

  return {
    studyTitle,
    studyDescription,
    studyBackgroundImage,
    studyCategory,
    studyMembers,
    studyAt
  }
})
