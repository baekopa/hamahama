from fastapi import FastAPI, UploadFile, File, HTTPException
from fastapi.responses import JSONResponse
import torch
import whisper
from whisper import load_model
from pyannote.audio import Pipeline
# import soundfile as sf
from tempfile import NamedTemporaryFile
from fastapi.middleware.cors import CORSMiddleware
import logging
import ffmpeg
import re
from pydub import AudioSegment
import subprocess

token = "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"

app = FastAPI()

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# CORS 미들웨어 추가
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  # Vue 앱의 URL, 배포 시 실제 도메인으로 변경
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용 (GET, POST 등)
    allow_headers=["*"],  # 모든 HTTP 헤더 허용
)

# 설정: 장치, 모델 및 파이프라인 초기화
print(torch.cuda.is_available())
device = "cuda" if torch.cuda.is_available() else "cpu"
whisper_model = load_model("large", device=device)

try:
    logger.error("파이프리인 생선 전")
    diarization_pipeline = Pipeline.from_pretrained("pyannote/speaker-diarization-3.1", use_auth_token=token)
    logger.error("파이프리인 생선 후")
except Exception as e:
    logger.error(f"파이프라인 초기화 중 오류 발생: {e}")

def convert_audio_ffmpeg(input_path, output_path, sample_rate=16000):
    ffmpeg.input(input_path).output(output_path, ar=sample_rate, ac=1).run()

def millisec(timeStr):
  spl = timeStr.split(":")
  s = (int)((int(spl[0]) * 60 * 60 + int(spl[1]) * 60 + float(spl[2]) )* 1000)
  return s


@app.post("/transcribe")
async def transcribe_audio(file: UploadFile = File(...)):
    if not file.filename.endswith('.wav'):
        raise HTTPException(status_code=400, detail="Only WAV files are supported.")
    
    # 임시 파일에 오디오 저장
    with NamedTemporaryFile(delete=False, suffix=".wav") as tmp:
        content = await file.read()
        tmp.write(content)
        tmp_filename = tmp.name

    # ffmpeg로 wav 변환
    converted_filename = tmp_filename.replace(".wav", "_converted.wav")
    convert_audio_ffmpeg(tmp_filename, converted_filename)
    
    # 화자 분할
    diarization = diarization_pipeline(converted_filename)
    
    with open("diarization.txt", "w") as text_file:
        text_file.write(str(diarization))

    print(*list(diarization.itertracks(yield_label = True))[:10], sep="\n")

    dzs = open('diarization.txt').read().splitlines()

    groups = []
    g = []
    lastend = 0

    for d in dzs:   
        if g and (g[0].split()[-1] != d.split()[-1]):      #same speaker
            groups.append(g)
            g = []
    
        g.append(d)
    
        end = re.findall('[0-9]+:[0-9]+:[0-9]+\.[0-9]+', string=d)[1]
        end = millisec(end)
        if (lastend > end):       #segment engulfed by a previous segment
            groups.append(g)
            g = [] 
        else:
            lastend = end

    if g:
        groups.append(g)

    print(*groups, sep='\n')
    
    audio = AudioSegment.from_wav(converted_filename)

    speaker = []
    gidx = -1
    for g in groups:
        print("g 값 : ", g)
        print("발화자 번호", g[-1][-1])
        speaker.append(g[-1][-1])
        start = re.findall('[0-9]+:[0-9]+:[0-9]+\.[0-9]+', string=g[0])[0]
        end = re.findall('[0-9]+:[0-9]+:[0-9]+\.[0-9]+', string=g[-1])[1]
        start = millisec(start) #- spacermilli
        end = millisec(end)  #- spacermilli
        print("start, end :", start, end)
        gidx += 1
        audio[start:end].export(str(gidx) + '.wav', format='wav')
    
    print(speaker)
    for i in range(gidx+1):
        # 오디오 파일 이름 구성
        audio_file = f"{i}.wav"
        
        # Whisper 명령 구성
        whisper_command = [
            "whisper", 
            audio_file, 
            "--language", "ko", 
            "--model", "large"
        ]
        
        # Whisper 명령 실행
        result = subprocess.run(whisper_command, capture_output=True, text=True, encoding='utf-8')
        
        print(result)
        # 결과 출력
        print(speaker[i]," : ", result.stdout)

        # 에러 메시지가 있는 경우 출력
        if result.stderr:
            print(result.stderr)

    return 

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
