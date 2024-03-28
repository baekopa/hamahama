<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer floating permanent>
        <v-list density="compact" nav>
          <v-list-item
            @click="GoHome"
            prepend-icon="mdi-view-dashboard"
            title="홈"
            value="home"
          ></v-list-item>
          <v-list-item
            @click="GoSummary"
            prepend-icon="mdi-forum"
            title="요약"
            value="summary"
          ></v-list-item>
          <v-list-item
            @click="GoQuiz"
            prepend-icon="mdi-forum"
            title="리마인드 퀴즈"
            value="quiz"
          ></v-list-item>
          <v-list-item prepend-icon="mdi-forum" title="스터디 관리" value="setting"></v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-main>
        <div>
          <div>
            <p>{{ studyStore.studyTitle }}</p>
          </div>
          <div class="study-info d-flex">
            <img :src="studyStore.studyBackgroundImage" alt="스터디이미지" class="study-image" />
            <div class="ml-10">
              <v-chip>{{ studyStore.studyCategory }}</v-chip>
              <p>{{ studyStore.studyDescription }}</p>
            </div>
          </div>
          <div class="flex">
            <div class="mr-16">
              <div class="d-flex">
                <p class="mr-3">스터디원</p>
                <p>{{ studyStore.studyMembers.length }}</p>
              </div>
              <div class="invite-user">
                <input
                  v-bind="inviteUser"
                  type="text"
                  label="스터디원으로 초대할 유저의 이메일을 입력하세요"
                />
                <v-btn @click="InviteStudy()">초대하기</v-btn>
              </div>
              <div class="member-list">
                <div class="d-flex my-4 align-center" v-for="member in studyStore.studyMembers">
                  <img class="user-profile mr-4" :src="member.image" alt="profile" width="50px" />
                  <p class="mr-2" v-if="member.type == 'STUDY_LEADER'">스터디장</p>
                  <p class="mr-2" v-else>스터디원</p>
                  <p>{{ member.name }}</p>
                </div>
              </div>
            </div>
            <div class="study-schedule">
              <div class="d-flex justify-between">
                <p class="mr-4">스터디 일정</p>
                <div class="study-date mr-4" v-for="(date, index) in studyDate" :key="index">
                  <p v-if="date === '1'">{{ getDayOfWeek(index) }}</p>
                </div>
                <p>{{ studyStartTime }} ~ {{ studyEndTime }}</p>
              </div>
              <v-btn class="w-full" color="#3FB1FA">일정추가</v-btn>
              <div class="schedule-list">
                <div v-for="schedule in scheduleList">
                  <p>{{ schedule.title }}</p>
                  <p>{{ schedule.time }}</p>
                  <hr />
                </div>
              </div>
            </div>
          </div>
        </div>
      </v-main>
    </v-layout>
  </v-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStudyStore } from '@/stores/study'
import instance from '@/api'
const route = useRoute()
const router = useRouter()
const studyId = route.params.id
const studyStore = useStudyStore()
const inviteUser = ref()

const studyDate = ref('0010100')
const studyStartTime = ref('19:00')
const studyEndTime = ref('21:00')

const scheduleList = ref([
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜',
    time: '2024/03/15 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜',
    time: '2024/03/15 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜',
    time: '2024/03/15 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜',
    time: '2024/03/15 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜',
    time: '2024/03/15 PM 3:00'
  },
  {
    id: 1,
    title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜',
    time: '2024/03/15 PM 3:00'
  },
  { id: 1, title: '24년 3월 8일 스터디의 주제는 다음과 같습니당나귀뚜', time: '2024/03/15 PM 3:00' }
])

function GoHome() {
  router.push({ name: 'study', params: { id: studyId } })
}
function GoQuiz() {
  router.push({ name: 'studyQuiz', params: { id: studyId } })
}
function GoSummary() {
  router.push({ name: 'studySummary', params: { id: studyId } })
}

function InviteStudy(user) {
  instance
    .post(`api/study/invite/${inviteUser}`)
    .then((res) => {
      console.log(res)
    })
    .catch((err) => {
      console.log(err)
    })
}

const getDayOfWeek = (index) => {
  // 시작 요일이 월요일이라고 가정
  const daysOfWeek = ['월', '화', '수', '목', '금', '토', '일']
  return daysOfWeek[index]
}
</script>

<style scoped>
.study-info {
  width: 1300px;
  height: 550px;
}
.study-image {
  height: 389px;
  width: 389px;
}
.user-profile {
  height: 50px;
  width: 50px;
  border-radius: 50%;
}
.study-member {
  width: 600px;
  height: 470px;
  border: 1px solid;
}
.study-schedule {
  width: 650px;
  height: 650px;
}
.schedule-list {
  overflow-y: auto;
}
</style>
