<template>
  <v-container>
    <div class="d-flex">
      <div style="position: relative">
        <v-img
          @click="uploadUserImage"
          src="https://randomuser.me/api/portraits/men/85.jpg"
          alt="Profile"
          class=""
          style="height: 300px; width: 300px"
        />
        <img
          src="@/assets/image/mypage/addImageIcon.svg"
          alt="Profile"
          class=""
          style="height: 30px; width: 30px; position: absolute; bottom: 0%; right: 0%"
        />
      </div>
      <div class="ml-10">
        <div class="text-h3 mb-4">기본 정보</div>
        <div>
          <span>이름</span>
          <span>{{ userName }}</span>
          <v-btn class="">수정</v-btn>
        </div>
        <div>
          <span>이메일</span>
          <span>{{ userEmail }}</span>
          <v-btn class="">수정</v-btn>
        </div>
        <div>
          <span>생년월일</span>
          <span>{{ userBirthDay }}</span>
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
const uploadUserImage = () => {
  console.log('zz')
}

const userName = ref('')
const userEmail = ref('')
const userBirthDay = ref('')

onMounted(() => {
  getMyInfo()
    .then(() => {})
    .catch(() => {})
})
</script>

<style scoped>
.v-btn {
  background-color: rgba(63, 177, 250, 1);
  color: white;
}
</style>
