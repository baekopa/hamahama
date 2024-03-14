<!-- 로그인 후 메인페이지 -->
<template>
  <div class="mainpage">
    <div>
      <Slider />
    </div>
    <div class="study-list">
      <StudyList />
    </div>
    <div class="my-note">
      <MyNote />
    </div>
  </div>
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
