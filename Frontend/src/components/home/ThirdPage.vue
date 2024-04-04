<template>
  <div class="page d-flex justify-center align-center flex-column">
    <v-container class="d-flex justify-center align-center">
      <v-row class="content">
        <div class="oval-background ml-16 d-flex flex-column align-center justify-center">
          <div data-aos="zoom-in-down">
            <div class="text-h3 mb-10">
              <p class="my-2">
                방문해주셔서 <span class="font-weight-bold text-teal-accent-2">감사감사..</span>하마..
              </p>
            </div>
          </div>
        </div>
      </v-row>
    </v-container>
    <div class="moving-image-container mt-40">
      <img src="@/assets/image/home/flowhama.png" alt="Moving Image">
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.page {
  width: 1500px;
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

.moving-image-container {
  position: relative;
  overflow: hidden;
  width: 1820px; /* Adjust as per your image size */
  height: 400px; /* Adjust as per your image size */
}

.moving-image-container img {
  position: absolute;
  top: 0;
  object-fit: cover;
  left: 100%; /* Start from the right side of the container */
  animation: moveLeft 11s linear infinite; /* Adjust duration and timing function as needed */
}

@keyframes moveLeft {
  0% {
    left: 100%; /* Start position */
  }
  100% {
    left: -100%; /* End position (completely outside of the container) */
  }
}
</style>
