<template>
  <v-container>
    <v-layout style="max-height: 800px">
      <v-navigation-drawer style="width: 323px; height: 850px">
        <p class="text-3xl text-center mt-10 point-font text-stone-900">
          {{ studyStore.studyType }}
        </p>
        <v-list lines="two" density="compact" nav>
          <v-list-item three-line>
            <v-list-item-content class="align-self-center">
              <div class="ml-14 mt-10">
                <div class="text-xl font-bold block">
                  {{ studyStore.studyTitle }}
                </div>
              </div>
              <v-list-item-subtitle class="ml-14 mt-1"
                ><div class="text-base">
                  {{ studyStore.studyCategory }}
                </div></v-list-item-subtitle
              >
            </v-list-item-content>
          </v-list-item>

          <div class="ml-8 mt-8">
            <v-list-item
              @click="GoHome()"
              prepend-icon="mdi-view-dashboard"
              value="home"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >스터디 홈</v-list-item
            >
            <v-list-item
              @click="GoSummary()"
              prepend-icon="mdi-forum"
              value="summary"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >요약</v-list-item
            >
            <v-list-item
              @click="GoQuiz()"
              prepend-icon="mdi-help-box"
              value="quiz"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >리마인드 퀴즈</v-list-item
            >
            <v-list-item
              @click="GoSetting()"
              prepend-icon="mdi-account-key"
              value="setting"
              color="primary"
              rounded="xl"
              class="pl-6 text-xl"
              >스터디 관리</v-list-item
            >
          </div>
        </v-list>
      </v-navigation-drawer>
      <v-divider style="height: 900px" class="mr-10" vertical></v-divider>
      <v-main class="ml-10 mt-5 overflow-y-auto" style="min-height: 850px">
        <v-container>
          <div class="d-flex justify-between">
            <div class="title d-flex flex-column">
              <span class="text-2xl ml-5 font-bold">
                <span class="tossface text-3xl">🔏 </span
                ><span class="point-color font-bold">{{ studyStore.studyTitle }}</span> 스터디
                관리</span
              >
              <p class="text-base ml-5 mt-2 italic text-gray-500">
                <span>{{ '스터디 정보를 변경하세요.' }}</span>
              </p>
            </div>
          </div>
          <v-divider
            :thickness="2"
            class="border-opacity-50 my-3"
            style="width: 1300px"
            color="info"
          ></v-divider>

          <div class="d-flex flex-column" style="width: 1500px">
            <div class="d-flex">
              <div class="">
                <img
                  :src="studyStore.studyBackgroundImage"
                  alt="스터디이미지"
                  class="study-image"
                />
              </div>
              <div class="mt-6 ml-12 d-flex flex-column">
                <div>
                  <v-chip size="x-large">{{ studyStore.studyCategory }}</v-chip>
                </div>
                <div>
                  <textarea
                    class="mt-5 note-content"
                    style="width: 800px; height: 230px"
                    :value="studyStore.studyDescription"
                  ></textarea>
                </div>
              </div>
            </div>
            <div class="d-flex mt-20">
              <div class="mr-16" style="width: 600px">
                <div class="d-flex justify-between mr-10">
                  <span class="text-2xl font-bold">스터디원</span>
                  <span class="text-xl"
                    ><span class="tossface">👤 </span> {{ studyStore.studyMembers.length }}</span
                  >
                </div>
                <v-divider
                  :thickness="2"
                  class="border-opacity-75 my-3"
                  style="width: 600px"
                  color="info"
                ></v-divider>
                <div v-if="isLeader && studyStore.studyType == '같이하마'" class="invite-user mt-5">
                  <div class="d-flex flex-column">
                    <div class="d-flex">
                      <input
                        type="text"
                        id="memberName"
                        v-model="memberName"
                        @input="searchMembers"
                        variant="plain"
                        placeholder="초대할 스터디원의 이메일을 입력하세요."
                        class="content-title w-full"
                      />
                    </div>

                    <div v-if="members.length > 0" class="mt-5">
                      <span>검색 결과:</span>
                      <ul>
                        <li
                          v-for="(member, index) in members"
                          :key="index"
                          @click="selectMember(member)"
                        >
                          {{ member.name }} <span class="mr-2">{{ member.email }}</span>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <v-btn @click="InviteStudy()">초대하기</v-btn>
                </div>
                <div class="member-list">
                  <div
                    class="d-flex align-center pa-4"
                    v-for="member in studyStore.studyMembers"
                    :key="member.id"
                  >
                    <img class="user-profile mr-4" :src="member.image" alt="profile" width="50px" />
                    <div class="point-color font-bold">
                      <p class="mr-2" v-if="member.type == 'STUDY_LEADER'">스터디장</p>
                      <p class="mr-2" v-else-if="member.type == 'STUDY_MEMBER'">스터디원</p>
                      <p class="mr-2" v-else-if="member.type == 'INVITATION'">초대요청</p>
                    </div>
                    <p class="mr-2">{{ member.name }}</p>
                    <p>{{ member.email }}</p>
                    <!-- 스터디장일 때 -->
                    <div class="ml-5">
                      <v-chip v-if="member.type == 'STUDY_LEADER'">위임하기</v-chip>
                      <v-chip v-else-if="member.type == 'STUDY_MEMBER'">강퇴하기</v-chip>
                      <v-chip v-else-if="member.type == 'INVITATION'">초대취소</v-chip>
                    </div>
                  </div>
                </div>
              </div>
              <div class="study-schedule d-flex flex-column" style="width: 600px">
                <div class="d-flex justify-between">
                  <div>
                    <div class="d-flex mr-10">
                      <span class="text-2xl font-bold mr-5">스터디일정</span>
                      <span v-for="(date, index) in studyDate" :key="index"
                        ><v-chip color="#3FB1FA" variant="flat" class="mr-3" v-if="date === '1'">{{
                          getDayOfWeek(index)
                        }}</v-chip></span
                      >
                    </div>
                    <v-divider
                      :thickness="2"
                      class="border-opacity-75 my-3"
                      style="width: 600px"
                      color="info"
                    ></v-divider>
                  </div>
                  <!-- <div class="study-date mr-4" v-for="(date, index) in studyDate" :key="index">
                    <p v-if="date === '1'">{{ getDayOfWeek(index) }}</p>
                  </div>
                  <p>{{ studyStartTime }} ~ {{ studyEndTime }}</p> -->
                </div>
                <v-btn @click="CreateMeeting" class="w-full" color="#3FB1FA">일정추가</v-btn>
                <div class="schedule-list">
                  <div
                    class="pa-4 align-center"
                    v-for="schedule in scheduleList"
                    :key="schedule.id"
                  >
                    <p>{{ schedule.studyAt }} - {{ schedule.topic }}</p>
                    <hr />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </v-container>
      </v-main>
    </v-layout>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStudyStore } from '@/stores/study'
