<template>
  <v-container>
    <div class="flex justify-center">
      <v-card variant="flat" style="width:1400px">
        <div class="d-flex justify-between">
          <div class="title d-flex items-center">
            <span class="text-3xl font-bold">
              <span class="tossface text-3xl">🔐 </span>
              <span class="point-color font-bold">{{ userName }}</span>기본 정보 수정</span>
         </div>
        </div>
        <v-divider :thickness="2" class="border-opacity-50 my-3" style="width: 1400px" color="info"></v-divider>
        </v-card>
      </div>
    <div class="d-flex ml-10 mt-10">
      <div style="position: relative">
        <v-card
          @click="uploadUserImage"
          image="https://ilyo.co.kr/contents/article/images/2021/0119/1610984144335236.jpg"
          alt="Profile"
          class="rounded-lg"
          variant="flat"
          hover
          style="height: 300px; width: 300px"><div style="height: 300px; width: 300px" class="img-text text-2xl d-flex items-center justify-center">이미지 변경</div></v-card>
        <img
          src="@/assets/image/mypage/addImageIcon.svg"
          alt="Profile"
          class="mr-2 mb-2"
          style="height: 30px; width: 30px; position: absolute; bottom: 0%; right: 0%"
        />
      </div>
      <div class="ml-10 mt-8">
        <div class="d-flex mb-7">
          <div class="w-52">
            <span class="text-2xl">이름</span>
            <span>{{ authStore.userName }}</span>
          </div>
          <v-btn class="">수정</v-btn>
        </div>
        <div class="d-flex mb-7">
          <div class="w-52">
            <span class="text-2xl">이메일</span>
            <span>{{ authStore.userEmail }}</span>
          </div>
          <v-btn class="">수정</v-btn>
        </div>

        <div class="change-password mt-10">
          <div>비밀번호 변경하기</div>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyInfo } from '@/api/mypage'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

onMounted(() => {
  getMyInfo()
})
</script>

<style scoped>
.v-btn {
  background-color: rgba(63, 177, 250, 1);
  color: white;
}

.img-text {
  color: #ffffff00;
  font-weight: bold;
  
}

.img-text:hover {
  background-color: #00000022;
  color: white;
}
</style>
