<template>
  <v-container class="align-center mt-16">
    <!-- Fluid 컨테이너로 전체 너비를 사용 -->
    <v-row align="center" class="align-center">
      <!-- 이미지를 포함하는 왼쪽 컬럼, 여기서는 기본적으로 수평 중앙 정렬됩니다 -->
      <v-col cols="12" md="6" class="d-flex justify-center">
        <img src="@/assets/image/login/main.png" alt="Test Image" style="width: 60%" />
      </v-col>

      <!-- 텍스트와 컴포넌트를 포함하는 오른쪽 컬럼, 여기서는 내부 요소들이 수직 및 수평 중앙 정렬됩니다 -->
      <v-col cols="12" md="6" class="d-flex flex-column align-center">
        <h1 class="mb-10">오늘도 열공하마하마</h1>
        <KakaoLogin />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import KakaoLogin from '@/components/login/KakaoLogin.vue'
</script>

<style scoped></style>
