<template>
  <div>
    <h2>{{ studyStore.studyName }}에서 함께 공부한 내용</h2>
  </div>
  <v-row>
    <!-- v-for 사용해야함 -->
    <v-col cols="3">

    </v-col>

  </v-row>
</template>

<script setup>
  // import { ref } from 'vue'
  import { useStudyStore } from '@/stores/study'
  // import { useAudioStore } from '@/stores/audioStore';
  // import axios from 'axios';

  const studyStore = useStudyStore()

</script>

<style scoped></style>
