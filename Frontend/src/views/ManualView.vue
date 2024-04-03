<template>
  <v-app>
    <v-container fluid>
      <v-stepper :items="['공부하마', '같이하마', '노트 생성 및 제출', '녹음', '요약', '리마인드 퀴즈']">
        <template v-slot:item.1>
          <v-card title="혼자 공부하는 것이 좋다면 공부하마를 이용해보세요!" flat>
            <img src='../assets/image/main/irean.jpg'>
          </v-card>
        </template>
        <template v-slot:item.2>
          <v-card title="스터디 생성을 눌러 스터디룸을 만들어보세요" flat>...</v-card>
          <v-card title="같이 공부할 멤버를 초대해보세요" flat>...</v-card>
          <v-card title="스터디 일정을 정해서 추가해주세요" flat>...</v-card>
        </template>
        <template v-slot:item.3>
          <v-card title="스터디 시작 전, 공부한 내용을 정리해서 노트에 적어봐요" flat>...</v-card>
          <v-card title="노트를 다음 스터디 미팅에 제출해보세요" flat>...</v-card>
        </template>
        <template v-slot:item.4>
          <v-card title="녹음 버튼을 눌러서 스터디 내용을 녹음해주세요" flat>...</v-card>
          <v-card title="스터디 녹음이 끝나면 잠시 기다려주세요" flat>...</v-card>
          <v-card title="텍스트 변환이 완료되면 알람이 와요" flat>...</v-card>
        </template>
        <template v-slot:item.5>
          <v-card title="요약 기능을 사용해서 스터디 내용을 요약해보세요" flat>...</v-card>
        </template>
        <template v-slot:item.6>
          <v-card title="공부한 내용을 바탕으로 퀴즈를 만들어보세요" flat>...</v-card>
        </template>
      </v-stepper>
    </v-container>
  </v-app>
</template>

<script setup>
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
    contents: ['스터디 생성을 눌러 스터디룸을 만들어보세요',
      '같이 공부할 멤버를 초대해보세요',
      '스터디 일정을 정해서 추가해주세요',
      '스터디 시작 전 녹음 버튼을 눌러서 스터디 내용을 녹음해주세요'
    ]
  },
  {
    color: 'green-lighten-1',
    icon: 'mdi-airballoon',
    title: '3. 노트 생성/제출',
    contents: ['스터디 시작 전, 공부한 내용을 정리해서 노트에 적어봐요!', '노트를 다음 스터디 미팅에 제출해보세요!']
  },
  {
    color: 'indigo-lighten-2',
    icon: 'mdi-layers-triple',
    title: '4. 요약/리마인드 퀴즈',
    contents: ['스터디 녹음이 끝나면 잠시 기다려주세요',
      '텍스트 변환이 완료되면 알람이 와요',
      '요약 기능을 사용해서 스터디 내용을 요약해보세요',
      '공부한 내용을 바탕으로 퀴즈를 만들어보세요'
    ]
  }
]
</script>

<style scoped></style>
