from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
import whisper
from concurrent.futures import ThreadPoolExecutor
import asyncio
import shutil
import os
from tempfile import NamedTemporaryFile
import logging
import requests

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

# CORS 미들웨어 추가
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  # Vue 앱의 URL, 배포 시 실제 도메인으로 변경
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용 (GET, POST 등)
    allow_headers=["*"],  # 모든 HTTP 헤더 허용
)

# 모델 로드는 애플리케이션 시작 시 한 번만 수행
model = whisper.load_model("medium")

# ThreadPoolExecutor 인스턴스 생성
executor = ThreadPoolExecutor(max_workers=1)


@app.post("/transcribe/")
async def transcribe_audio(file: UploadFile = File(...)):
    logger.info("Transcription request received")
    try:
        # 임시 파일 생성 및 저장
        with NamedTemporaryFile(delete=False) as tmp_file:
            shutil.copyfileobj(file.file, tmp_file)
            tmp_file_path = tmp_file.name
        logger.info(f"Temporary file saved at {tmp_file_path}")

        # 오디오 파일 로드 및 처리를 위한 비동기 함수 호출
        result = await asyncio.get_event_loop().run_in_executor(executor, process_audio, tmp_file_path)
        print(result)
        logger.info("Transcription completed successfully")

        send_to_spring_boot(result)
        # 결과 반환
        return JSONResponse(content={"detected_language": result['language'], "text": result['text']})

    except Exception as e:
        logger.error(f"An error occurred: {e}")
        raise HTTPException(status_code=500, detail="An error occurred during transcription.")

    finally:
        # 임시 파일이 생성되었다면 삭제
        if tmp_file_path and os.path.exists(tmp_file_path):
            os.remove(tmp_file_path)


def process_audio(tmp_file_path):
    # 오디오 파일 로드
    audio = whisper.load_audio(tmp_file_path)
    audio = whisper.pad_or_trim(audio)  # 오디오를 패딩하거나 잘라서 모델에 맞는 길이로 조정

    # log-Mel 스펙트로그램 생성 및 모델이 있는 같은 디바이스로 이동
    mel = whisper.log_mel_spectrogram(audio).to(model.device)

    # 말하는 언어 감지 및 텍스트 디코드
    _, probs = model.detect_language(mel)
    detected_language = max(probs, key=probs.get)
    options = whisper.DecodingOptions(temperature=0.2)
    result = whisper.decode(model, mel, options)

    return {"language": detected_language, "text": result.text}

def send_to_spring_boot(result):
    url = 'http://localhost:8080/audio'  # Spring Boot 애플리케이션 URL
    response = requests.post(url, json=result)
    if response.status_code == 200:
        print("데이터가 성공적으로 Spring Boot 서버에 저장되었습니다.")
    else:
        print("오류가 발생했습니다:", response.status_code)