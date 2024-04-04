# resources.py
from contextlib import asynccontextmanager
import torch
from whisper import load_model
from pyannote.audio import Pipeline
import os

@asynccontextmanager
async def app_lifespan(app):
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    whisper_model = load_model("small", device=device)
    token = os.getenv("STT_TOKEN")
    diarization_pipeline = Pipeline.from_pretrained("pyannote/speaker-diarization@2.1", use_auth_token=token).to(device)

    app.state.whisper_model = whisper_model
    app.state.diarization_pipeline = diarization_pipeline

    yield
    print("애플리케이션 종료. 리소스 정리 완료.")
