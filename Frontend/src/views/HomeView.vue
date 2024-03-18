<template>
  <main>
    <div class="d-flex justify-center align-center">
      <FirstPage />
    </div>
    <div class="d-flex justify-center align-center">
      <SecondPage />
    </div>
    <div class="d-flex justify-center align-center">
      <ThirdPage />
    </div>
  </main>
</template>

<script setup>
import FirstPage from '@/components/home/FirstPage.vue'
import SecondPage from '@/components/home/SecondPage.vue'
import ThirdPage from '@/components/home/ThirdPage.vue'
import TopNav from '@/components/home/TopNav.vue'
</script>

<style scoped>
/* Add custom styles here */
</style>
