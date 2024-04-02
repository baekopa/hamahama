import { ref } from 'vue'

import { defineStore } from 'pinia'

export const useLoadStore = defineStore('load', () => {
  const isLoading = ref(false)

  return {
    isLoading
  }
})
