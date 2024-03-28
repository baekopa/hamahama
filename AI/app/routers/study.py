from fastapi import APIRouter
from model.request_dto import OriginalText, QuizRequest
from model.response_dto import SummaryDTO, KeywordDTO, QuizDTO, TailQuestionDTO
from service.text_processing import process_text, process_for_remind_quiz
from service.summary_pre_service import do_summary
from service.keyword_quiz_pre_service import do_keyword, do_quiz
from service.tail_question_service import do_tail_question

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
    origin_text_list = process_text(origin_dto.original_text)
    
    keyword_list = do_keyword(origin_text_list)
    return KeywordDTO(original_text=origin_dto.original_text, keyword=keyword_list)

@router.post("/quiz", tags=["리마인드 퀴즈"], response_model=QuizDTO)
async def quiz_text(origin_dto: QuizRequest):
    summary_text_for_quiz= process_for_remind_quiz(origin_dto.summaryText)
    
    quiz_list = do_quiz(summary_text_for_quiz)
    return QuizDTO(quiz=quiz_list)

@router.post("/tailquestion", tags=["꼬리 질문"], response_model=TailQuestionDTO)
async def tail_question_text(origin_dto: OriginalText):
    
    tail_question = do_tail_question(origin_dto.original_text)
    return TailQuestionDTO(original_text=origin_dto.original_text, tail_question=tail_question)