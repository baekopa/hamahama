<template>
  <v-container>
    <div class="d-flex">
      <div class="ml-10">
        <div class="text-h3 mb-4">내 정보</div>
        <div>
          <span>이름</span>
          <span>{{ authStore.userName }}</span>
          <v-btn class="">수정</v-btn>
        </div>
        <div>
          <span>이메일</span>
          <span>{{ authStore.userEmail }}</span>
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
</style>
