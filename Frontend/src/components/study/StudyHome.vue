<template>
  <div>
    <p>{{ studyStore.studyName }}</p>
    <br>
    <div>
      <v-col cols="12" md="4" sm="6">
        <v-btn rounded="xl" size="x-large" block @click="startRecording">녹음 시작</v-btn>
      </v-col>
    </div>
    <div>
      <v-col cols="12" md="4" sm="6">
        <v-btn rounded="xl" size="x-large" block @click="stopRecording">녹음 중지</v-btn>
      </v-col>
    </div>
    <!-- 녹음 시간 표시 -->
    <div v-if="recording">
      녹음 시간: {{ elapsedTime }}
    </div>
    <br>
    <div>
      <h1>다음 스터디 일정</h1>
      <p>스터디까지 남은 시간</p>
      <v-col>
        <v-img :width="300" aspect-ratio="16/9" :src="mainImage" cover></v-img>
      </v-col>
      
      
    </div>
    <br>
    <div>
      <h1>오늘의 노트</h1>
      <div>
        <h4>전체 요약</h4>
      </div>
      <div>
        <h4>응용 문제</h4>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref } from 'vue'
  import { useStudyStore } from '@/stores/study'
  import { useAudioStore } from '@/stores/audioStore';
  import axios from 'axios';
  import mainImage from '@/assets/image/home/main2.png';

  const studyStore = useStudyStore()
  const audioStore = useAudioStore();
  const mediaRecorder = ref(null);
  const audioChunks = ref([]);
  const recordText = ref('');
  const startTime = ref(null);
  const recording = ref(false);
  const elapsedTime = ref('00:00');
  const timer = ref(null);
  

  const updateElapsedTime = () => {
    const now = Date.now()
    const diff = now - startTime.value
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

  const stopRecording = () => {
    console.log("레코딩 멈춰 명령 실행");
    if (mediaRecorder.value) {
      console.log("녹음파일 있으니 녹음 중지할게요");
      mediaRecorder.value.stop();
      mediaRecorder.value.stream.getTracks().forEach(track => track.stop()); // 스트림의 모든 트랙을 멈춤. 마이크 종료
      clearInterval(timer.value)
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
</script>