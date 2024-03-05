<template>
  <v-container>
    <v-card>
      <v-layout>
        <v-navigation-drawer floating permanent>
          <v-list density="compact" nav>
            <v-list-item
              @click="selectedComponent = 'DashBoard'"
              prepend-icon="mdi-view-dashboard"
              title="홈"
              value="home"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'MyInfo'"
              prepend-icon="mdi-forum"
              title="내정보"
              value="about"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'MyStudy'"
              prepend-icon="mdi-forum"
              title="요약"
              value="summary"
            ></v-list-item>
          </v-list>
        </v-navigation-drawer>
        <v-main style=""> <component :is="selectedComponent"></component></v-main>
      </v-layout>
    </v-card>
  </v-container>
</template>

<script>
import { ref } from 'vue'
import DashBoard from '@/components/mypage/DashBoard.vue'
import MyInfo from '@/components/mypage/MyInfo.vue'
import MyStudy from '@/components/mypage/MyStudy.vue'

export default {
  components: {
    DashBoard,
    MyInfo,
    MyStudy
  },
  setup() {
    const selectedComponent = ref('DashBoard')
    return { selectedComponent }
  }
}

// 여기서 모든 정보(스케쥴,요약) 요청을 할까?? 어떻게 할까 모르겠다
</script>

<style scoped></style>
