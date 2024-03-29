import os, re, subprocess
from whisper import transcribe
from fastapi import APIRouter, FastAPI, UploadFile, File, HTTPException, Path
from fastapi.responses import JSONResponse
from pyannote.audio import Pipeline
from pydub import AudioSegment
from tempfile import NamedTemporaryFile
from model.request_dto import OriginalText, QuizRequest
from model.response_dto import SummaryDTO, KeywordDTO, QuizDTO, TailQuestionDTO
from service.text_processing import process_text, process_for_remind_quiz
from service.summary_pre_service import do_summary
from service.keyword_quiz_pre_service import do_keyword, do_quiz
from service.tail_question_service import do_tail_question
from service.audio_to_text_service import load_models, set_pipeline, convert_audio_ffmpeg, millisec, remove_time_from_text, clean_up_files


router=APIRouter(
    prefix="/studies",
    # tags=["요약, 키워드, 퀴즈 생성"]
)

@router.post("/summary", tags=["요약 생성"], response_model=SummaryDTO)
async def summary_text(origin_dto: OriginalText):
    origin_text_list = process_text(origin_dto.originalText)
    
    output = do_summary(origin_text_list)
    return SummaryDTO(originalText=origin_dto.originalText, summaryText=output)

@router.post("/keyword",  tags=["키워드"], response_model=KeywordDTO)
async def keyword_text(origin_dto: OriginalText):
    origin_text_list = process_text(origin_dto.originalText)
    
    keyword_list = do_keyword(origin_text_list)
    return KeywordDTO(keyword=keyword_list)

@router.post("/quiz", tags=["리마인드 퀴즈"], response_model=QuizDTO)
async def quiz_text(origin_dto: QuizRequest):
    summary_text_for_quiz= process_for_remind_quiz(origin_dto.summaryText)
    
    quiz_created = do_quiz(summary_text_for_quiz)
    quiz_created=str(quiz_created)
    return QuizDTO(quiz=quiz_created)

@router.post("/tailquestion", tags=["꼬리 질문"], response_model=TailQuestionDTO)
async def tail_question_text(origin_dto: OriginalText):
    
    tail_question = do_tail_question(origin_dto.original_text)
    return TailQuestionDTO(original_text=origin_dto.original_text, tail_question=tail_question)



@router.post("/transcribe/{study_id}/{meeting_id}", tags=["STT"])
async def transcribe_audio(study_id: int = Path(...), meeting_id: int = Path(...), file: UploadFile = File(...)):

    model = load_models()
    token = os.getenv("STT_TOKEN")
    diarization_pipeline = set_pipeline(token=token)
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
        # logger.info(d)
        # logger.info(g)
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
        
        # Whisper 명령 구성
        whisper_command = [
            "whisper", 
            audio_file, 
            "--language", "ko", 
            "--model", "small"
        ]
        
        # Whisper 명령 실행
        result = subprocess.run(whisper_command, capture_output=True, text=True, encoding='utf-8')
        # try:
        #     result = model.transcribe(audio_file, language="ko")
        #     transcribed_text = result["text"]
        # finally:
        #     # 사용이 끝난 임시 파일 삭제
        #     os.remove(tmp_filename)
        
        cleaned_text = remove_time_from_text(result.stdout)
        transcription_data["transcriptions"].append({"speaker": speaker[i], "text": cleaned_text})
        print(result)
        # 결과 출력
        print(speaker[i]," : ", cleaned_text)

        clean_up_files(f"{i}.*")

        # 에러 메시지가 있는 경우 출력
        if result.stderr:
            print(result.stderr)
            
    clean_up_files("diarization.txt")
    print(transcription_data)
    print(study_id, meeting_id)
    
    return JSONResponse(content=transcription_data["transcriptions"])