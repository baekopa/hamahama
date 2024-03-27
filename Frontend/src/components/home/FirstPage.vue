<template>
  <div class="page d-flex align-center flex-column mt-20">
    <div class="mt-16">
      <v-container>
        <v-row>
          <div class="shape">
          </div>
          <v-col cols="" class="d-flex flex-column justify-center">
            <div data-aos="zoom-in-down">
              <div class="text-h3 mb-10">
                <p class="my-2">오프라인 스터디는</p>
                <p class="my-2">
                  <span class="font-weight-bold text-light-blue-accent-2 point-font">하마하마</span>와 함께
                </p>
              </div>
              <div class="text-h6">
                <p class="my-5">스터디 녹음본으로 멋드러진 요약 정리 하마하마....</p>
              </div>
              <v-btn @click="goLogin"><div class="text-xl">시작하기</div></v-btn>
            </div>
          </v-col>
          <v-col cols="6">
            <img class="ml-20" src="@/assets/image/home/main2.png" style="width:550px" alt="하마1" />
          </v-col>
        </v-row>
      </v-container>
    </div>
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

.shape {
  margin-left: -3vw;
  position:absolute;
 	background: linear-gradient(210deg, #05D4C055 0%, #3FB1FA55 100%);
  animation: morph 13s ease-in-out infinite;
  border-radius: 60% 40% 30% 70% / 60% 30% 70% 40%;
  height: 300px;
  transition: all 1s ease-in-out;
  width: 340px;
}

@keyframes morph {
  20% {
    border-radius: 45% 55% 62% 38% / 53% 51% 49% 47%;
  }
  40% {
    border-radius: 45% 55% 49% 51% / 36% 51% 49% 64%;
  }
  60% {
    border-radius: 60% 40% 57% 43% / 47% 62% 38% 53%;
  }
  80% {
    border-radius: 60% 40% 32% 68% / 38% 36% 64% 62%;
  }
}

</style>
