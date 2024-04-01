<template>
  <v-container class="mypage" style="max-height: 800px">
    <v-layout style="max-height: 800px">
      <v-navigation-drawer style="width: 323px; height: 800px">
        <p class="text-3xl text-center mt-10 point-font text-stone-900">마이페이지</p>
        <v-list lines="two" density="compact" nav>
          <v-list-item three-line>
            <v-list-item-avatar class="d-flex justify-center align-center mt-5">
              <img
                :src="authStore.userImgUrl"
                alt="Profile"
                class="rounded-full border-4 border-slate-900"
                style="height: 200px; width: 200px"
              />
            </v-list-item-avatar>
            <v-list-item-content class="align-self-center">
              <v-list-item-title class="ml-14 mt-10"
                ><div class="text-2xl font-bold">{{ authStore.userName }}</div></v-list-item-title
              >
              <v-list-item-subtitle class="ml-14 mt-1"
                ><div class="text-base">{{ authStore.userEmail }}</div></v-list-item-subtitle
              >
            </v-list-item-content>
          </v-list-item>

          <div class="ml-8 mt-8">
            <v-list-item
              @click="selectedComponent = 'DashBoard'"
              prepend-icon="mdi-view-dashboard"
              value="dashboard"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >대시보드</v-list-item
            >
            <v-list-item
              @click="selectedComponent = 'Study'"
              prepend-icon="mdi-account-group"
              value="study"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >같이하마</v-list-item
            >
            <v-list-item
              @click="selectedComponent = 'MyStudy'"
              prepend-icon="mdi-square-edit-outline"
              value="mystudy"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >공부하마</v-list-item
            >
            <v-list-item
              @click="selectedComponent = 'RemindQuiz'"
              prepend-icon="mdi-help-box"
              value="quiz"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >리마인드 퀴즈</v-list-item
            >
            <v-list-item
              @click="selectedComponent = 'MyInfo'"
              prepend-icon="mdi-account-key"
              value="myinfo"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >내 정보</v-list-item
            >
          </div>
        </v-list>
      </v-navigation-drawer>
      <!-- 세로줄 -->
      <v-divider style="height: 900px" class="mr-10" vertical></v-divider>
      <v-main class="ml-10 mt-5" style="min-height: 800px">
        <component :is="selectedComponent"></component
      ></v-main>
    </v-layout>
  </v-container>
</template>

<script>
import { ref } from 'vue'
import DashBoard from '@/components/mypage/DashBoard.vue'
import MyInfo from '@/components/mypage/MyInfo.vue'
import MyStudy from '@/components/mypage/MyStudy.vue'
import Study from '@/components/mypage/Study.vue'
import RemindQuiz from '@/components/mypage/RemindQuiz.vue'

import { useAuthStore } from '@/stores/auth'

export default {
  components: {
    DashBoard,
    MyInfo,
    MyStudy,
    Study,
    RemindQuiz
  },
  setup() {
    const selectedComponent = ref('DashBoard')
    const authStore = useAuthStore()
    return { selectedComponent, authStore }
  }
}
</script>

<style>
.study-select {
  margin-top: 50px;
}

::-webkit-scrollbar {
  border-radius: 30px;
  width: 8px;
}

::-webkit-scrollbar-track {
  background-color: white;
  border-radius: 30px;
}

::-webkit-scrollbar-thumb {
  background-color: #a5b4fc;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #818cf8;
}
</style>
