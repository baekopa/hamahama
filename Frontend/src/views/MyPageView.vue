<template>
  <v-container class="mypage" style="max-height: 800px">
    <v-layout style="max-height: 800px">
      <v-navigation-drawer style="width: 323px; height: 800px">
        <p class="text-h5 text-center mt-10">마이페이지</p>
        <v-list lines="two" density="compact" nav>
          <v-list-item three-line>
            <v-list-item-avatar class="d-flex justify-center align-center">
              <img
                src="https://randomuser.me/api/portraits/men/85.jpg"
                alt="Profile"
                class="rounded-circle"
                style="height: 212px; width: 212px"
              />
            </v-list-item-avatar>
            <v-list-item-content class="align-self-center">
              <v-list-item-title class="text-center text-h6 mt-5">백오파</v-list-item-title>
              <v-list-item-subtitle class="text-center mt-1"
                >backopa@example.com</v-list-item-subtitle
              >
            </v-list-item-content>
          </v-list-item>

          <div class="">
            <v-list-item
              @click="selectedComponent = 'DashBoard'"
              prepend-icon="mdi-view-dashboard"
              title="대시보드"
              value="dashboard"
              color="primary"
              rounded="xl"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'Study'"
              prepend-icon="mdi-account-group"
              title="같이하마"
              value="study"
              color="primary"
              rounded="xl"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'MyStudy'"
              prepend-icon="mdi-square-edit-outline"
              title="공부하마"
              value="mystudy"
              color="primary"
              rounded="xl"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'RemindQuiz'"
              prepend-icon="mdi-help-box"
              title="리마인드 퀴즈"
              value="quiz"
              color="primary"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'MyInfo'"
              prepend-icon="mdi-account-key"
              title="내 정보"
              value="myinfo"
              color="primary"
              rounded="xl"
            ></v-list-item>
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

const userinfo = ref([
  { name: '백오파', email: 'backOpa@gmail.com', profileimg: 'https://vuejs.org/images/logo.png' }
])

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
    return { selectedComponent }
  }
}

// 여기서 모든 정보(스케쥴,요약) 요청을 할까?? 어떻게 할까 모르겠다
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
