<template>
  <div class="page d-flex justify-center align-center flex-column">
    <v-container>
      <v-row>
        <v-col cols="6">
          <img src="@/assets/image/Main/main3.png" alt="하마3" />
        </v-col>
        <v-col cols="6" class="oval-background d-flex flex-column align-center justify-center">
          <div data-aos="zoom-in-down">
            <div class="text-h3 mb-10">
              <p class="my-2">개인 요약도 가능</p>
              <p class="my-2">
                <span class="font-weight-bold text-teal-accent-2">하마하마</span>
              </p>
            </div>
            <p class="my-2 text-h6">하마하마....</p>
            <v-btn>시작하기</v-btn>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup></script>

<style scoped>
.page {
  height: 900px;
}

.oval-background::before {
  content: '';
  position: absolute;
  top: 30%; /* 조정하여 위치 변경 가능 */
  right: -5%; /* 조정하여 위치 변경 가능 */
  transform: translate(-50%, -50%) rotate(60deg);
  width: 60%; /* 타원의 너비 조정 */
  height: 70%; /* 타원의 높이 조정 */
  background-color: #05d4c0; /* 배경색과 투명도 조정 */
  opacity: 24%;
  border-radius: 60%;
  z-index: -1;
}

.v-col {
  position: relative; /* ::before 가상 요소를 위한 상대 위치 지정 */
}
</style>
