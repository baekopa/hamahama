<template>
  <div
    class="login-page d-flex justify-center align-center"
    style="background: linear-gradient(rgba(159, 218, 255, 1), rgba(5, 212, 192, 1))"
  >
    <div class="d-flex component rounded-xl">
      <v-row align="center" class="align-center">
        <v-col cols="12" md="6" class="d-flex justify-center">
          <img
            src="@/assets/image/login/main.png"
            alt="Test Image"
            style="width: 510px; height: 510px"
          />
        </v-col>

        <v-col cols="12" md="6" class="d-flex flex-column align-center">
          <h1 class="mb-10 text-3xl point-font">
            오늘도 열공<span class="hama point-font"> 하마하마!!</span>
          </h1>
          <SocialLogin />
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script setup>
import SocialLogin from '@/components/login/SocialLogin.vue'
</script>

<style scoped>
.login-page {
  background: linear-gradient(rgba(159, 218, 255, 1), rgba(5, 212, 192, 1));
  height: calc(100vh - 58px);
}
.component {
  width: 1333px;
  height: 756px;
  background-color: rgba(255, 255, 255, 0.58);
}
.hama {
  color: #3fb1fa;
}
</style>
