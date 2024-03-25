<template>
  <v-container class="page">
    <div class="d-flex align-center">
      <img src="@/assets/image/study/rocketIcon.svg" alt="로켓" width="73px" />
      <span class="text-h4 font-weight-black ml-4">스터디 만들기</span>
    </div>
    <v-form fast-fail @submit.prevent>
      <v-sheet
        elevation="3"
        rounded="lg"
        class="basic-form mt-10 d-flex flex-column"
        style="height: 552px"
      >
        <div class="pa-10">
          <span class="text-h5 font-weight-black">스터디 기본 정보</span>
        </div>

        <v-col class="pa-10 d-flex">
          <v-sheet class="mr-10">
            <v-file-input
              id="backgroundImage"
              :rules="rules"
              accept="image/png, image/jpeg, image/bmp"
              style="height: 400px; width: 400px"
            ></v-file-input>
          </v-sheet>

          <div class="ml-10" style="width: 500px">
            <div class="">
              <span class="text-h5">스터디 제목</span>
              <v-text-field
                class="mt-5"
                v-model="studyName"
                :rules="studyNameRules"
                variant="outlined"
                label="스터디 제목"
              ></v-text-field>
            </div>
            <div class="">
              <span class="text-h5">스터디 분류</span>
              <v-text-field
                class="mt-5"
                variant="outlined"
                v-model="studyCategory"
                label="스터디 종류"
              ></v-text-field>
            </div>
            <div>
              <span>스터디 멤버 초대</span>
            </div>

            <div v-if="selectedMembers.length > 0" class="mt-5">
              <span>선택된 멤버:</span>
              <ul>
                <li v-for="(member, index) in selectedMembers" :key="index">
                  {{ member.full_name }}
                </li>
              </ul>
            </div>
          </div>
        </v-col>
      </v-sheet>

      <v-sheet rounded="lg" class="detail-schedule mt-10 pa-10 d-flex flex-column">
        <div class="d-flex align-center">
          <img src="@/assets/image/study/calendar.svg" alt="" width="68px" height="68px" />
          <span class="text-h4 ml-4 font-weight-black">상세일정</span>
        </div>
        <div class="d-flex align-center ml-10">
          <span class="ml-16 text-h6 font-weight-black">스터디 요일</span>
          <v-row class="ml-16">
            <v-col class="ml-12">
              <v-sheet class="py-4 px-1">
                <v-chip-group
                  @click="convertTagsToBinaryString"
                  v-model="selectedDay"
                  selected-class="text-primary"
                  multiple
                >
                  <v-chip v-for="tag in tags" :key="tag" :value="tag" size="x-large" class="mx-10">
                    {{ tag }}
                  </v-chip>
                </v-chip-group>
              </v-sheet>
            </v-col>
          </v-row>
        </div>

        <div class="d-flex align-center mt-10 ml-10">
          <span class="ml-16 text-h6 font-weight-black">스터디 시작일</span>
          <v-row class="ml-16">
            <v-col class="ml-16" cols="auto">
              <input id="date" type="date" v-model="startDay" />
            </v-col>
          </v-row>
          <span class="text-h6 font-weight-black">스터디 종료일</span>
          <v-row class="ml-16">
            <v-col cols="auto">
              <input id="date" type="date" v-model="endDay" />
            </v-col>
          </v-row>
        </div>

        <div class="d-flex align-center mt-10 ml-10">
          <span class="ml-16 text-h6 font-weight-black">스터디 시작시간</span>
          <v-row class="ml-11">
            <v-col class="ml-16" cols="auto">
              <input id="time" type="time" v-model="startTime" />
            </v-col>
          </v-row>
          <span class="text-h6 font-weight-black">스터디 종료시간</span>
          <v-row class="ml-11">
            <v-col cols="auto">
              <input id="time" type="time" v-model="endTime" />
            </v-col>
          </v-row>
        </div>
      </v-sheet>

      <v-sheet class="study-description mt-10 pa-10 d-flex flex-column">
        <div class="d-flex align-center">
          <img src="@/assets/image/study/pinIcon.svg" alt="" width="68px" />
          <span class="text-h4 ml-4 font-weight-black">스터디 설명</span>
        </div>
        <div></div>
        <div class="pa-10 d-flex align-center">
          <v-textarea
            rounded="lg"
            v-bind="studyDescription"
            no-resize
            label="스터디에 대한 정보를 적어주세요"
            variant="outlined"
            class="mt-5"
          ></v-textarea>
        </div>
      </v-sheet>

      <v-sheet class="d-flex justify-end mt-5">
        <v-btn @click="checkForm" class="m-btn mr-5" rounded="lg" type="submit">스터디 생성</v-btn>
        <v-btn class="c-btn" rounded="lg">취소</v-btn>
      </v-sheet>
    </v-form>
    <v-btn @click="test">테스트</v-btn>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { instance } from '@/api/index'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

