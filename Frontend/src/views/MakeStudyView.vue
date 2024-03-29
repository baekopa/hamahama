<template>
  <v-form fast-fail @submit.prevent>
  <div class="mb-32">
  <div class="bg-white d-flex flex-column items-center mt-15">
    <div class="d-flex flex-column" style="width: 1300px">    
      <div class="text-gray-500 point-font">
        <span class="text-xl mr-2"><</span><span class="tossface text-xl">👨‍👨‍👧‍👧</span> 스터디 생성
      </div>  
      <div class="note-title point-font mt-14"> 스터디 기본 정보 <span class="text-red-300">*</span></div>
      <div class="d-flex mt-3">
        <v-card @click="openFileInput" width="300" height="300" class="rounded-lg d-flex text-center justify-center text-xl mr-14" variant="outlined" color="blue">
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
            style="width: 300px; height: 300px"
          />
          <button v-else>이미지 선택</button>
        </v-card>
        <div class="d-flex flex-column justify-center">
          <input v-model="studyName" :rules="studyNameRules" variant="plain" placeholder="스터디 이름을 작성해주세요" class="note-title"/>
          <textarea v-model="studyDescription" variant="plain" placeholder="스터디 설명을 작성해주세요." class="note-content" rows="7" style="width: 900px;"></textarea>
        </div>
      </div>
    </div>
    <div class="d-flex flex-column mt-20" style="width: 1300px">
      <div class="note-title point-font"> 
        스터디원 초대
        <span class="italic text-gray-500 font-light text-base ml-2"></span>
        <div></div>
      </div>
      <v-row>
        <v-col cols="5">
          <div class="text-gray-500 font-light text-base ml-2 mb-2 text-xl">스터디원 검색</div>
          <input type="text" id="memberName" v-model="memberName" @input="searchMembers" variant="plain" placeholder=" 함께할 스터디원의 이메일을 입력해주세요." class="border note-content px-2 py-2 rounded-lg w-full" />
          <div class="mt-4 overflow-y-auto h-80 p-4 bg-gray-100 rounded-lg">
            <ul v-if="members.length > 0" class="text-xl">
              <li v-for="(member, index) in members" :key="index" @click="selectMember(member)" class="mb-4">
                <img :src="member.profileImage" class="w-8 inline rounded-full mr-2"/> {{ member.name }} <span class="text-gray-700 ml-2">{{ member.email }}</span>
              </li>
            </ul>
          </div>
        </v-col>
        <v-col cols="2" class="d-flex flex-column justify-center items-center text-2xl ">
          <div>></div>
          <div><</div>
        </v-col>
        <v-col cols="5">
          <div class="text-gray-500 font-light text-base ml-2  text-xl">초대 요청 대상</div>
          <div class="mt-2 overflow-y-auto p-4 h-96 bg-gray-100 rounded-lg">
            <ul v-if="members.length > 0" class="text-xl">
              <li v-for="(member, index) in selectedMembersName" :key="index" @click="toggleMemberSelection(member)" class="mb-2">
                <img :src="member.profileImage" class="w-8 inline rounded-full mr-2"/> {{ member.name }} <span class="text-gray-700 ml-2">{{ member.email }}</span>
              </li>
            </ul>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="d-flex flex-column mt-24" style="width: 1300px">     
      <div class="note-title point-font">스터디 날짜</div>
      <div class="d-flex mt-8 items-center text-xl">
        <div class="w-36 mr-7">스터디 진행 기간</div>
        <input id="date" type="date" v-model="startDate" class="border text-gray-500"/>
        <div class="mx-6">~</div>
        <input id="date" type="date" v-model="endDate" class="border text-gray-500" />
      </div>
      <div class="d-flex mt-7 items-center text-xl">
        <div class="w-36 mr-7">스터디 요일</div>
        <v-chip-group
          @click="convertTagsToBinaryString"
          v-model="selectedDay"
          selected-class="text-primary"
          multiple
        >
          <v-chip v-for="tag in tags" :key="tag" :value="tag" size="x-large" class="mx-4">
            {{ tag }}
          </v-chip>
        </v-chip-group>
      </div>
      <div class="d-flex mt-7 items-center text-xl">
        <div class="w-36 mr-7">스터디 시간</div>
        <input id="time" type="time" v-model="startTime" class="border text-gray-500" />
        <div class="mx-6">~</div>
        <input id="time" type="time" v-model="endTime" class="border text-gray-500" />
      </div>
    </div>
    <div class="d-flex flex-column mt-20" style="width: 1300px">
      <div class="note-title point-font mt-14"> 스터디 추가 정보</div>
      <input v-model="studyCategory" variant="plain" placeholder="스터디 주제를 작성해주세요." class="my-3 note-content" />
    </div>
    <div class="d-flex justify-end mt-40" style="width: 1300px">
      <v-btn @click="checkForm" size="large" class="" variant="flat" color="#3fb1fa" rounded="xl">스터디 생성</v-btn>
      <v-btn size="large" class="mx-5" variant="flat" color="#FF6B74" rounded="xl">취소</v-btn>
    </div>
  </div>
</div>
</v-form>
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

.text-area {
  border-radius: 10px;
  padding: 4px;
  margin: 20px;
  overflow-y: auto;
  max-height: 600px;
}
.study-select {
  margin-top: 50px;
}

.summary-content {
  width: 1300px;
  height: 400px;
  border: solid 1px black;
}

.note-title {
  font-size: x-large;
  outline: none;
  margin: 20px 0px;
  font-weight: bold;
}

.note-content {
  font-size: large;
  outline: none;
  /* line-height: 30px; */
}

.profile-img {
  width: 40px;
  border-radius: 50%;
}

.search-input {
  border: 1px;
  border-bottom:#3fb1fa;
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
  background-color: #dbdbdb;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #afafaf;
}
</style>
