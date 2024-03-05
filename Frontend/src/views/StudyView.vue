<template>
  <v-container>
    <v-card>
      <v-layout>
        <v-navigation-drawer floating permanent>
          <v-list density="compact" nav>
            <v-list-item
              @click="selectedComponent = 'StudyHome'"
              prepend-icon="mdi-view-dashboard"
              title="홈"
              value="home"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'StudySchedule'"
              prepend-icon="mdi-forum"
              title="스케쥴"
              value="about"
            ></v-list-item>
            <v-list-item
              @click="selectedComponent = 'StudySummary'"
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
import StudyHome from '@/components/study/StudyHome.vue'
import StudySchedule from '@/components/study/StudySchedule.vue'
import StudySummary from '@/components/study/StudySummary.vue'

export default {
  components: {
    StudyHome,
    StudySchedule,
    StudySummary
  },
  setup() {
    const selectedComponent = ref('StudyHome')
    return { selectedComponent }
  }
}

// 여기서 모든 정보(스케쥴,요약) 요청을 할까?? 어떻게 할까 모르겠다
</script>

<style scoped></style>