import { useAuthStore } from '@/stores/auth'
import Swal from 'sweetalert2'
import instance from '@/api'

const route = useRoute()
const router = useRouter()
const studyId = route.params.id
const studyStore = useStudyStore()
const authStore = useAuthStore()

const studyDate = ref('0010100')
const studyStartTime = ref('19:00')
const studyEndTime = ref('21:00')

const scheduleList = ref([])

function GoHome() {
  router.push({ name: 'study', params: { id: studyId } })
}
function GoQuiz() {
  router.push({ name: 'studyQuiz', params: { id: studyId } })
}
function GoSummary() {
  router.push({ name: 'studySummary', params: { id: studyId } })
}

const memberName = ref('')
const members = ref([])
const selectedMembers = ref([])
const selectedMembersName = ref([])

const isLeader = ref(false)

const searchMembers = async () => {
  if (!memberName.value.trim()) {
    members.value = []
    return
  }

  try {
    const response = await instance.get(`api/members?q=${memberName.value}`)
    console.log(response)
    members.value = response.data.data
  } catch (error) {
    console.error('멤버 검색 오류:', error)
    // 오류 처리
  }
}

const selectMember = (member) => {
  // 이미 선택된 멤버인지 확인
  if (!selectedMembers.value.find((m) => m.id === member.id)) {
    selectedMembers.value.push(member.memberId)
    selectedMembersName.value.push(member)
    memberName.value = member.email;
  }
}

