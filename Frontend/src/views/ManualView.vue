<template>
  <v-app>
    <v-container fluid style="width:1300px;">
      <v-stepper class="border-0"
        :items="['공부하마', '같이하마', '미팅', '스터디 생성']"
      >
        <template v-slot:item.1>
          <v-card variant="text" style="height: 620px;">
            <div class="mt-5">
              <p class="ml-5 point-font text-2xl mb-5">공부하마 <span class="tossface">😎</span></p>
              <p class="ml-5 text-xl">공부한 내용을 <span class="point-color font-bold">노트</span> 정리 하세요.</p>
              <p class="ml-5 text-xl">노트의 <span class="point-color font-bold">요약</span>을 생성하고 미팅에 <span class="point-color font-bold">공유</span>하세요!</p>
              <div class="d-flex justify-center mt-10 border-2 rounded-md mx-5 border">
                <img src="@/assets/image/home/note.png" class="manual-image"/>
              </div>  
            </div>
          </v-card>
        </template>
        <template v-slot:item.2 >
          <div class="mt-5">
              <p class="ml-5 point-font text-2xl mb-5">같이하마 <span class="tossface">🤼</span></p>
              <p class="ml-5 text-xl">미팅을 생성하고 스터디 내용을 <span class="point-color font-bold">녹음</span> 하세요.</p>
              <p class="ml-5 text-xl">스터디원들이 제출한 노트와 생성된 <span class="point-color font-bold">전체 요약</span>을 보며 함께 공부하세요!</p>
              <div class="d-flex justify-center mt-10 border-2 rounded-md mx-5 border">
                <img src="@/assets/image/home/meeting.png" class="manual-image"/>
              </div>  
            </div>
        </template>
        <template v-slot:item.3>
          <div class="mt-5">
              <p class="ml-5 point-font text-2xl mb-5">미팅 요약 <span class="tossface">👩‍🏫</span></p>
              <p class="ml-5 text-xl"><span class="point-color font-bold">STT</span> 결과인 전문을 바탕으로 요약과 키워드를 생성해보세요.</p>
              <p class="ml-5 text-xl">생성된 산출물은 <span class="point-color font-bold">수정</span>도 가능해요!</p>
              <div class="d-flex justify-center mt-10 border-2 rounded-md mx-5 border">
                <img src="@/assets/image/home/afterMeeting.png" class="manual-image"/>
              </div>  
            </div>
        </template>
        <template v-slot:item.4>
          <div class="mt-5">
              <p class="ml-5 point-font text-2xl mb-5">스터디 생성 <span class="tossface">🤗</span></p>
              <p class="ml-5 text-xl">그럼 스터디 생성 해볼까요~?</p>
              <p class="ml-5 text-xl">그전에..! <v-chip class="ml-5 text-xl" @click="goLogin">로그인</v-chip></p>
              <div class="d-flex justify-center mt-10 border-2 rounded-md mx-5 border">
                <img src="@/assets/image/home/study.png" class="manual-image"/>
              </div>  
            </div>
        </template>
      </v-stepper>
    </v-container>
  </v-app>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()

const goLogin = function () {
  router.push({ name: 'login' })
}

const steps = [
  {
    color: 'red-lighten-2',
    icon: 'mdi-star',
    title: '1. 공부하마',
    contents: ['혼자 공부하는 것이 좋다면 공부하마를 이용해보세요!']
  },
  {
    color: 'purple-lighten-2',
    icon: 'mdi-book-variant',
    title: '2. 같이하마',
    contents: [
      '스터디 생성을 눌러 스터디룸을 만들어보세요',
      '같이 공부할 멤버를 초대해보세요',
      '스터디 일정을 정해서 추가해주세요',
      '스터디 시작 전 녹음 버튼을 눌러서 스터디 내용을 녹음해주세요'
    ]
  },
  {
    color: 'green-lighten-1',
    icon: 'mdi-airballoon',
    title: '3. 노트 생성/제출',
    contents: [
      '스터디 시작 전, 공부한 내용을 정리해서 노트에 적어봐요!',
      '노트를 다음 스터디 미팅에 제출해보세요!'
    ]
  },
  {
    color: 'indigo-lighten-2',
    icon: 'mdi-layers-triple',
    title: '4. 요약/리마인드 퀴즈',
    contents: [
      '스터디 녹음이 끝나면 잠시 기다려주세요',
      '텍스트 변환이 완료되면 알람이 와요',
      '요약 기능을 사용해서 스터디 내용을 요약해보세요',
      '공부한 내용을 바탕으로 퀴즈를 만들어보세요'
    ]
  }
]
</script>

<style scoped>

.manual-image {
  height: 450px;
  object-fit: cover;
  
}
</style>
