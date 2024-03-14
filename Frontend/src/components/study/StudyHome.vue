<template>
  <div>
    <p>{{ studyStore.studyName }}</p>
    <p>이수민</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStudyStore } from '@/stores/study'

const studyStore = useStudyStore()
</script>

<style scoped></style>
