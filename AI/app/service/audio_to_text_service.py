import torch
from whisper import load_model
from pyannote.audio import Pipeline
# import soundfile as sf
import torchaudio
from tempfile import NamedTemporaryFile
from torchaudio.transforms import Resample
import ffmpeg
import re, glob, os
from pyannote.audio import Pipeline
from pydub import AudioSegment
from fastapi import HTTPException


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

async def speech_to_text(model, pipeline, file):
    if not file.filename.endswith('.wav'):
        raise HTTPException(status_code=400, detail="Only WAV files are supported.")
    
    # 임시 파일에 오디오 저장
    with NamedTemporaryFile(delete=False, suffix=".wav") as tmp:
        content = await file.read()
        print(tmp)
        tmp.write(content)
        tmp_filename = tmp.name

    # # ffmpeg로 wav 변환
    converted_filename = tmp_filename.replace(".wav", "_converted.wav")
    convert_audio_ffmpeg(tmp_filename, converted_filename)
    # convert_audio_sample_rate(tmp_filename, converted_filename, new_sample_rate=16000)

    # 화자 분할
    diarization = pipeline(converted_filename)
    # diarization = diarization_pipeline(tmp_filename)
    
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
    
    # 화자 분할된 데이터를 JSON 형식으로 준비
    transcription_data = {
        "transcriptions": [
            # 각 화자의 발화 데이터를 여기에 추가
            # 예: {"speaker": "1", "text": "여기는 화자 1의 발화입니다."},
        ]
    }

    for i in range(gidx+1):
        # 오디오 파일 이름 구성
        audio_file = f"{i}.wav"
    
        result = model.transcribe(audio_file, language="ko")
        transcribed_text = result["text"]

        print("result 나옴 : ", result)
        # cleaned_text = remove_time_from_text(result.stdout)
        cleaned_text = remove_time_from_text(transcribed_text)
        print("remove text 성공")
        transcription_data["transcriptions"].append({"speaker": speaker[i], "text": cleaned_text})

        # 결과 출력
        print(speaker[i]," : ", cleaned_text)

        clean_up_files(f"{i}.*")
            
    clean_up_files("diarization.txt")

    return transcription_data
