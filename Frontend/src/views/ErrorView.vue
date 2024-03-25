<template>
  <div>
      <div class="w-full">
        <div class="">
          <h1 style="" class="error-type text-center font-extrabold">404</h1>
        </div>
        <div class="error-msg">
          <h2 class="error-text text-5xl mb-4 font-bold">요청하신 내용을 찾을 수 없어요!</h2>
          <h3 class="error-text text-2xl">입력하신 주소가 정확한지 다시 한번 확인해주세요.</h3>
        </div>  
        <div class="error-image">
          <img src="../assets/image/error.png" alt="" width="600px" /><img />
        </div>
      </div>
      <!-- <v-col cols="12" md="4">
        <img src="../assets/image/error.png" alt="" width="500" /><img />
      </v-col> -->
  </div>
</template>

<script setup></script>

<style scoped>
.error-type {
  position:fixed;
  margin-top: -20px;
  margin-left: 380px;
  font-size: 500px;
  color: #FF8990;
  opacity: 0.6;
  /* -webkit-text-stroke-width: 10px; */
  -webkit-text-stroke-color: #FF8990;
  font-family: "UhBeeSe_hyun";
}

.error-image {
  position: fixed;
  z-index: 1;
  margin-top: 200px;
  margin-left: 1170px;
}

.error-msg {
  position: fixed;
  margin-top: 630px;
  margin-left: 620px;
}

.error-text {
  text-align: right;
}
</style>
