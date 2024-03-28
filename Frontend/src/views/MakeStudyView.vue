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
          <v-sheet @click="openFileInput" border width="300" height="300" class="mr-10">
            <input
              type="file"
              @change="previewImage"
              accept="image/png, image/jpeg, image/bmp"
              style="display: none"
              ref="fileInput"
            />
            <img
              v-if="imageUrl"
              :src="imageUrl"
              alt="이미지 미리보기"
              style="width: 400px; height: 300px"
            />
            <button>이미지 선택</button>
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
              <div>
                <label for="memberName">멤버 이름:</label>
                <input
                  class="border"
                  type="text"
                  id="memberName"
                  v-model="memberName"
                  @input="searchMembers"
                />
              </div>

              <div v-if="members.length > 0" class="mt-5">
                <span>검색 결과:</span>
                <ul>
                  <li v-for="(member, index) in members" :key="index" @click="selectMember(member)">
                    {{ member.name }}
                  </li>
                </ul>
              </div>

              <div class="mt-5">
                <span>선택된 멤버:</span>
                <ul>
                  <li
                    v-for="(member, index) in selectedMembersName"
                    :key="index"
                    @click="toggleMemberSelection(member)"
                  >
                    {{ member.name }}
                  </li>
                </ul>
              </div>
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
              <input id="date" type="date" v-model="startDate" />
            </v-col>
          </v-row>
          <span class="text-h6 font-weight-black">스터디 종료일</span>
          <v-row class="ml-16">
            <v-col cols="auto">
              <input id="date" type="date" v-model="endDate" />
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
            v-model="studyDescription"
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
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import instance from '@/api/index'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

const router = useRouter()

const imageUrl = ref(null)
const fileInput = ref(null) // fileInput을 ref로 정의

const previewImage = (event) => {
  const file = event.target.files[0]
  if (!file) return

  // FileReader 객체 생성
  const reader = new FileReader()

  // 파일을 읽어들이면 호출되는 콜백 함수 정의
  reader.onload = (e) => {
    // 읽어들인 이미지 데이터를 imageUrl에 할당하여 미리보기
    imageUrl.value = e.target.result
  }

  // 파일을 읽어들임
  reader.readAsDataURL(file)
}

const openFileInput = () => {
  // 파일 선택 input 열기
  fileInput.value.click()
}

const studyName = ref('')
const studyNameRules = ref([
  (value) => (!!value && value.length <= 50) || '스터디 이름을 입력해주세요. (최대 50자)'
])
const studyCategory = ref('')

const memberName = ref('')
const members = ref([])
const selectedMembers = ref([])
const selectedMembersName = ref([])
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
  }
  console.log(selectedMembers.value)
}
const toggleMemberSelection = (member) => {
  const index = selectedMembers.value.findIndex((m) => m.id === member.id)
  if (index !== -1) {
    // 이미 선택된 멤버인 경우 선택 해제
    selectedMembers.value.splice(index, 1)
    selectedMembersName.value.splice(index, 1)
    console.log(selectedMembers)
  } else {
    // 선택되지 않은 멤버인 경우 선택
    selectedMembers.value.push(member.memberId)
  }
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

const startDate = ref(new Date().toISOString().substr(0, 10)) // 오늘의 날짜를 YYYY-MM-DD 형식으로 설정
const endDate = ref(new Date().toISOString().substr(0, 10)) // 오늘의 날짜를 YYYY-MM-DD 형식으로 설정
const startTime = ref('00:00') // 기본 시작 시간 설정
const endTime = ref('23:59') // 기본 종료 시간 설정

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
  } else if (!startDate.value) {
    Swal.fire({
      title: '입력 오류',
      text: '스터디 시작일은 필수 입력 항목입니다.',
      icon: 'error',
      confirmButtonText: '확인'
    })
  } else if (!endDate.value) {
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
// 스터디 생성 함수
function createStudy() {
  // FormData 객체 생성
  const formData = new FormData()
  formData.append('title', studyName.value)
  formData.append('description', studyDescription.value)
  formData.append('category', studyCategory.value)
  formData.append('startDate', startDate.value)
  formData.append('endDate', endDate.value)
  formData.append('day', convertTagsToBinaryString())
  formData.append('startTime', startTime.value)
  formData.append('endTime', endTime.value)
  formData.append('members', selectedMembers.value)

  const image = fileInput.value
  // 파일이 선택되었는지 확인 후 FormData에 추가
  if (image.files.length > 0) {
    formData.append('backgroundImage', image.files[0])
  }

  instance
    .post('api/studies', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then((response) => {
      if (response.data.status === 201) {
        Swal.fire({
          title: '스터디 생성됨!',
          text: '스터디가 성공적으로 생성되었습니다.',
          icon: 'success',
          confirmButtonText: '확인'
        }).then((result) => {
          if (result.isConfirmed) {
            // 스터디 페이지로 이동
            router.push({ name: 'study', params: { id: response.data.data.studyId } })
          }
        })
      } else {
        Swal.fire({
          title: '오류',
          text: '스터디 생성 중 문제가 발생했습니다.',
          icon: 'error',
          confirmButtonText: '확인'
        })
      }
    })
    .catch((error) => {
      console.error('스터디 생성 오류:', error)
      Swal.fire({
        title: '오류',
        text: '스터디 생성 중 문제가 발생했습니다.',
        icon: 'error',
        confirmButtonText: '확인'
      })
    })
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
