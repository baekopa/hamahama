import torch
from whisper import load_model
from pyannote.audio import Pipeline
# import soundfile as sf
import torchaudio
from torchaudio.transforms import Resample
import ffmpeg
import re, glob, os
import speechbrain


def load_models():
    device = "cuda" if torch.cuda.is_available() else "cpu"
    whisper_model = load_model("small", device=device)
    return whisper_model

def set_pipeline():
    token = os.getenv("STT_TOKEN")
    diarization_pipeline = Pipeline.from_pretrained("pyannote/speaker-diarization@2.1", use_auth_token=token)
    print(diarization_pipeline)
    return diarization_pipeline

def convert_audio_ffmpeg(input_path, output_path, sample_rate=16000):
    ffmpeg.input(input_path).output(output_path, ar=sample_rate, ac=1).run()

def convert_audio_sample_rate(input_path, output_path, new_sample_rate):
    # 오디오 파일 로드
    waveform, original_sample_rate = torchaudio.load(input_path)
    # Resample transform 생성
    resampler = Resample(original_sample_rate, new_sample_rate)
    # 오디오 데이터를 새로운 샘플링 레이트로 리샘플링
    resampled_waveform = resampler(waveform)
    # 변환된 오디오 데이터를 파일로 저장
    torchaudio.save(output_path, resampled_waveform, new_sample_rate)

def millisec(timeStr):
  spl = timeStr.split(":")
  s = (int)((int(spl[0]) * 60 * 60 + int(spl[1]) * 60 + float(spl[2]) )* 1000)
  return s

# 결과 문자열에서 시간 부분을 제거하는 함수
def remove_time_from_text(text):
    # 정규 표현식을 사용하여 대괄호([]) 안의 내용과 대괄호를 모두 찾아서 제거합니다.
    return re.sub(r'\[\d{2}:\d{2}\.\d{3} --> \d{2}:\d{2}\.\d{3}\]\s*', '', text)

def clean_up_files(pattern):
    files = glob.glob(pattern)
    for f in files:
        try:
            os.remove(f)
            print(f"{f} has been removed")
        except OSError as e:
            print(f"Error: {f} : {e.strerror}")