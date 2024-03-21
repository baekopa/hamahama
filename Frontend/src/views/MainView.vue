<!-- 로그인 후 메인페이지 -->
<template>
  <v-container>
    <Slider class="my-10" />
    <StudyList class="pa-10 my-10" />
    <MyNote class="pa-10 my-10" />
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import StudyList from '@/components/main/StudyList.vue'
import MyNote from '@/components/main/MyNote.vue'
import Slider from '@/components/main/Slider.vue'

const authStore = useAuthStore()
</script>

<style></style>