const toggleMemberSelection = (member) => {
  const index = selectedMembers.value.findIndex((m) => m.id === member.id)
  if (index !== -1) {
    // 이미 선택된 멤버인 경우 선택 해제
    selectedMembers.value.splice(index, 1)
    selectedMembersName.value.splice(index, 1)
  } else {
    // 선택되지 않은 멤버인 경우 선택
    selectedMembers.value.push(member.memberId)
  }
}

function InviteStudy() {
  instance
    .post(`api/studies/${studyId}/members`, {
      memberId: selectedMembers.value[0]
    })
    .then((res) => {
      if (res.data.status == 201) {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: res.data.message,
          showConfirmButton: false,
          timer: 1500
        })
        router.go(0);
      } else {
        console.log(res)
      }
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

function LoadMeetingSchedule() {
  instance
    .get(`api/studies/${studyId}/meetings`)
    .then((res) => {
      const data = res.data
      if (data.status == 200) {
        console.log(res.data.message)
        scheduleList.value = data.data
      }
    })
    .catch((err) => {
      console.log(err)
    })
}

async function CreateMeeting() {
  const { value: formValues } = await Swal.fire({
    title: '스터디 일정 추가하마',
    html: `
        <style>
            .swal2-label {
                display: inline-block;
                width: 100px; /* 라벨 너비 조정 */

            }
            .swal2-input {
                width: calc(100% - 200px); /* 입력란 너비 조정 */
            }
        </style>
        <label for="swal-input1" class="swal2-label">스터디 주제</label>
        <input id="swal-input1" class="swal2-input">
        <label for="swal-input2" class="swal2-label">날짜</label>
        <input type="date" id="swal-input2" class="swal2-input">
        <label for="swal-input3" class="swal2-label">시간</label>
        <input type="time" id="swal-input3" class="swal2-input">
    `,
    focusConfirm: false,
    preConfirm: () => {
      return [
        document.getElementById('swal-input1').value,
        document.getElementById('swal-input2').value,
        document.getElementById('swal-input3').value
      ]
    }
  })

  if (formValues[0] && formValues[1] && formValues[2]) {
    instance
      .post(`api/studies/${studyId}/meetings`, {
        topic: formValues[0],
        studyAt: `${formValues[1]} ${formValues[2]}`
      })
      .then((res) => {
        const data = res.data
        console.log(data.message)
        if (data.status == 201) {
          Swal.fire({
            icon: 'success',
            title: '일정 추가 성공'
          })
          LoadMeetingSchedule()
        }
      })
      .catch((err) => {
        Swal.fire({
          icon: 'error',
          title: '일정 추가 실패',
          text: `${err.response.message}`
        })
      })
  } else {
    Swal.fire({
      icon: 'error',
      title: '일정 추가 실패',
      text: '주제, 날짜, 시간은 필수 입력입니다.'
    })
  }
}

function LoadStudyData() {
  instance.get(`api/studies/${studyId}/settings`).then((res) => {
    const data = res.data.data
    if (res.data.status == 200) {
      console.log(data)
      studyStore.studyTitle = data.title
      studyStore.studyDescription = data.description
      studyStore.studyBackgroundImage = data.backgroundImage
      studyStore.studyCategory = data.category
      studyStore.studyMembers = data.members
      studyStore.studyType = data.type
      const matchingMember = data.members.find((member) => {
        return member.email === authStore.userEmail && member.name === authStore.userName
      })
      if (matchingMember.type === 'STUDY_LEADER') {
        isLeader.value = true
      }
    }
  })
}

onMounted(() => {
  LoadStudyData()
  LoadMeetingSchedule()
})
</script>

<style scoped>
.study-image {
  height: 350px;
  width: 350px;
}
.user-profile {
  height: 50px;
  width: 50px;
  border-radius: 50%;
}
.member-list {
  overflow-y: auto;
  width: 580px;
  height: 200px;
}
.study-schedule {
  width: 650px;
}
.schedule-list {
  height: 280px;
  overflow-y: auto;
}
.content-title {
  font-size: medium;
  outline: none;
}
.note-content {
  font-size: large;
  outline: none;
  /* line-height: 30px; */
}
</style>
