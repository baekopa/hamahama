# resources.py
from contextlib import asynccontextmanager
import torch
from whisper import load_model
from pyannote.audio import Pipeline
import os

@asynccontextmanager
async def app_lifespan(app):
    device = "cuda" if torch.cuda.is_available() else "cpu"
    whisper_model = load_model("small", device=device)
    token = os.getenv("STT_TOKEN")
    diarization_pipeline = Pipeline.from_pretrained("pyannote/speaker-diarization@2.1", use_auth_token=token)
    print("모델 및 파이프라인 로드 완료.")

    app.state.whisper_model = whisper_model
    app.state.diarization_pipeline = diarization_pipeline

    yield  # FastAPI 애플리케이션이 요청을 처리하는 동안 여기에서 대기합니다.

    # 필요한 경우 여기에서 리소스 정리 로직을 추가합니다.
    print("애플리케이션 종료. 리소스 정리 완료.")
