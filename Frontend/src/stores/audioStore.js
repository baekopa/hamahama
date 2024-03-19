import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAudioStore = defineStore('audio', () => {
    const isRecording = ref(false);
    const audioUrl = ref(null);
  
    function setRecordingStatus(status) {
      isRecording.value = status;
    }
  
    function setAudioUrl(url) {
      audioUrl.value = url;
    }

  return { isRecording, audioUrl, setRecordingStatus, setAudioUrl }
})