const rules = ref([
  (value) => {
    return (
      !value || !value.length || value[0].size < 2000000 || 'Avatar size should be less than 2 MB!'
    )
  }
])
const studyName = ref('')
const studyNameRules = ref([
  (value) => (!!value && value.length <= 50) || '스터디 이름을 입력해주세요. (최대 50자)'
])
const studyCategory = ref('')

const memberName = ref('')
const members = ref([])
const selectedMember = ref('')
const selectedMembers = ref([])
const searchMembers = async () => {
  if (!memberName.value.trim()) {
    members.value = []
    return
  }
  const response = await fetch(`https://api.github.com/search/repositories?q=${memberName.value}`)
  const data = await response.json()
  members.value = data.items
  console.log(members)
}

const tags = ref(['월', '화', '수', '목', '금', '토', '일'])
const selectedDay = ref([])
// 요일 보낼때 1010101 이런식으로 보내야 해서 만든 함수
function convertTagsToBinaryString() {
  let binaryString = ''
  for (let tag of tags.value) {
    binaryString += selectedDay.value.includes(tag) ? '1' : '0'
  }
  console.log(binaryString)

  return binaryString
}

const startDay = ref(new Date())
const endDay = ref(new Date())
const startTime = ref(new Date())
const endTime = ref(new Date())

const studyDescription = ref('')

// 필수 입력 조건 확인 후 알림 표시 또는 생성 함수호출
function checkForm() {
  if (!studyName.value) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 제목은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else if (selectedDay.value.length === 0) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 요일은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else if (!startDay.value) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 시작일은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else if (!endDay.value) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 종료일은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else if (!startTime.value) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 시작 시간은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else if (!endTime.value) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 종료 시간은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else {
    createStudy()
  }
}

// 아래는 스터디 생성 함수들임

function createStudy() {
  // FormData 객체 생성
  const formData = new FormData()

  // FormData 객체에 데이터 추가
  formData.append('title', studyName.value)
  formData.append('description', studyDescription.value)
  formData.append('day', convertTagsToBinaryString())
  formData.append('startTime', startTime.value)
  formData.append('endTime', endTime.value)

  // 파일 업로드를 위한 FormData 추가
  const fileInput = document.querySelector('#backgroundImage')
  formData.append('backgroundImage', fileInput.files[0])

  console.log(formData)
  // API 요청 보내기
  instance
    .post('api/studies/new', formData)
    .then((response) => {
      // 스터디 생성이 성공하면 알림 표시
      Swal.fire({
        title: '스터디 생성됨!',
        text: '스터디가 성공적으로 생성되었습니다.',
        icon: 'success',
        confirmButtonText: '확인'
      })
    })
    .catch((error) => {
      // 스터디 생성에 실패하면 알림 표시
      Swal.fire({
        title: '오류',
        text: '스터디 생성 중 오류가 발생했습니다.',
        icon: 'error',
        confirmButtonText: '확인'
      })
    })
}

const test = () => {
  console.log(startTime)
  console.log(endTime)
  console.log(startDay)
  console.log(endDay)
}
</script>

<style scoped>
.page {
  width: 1441px;
}
.m-btn {
  background-color: #3fb1fa;
  color: white;
}
.c-btn {
  background-color: rgba(255, 52, 64, 0.74);
  color: white;
}

#date,
#time {
  width: 300px;
  height: 51px;
  border: 1px solid #8d9299;
  border-radius: 10px;
  padding: 10px;
}
</style>
