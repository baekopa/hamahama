<template>
  <v-container>
    <v-card>
      <v-layout>
        <v-navigation-drawer floating permanent>
          <p class="text-h5 text-center mt-4">마이페이지</p>
          <v-list density="compact" nav>
            <v-list-item three-line>
              <v-list-item-avatar class="d-flex justify-center align-center">
                <!-- 아바타 크기를 100으로 설정 -->
                <img
                  src="https://randomuser.me/api/portraits/men/85.jpg"
                  alt="Profile"
                  class="rounded-circle"
                  style="height: 200px; width: 200px"
                />
                <!-- 이미지 스타일 직접 조정 -->
              </v-list-item-avatar>
              <v-list-item-content class="align-self-center">
                <v-list-item-title class="text-center text-h6 mt-5">백오파</v-list-item-title>
                <v-list-item-subtitle class="text-center mt-1"
                  >backopa@example.com</v-list-item-subtitle
                >
              </v-list-item-content>
            </v-list-item>

            <v-list-item
              @click="selectedComponent = 'DashBoard'"
              prepend-icon="mdi-view-dashboard"
              title="대시보드"
              value="dashboard"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'Study'"
              prepend-icon="mdi-view-dashboard"
              title="같이하마"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'MyStudy'"
              prepend-icon="mdi-view-dashboard"
              title="공부하마"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'RemindQuiz'"
              prepend-icon="mdi-forum"
              title="리마인드 퀴즈"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'MyInfo'"
              prepend-icon="mdi-forum"
              title="내 정보"
            ></v-list-item>
          </v-list>
        </v-navigation-drawer>
        <v-main style="min-height: 800px"> <component :is="selectedComponent"></component></v-main>
      </v-layout>
    </v-card>
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

<style scoped></style>
