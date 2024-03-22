<template>
  <div class="page d-flex justify-center align-center flex-column">
    <v-container>
      <v-row>
        <v-col cols="6" class="oval-background d-flex flex-column align-center justify-center">
          <div data-aos="zoom-in-down">
            <div class="text-h3 mb-10">
              <p class="my-2">오프라인 스터디는</p>
              <p class="my-2">
                <span class="font-weight-bold text-light-blue-accent-2">하마하마</span>와 함께
              </p>
            </div>
            <p class="my-2 text-h6">스터디 녹음본으로 멋드러진 요약 정리 하마하마....</p>
            <v-btn @click="goLogin">시작하기</v-btn>
          </div>
        </v-col>
        <v-col cols="6">
          <img src="@/assets/image/home/hama1.png" alt="하마1" />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()

const goLogin = function () {
  router.push({ name: 'login' })
}
</script>

<style scoped>
.page {
  height: 900px;
}

.oval-background::before {
  content: '';
  position: absolute;
  top: 30%; /* 조정하여 위치 변경 가능 */
  left: 10%; /* 조정하여 위치 변경 가능 */
  transform: translate(-50%, -50%) rotate(-60deg);
  width: 60%; /* 타원의 너비 조정 */
  height: 70%; /* 타원의 높이 조정 */
  background-color: rgba(63, 177, 250, 0.6); /* 배경색과 투명도 조정 */
  opacity: 24%;
  border-radius: 60%;
  z-index: 1;
}

.v-col {
  position: relative; /* ::before 가상 요소를 위한 상대 위치 지정 */
}
</style>
