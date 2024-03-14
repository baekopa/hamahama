<template>
  <div>
    <div class="title">{{ authStore.userName }}님은 오늘도 열공중!</div>

    <div class="mt-10">
      <span>여기는 뭐가 나오나</span>
    </div>

    <div class="schedule-group d-flex mt-16">
      <div class="weekly-study">
        <span class="text-h6">주간 스터디 일정</span>
        <div></div>
      </div>
      <div class="schedule">
        <span class="text-h6">일정</span>
        <v-card class="scroll-container" max-width="425">
          <v-list two-line>
            <template v-for="(item, index) in scheduleItems" :key="index">
              <v-list-item class="mt-3">
                <template v-slot:title>{{ item.title }} {{ item.name }}</template>
                <template v-slot:subtitle>
                  <span class="font-weight-bold">{{ item.date }}</span>
                </template>
              </v-list-item>
            </template>
          </v-list>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const scheduleItems = ref([
  {
    title: '[토익 스터디]',
    name: 'Ali Connors',
    date: '2024.03.12'
  },
  {
    title: 'Summer BBQ',
    name: 'to Alex, Scott, Jennifer',
    date: '2024.03.12'
  },
  {
    title: 'Oui oui',
    name: 'Sandra Adams',
    date: '2024.03.12'
  },
  {
    title: 'Brunch this weekend?',
    name: 'Ali Connors',
    date: '2024.03.12'
  },
  {
    title: 'Summer BBQ',
    name: 'to Alex, Scott, Jennifer',
    date: '2024.03.12'
  },
  {
    title: 'Oui oui',
    name: 'Sandra Adams',
    date: '2024.03.12'
  },
  {
    title: 'Brunch this weekend?',
    name: 'Ali Connors',
    date: '2024.03.12'
  },
  {
    title: 'Summer BBQ',
    name: 'to Alex, Scott, Jennifer',
    date: '2024.03.12'
  },
  {
    title: 'Oui oui',
    name: 'Sandra Adams',
    date: '2024.03.12'
  }
])
</script>

<style scoped>
/* 스크롤 기능을 위한 스타일 */

.scroll-container {
  max-height: 300px; /* 원하는 높이로 설정 */
  overflow-y: auto; /* 내용이 넘칠 경우 세로 스크롤바 활성화 */
  border-radius: 20px; /* 컨테이너 모서리를 둥글게 */
  scrollbar-width: thin; /* 스크롤바 너비를 얇게 조정(Firefox) */
  scrollbar-color: #64b5f6 #e0f2f1; /* 스크롤바 색상 조정(Firefox) */
}
</style>
