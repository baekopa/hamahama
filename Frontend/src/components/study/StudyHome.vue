<template>
  <div>
    <p>{{ studyStore.studyName }}</p>

    <br>
    <div>
      <h1>다음 스터디 일정</h1>
      <p>스터디까지 남은 시간 </p>
      <v-row>
        <!-- 이미지 컴포넌트를 위한 컬럼 -->
        <v-col cols="5">
          <v-img :width="300" aspect-ratio="16/9" :src="mainImage" cover></v-img>
        </v-col>

        <v-col cols="7">
          <p>다음 일정의 제목</p>
        </v-col>

        <v-col cols="11" v-if="!recording">
          <v-btn class="gradient-btn" block rounded="xl" size="large" @click="startRecording">스터디 시작</v-btn>
        </v-col>

        <v-col cols="11" v-if="recording && !paused">
          <v-btn class="gradient-btn" rounded="xl" size="large" block @click="pauseRecording">녹음 일시정지</v-btn>
        </v-col>

        <v-col cols="11" v-if="recording && paused">
          <v-btn class="gradient-btn" rounded="xl" size="large" block @click="resumeRecording">녹음 재개</v-btn>
        </v-col>
        
        <v-col cols="11" v-if="recording">
          <v-btn class="gradient-btn" rounded="xl" size="large" block @click="stopRecording">녹음 중지</v-btn>
        </v-col>
      </v-row>
      <!-- 녹음 시간 표시 -->
      <div v-if="recording">
        녹음 시간: {{ elapsedTime }}
      </div>
      
    </div>
    <br>
    <div>
      <h1>오늘의 노트</h1>
      <v-col>
        <h4>전체 요약</h4>
        <p>요약입니하마</p>
      </v-col>
      <v-col>
        <h4>응용 문제</h4>
        <p>문제입니하마</p>
      </v-col>
    </div>
  </div>
</template>

<style>
.gradient-btn {
  background: linear-gradient(to right, rgb(19, 143, 214), rgb(3, 240, 229));
  color: white; /* 텍스트 색상을 흰색으로 설정 */
}
</style>

<script setup>
  import { ref } from 'vue'
  import { useStudyStore } from '@/stores/study'
  import { useAudioStore } from '@/stores/audioStore';
  import axios from 'axios';
  import mainImage from '@/assets/image/home/main2.png';

  const studyStore = useStudyStore()
  const audioStore = useAudioStore();


  // --------------- 녹음 관련 변수와 함수 ------------------ //
  const mediaRecorder = ref(null);
  const audioChunks = ref([]);
  const recordText = ref('');
  const startTime = ref(null);
  const recording = ref(false);
  const elapsedTime = ref('00:00');
  const timer = ref(null);
  const paused = ref(false); // 일시정지 상태 관리를 위한 참조
  const pausedTime = ref(0);
  const totalPausedDuration = ref(0); 
  
  
  const updateElapsedTime = () => {
    const now = Date.now()
    const diff = now - startTime.value - totalPausedDuration.value; // 일시정지된 시간을 고려
    const minutes = Math.floor(diff / 60000)
    const seconds = Math.floor((diff % 60000) / 1000)

    elapsedTime.value = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
  }

  const startRecording = async () => {
    console.log("녹음이 시작됨");
    recording.value = true
    startTime.value = Date.now()
    updateElapsedTime()
    timer.value = setInterval(updateElapsedTime, 1000)
    if (navigator.mediaDevices.getUserMedia) {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      mediaRecorder.value = new MediaRecorder(stream);
      audioChunks.value = [];
      mediaRecorder.value.ondataavailable = event => {
        audioChunks.value.push(event.data);
      };
      mediaRecorder.value.start();
      audioStore.setRecordingStatus(true);
    } else {
      alert("오디오 녹음을 지원하지 않는 브라우저입니다.");
    }
  };

  const pauseRecording = () => {
    if (mediaRecorder.value && recording.value && !paused.value) {
      mediaRecorder.value.pause();
      clearInterval(timer.value);
      paused.value = true;
      pausedTime.value = Date.now(); // 일시정지 시작 시간 저장
      console.log("녹음이 일시정지됨");
    }
  };

  const resumeRecording = () => {
    if (mediaRecorder.value && paused.value) {
      mediaRecorder.value.resume();
      const pausedDuration = Date.now() - pausedTime.value;
      totalPausedDuration.value += pausedDuration; // 총 일시정지 시간 업데이트
      timer.value = setInterval(updateElapsedTime, 1000);
      paused.value = false;
      console.log("녹음이 재개됨");
    }
  };


  const stopRecording = () => {
    console.log("레코딩 멈춰 명령 실행");
    if (mediaRecorder.value) {
      console.log("녹음파일 있으니 녹음 중지할게요");
      mediaRecorder.value.stop();
      mediaRecorder.value.stream.getTracks().forEach(track => track.stop()); // 스트림의 모든 트랙을 멈춤. 마이크 종료
      clearInterval(timer.value)
      pausedTime.value = 0;
      totalPausedDuration.value = 0; 
      recording.value = false
      mediaRecorder.value.onstop = async () => {
        const audioBlob = new Blob(audioChunks.value, { type: 'audio/wav' });
        console.log("업로드 함수 실행 직전");
        await uploadAudio(audioBlob);
        audioStore.setRecordingStatus(false);
      };
    } else {
      console.log("녹음 시작은 했니?");
    }
  };

  const uploadAudio = async (audioBlob) => {
    const formData = new FormData();
    formData.append("file", audioBlob, "recording.wav");
    console.log("트라이 직전")
    console.log(formData)
    // FastAPI 서버로 오디오 파일 전송
    try {
      console.log("post 간다!");
      const response = await axios.post("http://localhost:8000/", formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log("post끝");

      const data = response.data;
      console.log("Transcription result:", data);
      recordText.value = data.transcription;
      console.log(recordText.value)
    } catch (error) {
      console.error("오류가 발생했습니다:", error);
    }
  };
// --------------------------- 녹음  ---------------------------------------- //



</script>