import torch
from whisper import load_model
from pyannote.audio import Pipeline
# import soundfile as sf
import ffmpeg
import re, glob, os


def load_model():
    device = "cuda" if torch.cuda.is_available() else "cpu"
    whisper_model = load_model("small", device=device)
    return whisper_model

def set_pipeline(token):
    diarization_pipeline = Pipeline.from_pretrained("pyannote/speaker-diarization-3.1", use_auth_token=token)
    return diarization_pipeline

def convert_audio_ffmpeg(input_path, output_path, sample_rate=16000):
    ffmpeg.input(input_path).output(output_path, ar=sample_rate, ac=1).run()

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