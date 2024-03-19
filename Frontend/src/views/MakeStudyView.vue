<template>
  <v-container class="page">
    <div class="d-flex align-center">
      <img src="@/assets/image/study/rocketIcon.svg" alt="로켓" width="73px" />
      <span class="text-h4 font-weight-black ml-4">스터디 만들기</span>
    </div>
    <v-form fast-fail @submit.prevent>
      <v-sheet elevation="3" rounded="lg" class="basic-form mt-10 d-flex flex-column">
        <div class="pa-10">
          <span class="text-h5 font-weight-black">스터디 기본 정보</span>
        </div>

        <v-col class="pa-10">
          <v-file-input
            id="backgroundImage"
            :rules="rules"
            accept="image/png, image/jpeg, image/bmp"
            label="스터디 배경사진"
            style="height: 344px"
          ></v-file-input>

          <span>스터디 제목</span>
          <v-text-field
            v-model="studyName"
            :rules="studyNameRules"
            variant="outlined"
            label="50자 내외로 스터디 이름을 지어주세요"
          ></v-text-field>

          <span>스터디 멤버 초대</span>
          <v-text-field
            v-model="memberName"
            :rules="lastNameRules"
            variant="outlined"
            label="Last name"
          ></v-text-field>
          <v-text-field
            v-model="studyCategory"
            :rules="studyCategoryRules"
            label="Last name"
          ></v-text-field>
        </v-col>
      </v-sheet>

      <v-sheet elevation="3" rounded="lg" class="detail-schedule mt-10 pa-10 d-flex flex-column">
        <div class="d-flex align-center pa-">
          <img src="@/assets/image/study/calendar.svg" alt="" width="73px" height="73px" />
          <span class="text-h5 font-weight-black">상세일정</span>
        </div>
        <div>
          <span>스터디 요일</span>
          <v-row justify="space-around">
            <v-col cols="auto">
              <v-sheet class="py-4 px-1" elevation="">
                <v-chip-group
                  @click="convertTagsToBinaryString"
                  v-model="selectedDay"
                  selected-class="text-primary"
                  multiple
                >
                  <v-chip v-for="tag in tags" :key="tag" :value="tag">
                    {{ tag }}
                  </v-chip>
                </v-chip-group>
              </v-sheet>
            </v-col>
          </v-row>
        </div>
        <v-row justify="space-around">
          <v-col cols="12" md="6">
            <div class="mt-10">
              <span>스터디 시작일</span>
              <input type="date" />
            </div>
          </v-col>
        </v-row>

        <v-row class="">
          <v-col cols="12" md="6">
            <div class="mt-10">
              <span class="">스터디 종료일</span>
              <input type="date" />
            </div>
          </v-col>
        </v-row>

        <v-row justify="space-around">
          <v-col cols="12" md="6">
            <div class="mt-10">
              <span>스터디 시간</span>

              <input type="time" />
              <input type="time" />
            </div>
          </v-col>
        </v-row>
      </v-sheet>

      <v-sheet elevation="3" rounded="lg" class="study-description mt-10 pa-5 d-flex flex-column">
        <div class="d-flex align-center">
          <img src="@/assets/image/study/pinIcon.svg" alt="" width="50px" />
          <span class="text-h5 font-weight-black">스터디 설명</span>
        </div>
        <v-sheet class="pa-10">
          <v-textarea
            rounded="lg"
            v-bind="studyDescription"
            no-resize
            label="스터디에 대한 정보를 적어주세요"
            variant="outlined"
            class="mt-5"
          ></v-textarea>
        </v-sheet>
      </v-sheet>

      <v-sheet class="d-flex justify-end mt-5">
        <v-btn @click="checkForm" class="m-btn mr-5" rounded="lg" type="submit">스터디 생성</v-btn>
        <v-btn class="c-btn" rounded="lg">취소</v-btn>
      </v-sheet>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { apiInstance } from '@/api/index'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

const rules = ref([
  (value) => {
    console.log(value)
    return (
      !value || !value.length || value[0].size < 2000000 || 'Avatar size should be less than 2 MB!'
    )
  }
])

const studyNameRules = ref([
  (value) => (!!value && value.length <= 50) || '스터디 이름을 입력해주세요. (최대 50자)'
])

const startTime = ref(new Date())
const endTime = ref(new Date())

const studyName = ref('')
const tags = ref(['월', '화', '수', '목', '금', '토', '일'])

const selectedDay = ref([])

const studyDescription = ref('')

function convertTagsToBinaryString() {
  let binaryString = ''
  for (let tag of tags.value) {
    binaryString += selectedDay.value.includes(tag) ? '1' : '0'
  }
  console.log(binaryString)
  console.log(selectedDate)
  return binaryString
}

function checkForm() {
  // 필수 입력 조건 확인(스터디 제목, 스터디 요일, 스터디시작, 종료일, 설명)
  if (
    !studyName.value ||
    selectedDay.value.length === 0 ||
    !selectedDate.value ||
    !studyDescription.value
  ) {
    // 필수 입력 조건 중 하나라도 충족되지 않으면 알림 표시
    Swal.fire({
      title: '입력 오류',
      text: '스터디 제목, 스터디 요일, 스터디 시작일, 스터디 종료일, 스터디 설명은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else {
    // 모든 필수 입력 조건이 충족되면 스터디 생성 로직 수행
    createStudy()
  }
}

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

  // API 요청 보내기
  apiInstance
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
</script>

<style scoped>
.page {
  width: 1500px;
}
.m-btn {
  background-color: #3fb1fa;
  color: white;
}
.c-btn {
  background-color: rgba(255, 52, 64, 0.74);
  color: white;
}
</style>
