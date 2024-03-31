import os, re, subprocess, speechbrain
from whisper import transcribe
from fastapi import APIRouter, FastAPI, UploadFile, File, HTTPException, Path
from fastapi.responses import JSONResponse
from pyannote.audio import Pipeline
from pydub import AudioSegment
from tempfile import NamedTemporaryFile
from model.request_dto import OriginalText, QuizRequest, OriginalTextList
from model.response_dto import SummaryDTO, KeywordDTO, QuizDTO, TailQuestionDTO, UniquificationDTO
from service.text_processing import process_text, process_for_remind_quiz
from service.summary_pre_service import do_summary
from service.keyword_quiz_pre_service import do_keyword, do_quiz
from service.tail_question_service import do_tail_question
from service.audio_to_text_service import load_models, set_pipeline, convert_audio_ffmpeg, millisec, remove_time_from_text, clean_up_files, convert_audio_sample_rate, speech_to_text
from service.uniquification_service import do_uniquification



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
    
    tail_question = do_tail_question(origin_dto.originalText)
    return TailQuestionDTO(tailQuestion=tail_question)
    
@router.post("/uniquification", tags=["요약 중복 제거"], response_model=UniquificationDTO)
async def tail_question_text(origin_dto: OriginalTextList):
    
    uniquification = do_uniquification(origin_dto.submittedNoteList)
    return UniquificationDTO(uniquification=uniquification)



@router.post("/transcribe/{study_id}/{meeting_id}", tags=["STT"])
async def transcribe_audio(study_id: int = Path(...), meeting_id: int = Path(...), file: UploadFile = File(...)):

    model = load_models()
    diarization_pipeline = set_pipeline()
    data = await speech_to_text(model, diarization_pipeline, file)
    print(data)
    
    return JSONResponse(content=data["transcriptions"])