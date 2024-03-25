from fastapi import APIRouter
from model.RequestDTO import OriginalText
from model.ResponseDTO import SummaryDTO, KeywordDTO, QuizDTO
from service.summaryPreService import chkOriginTextLen, splitOriginText, splitOriginTextList, doSummary
from service.keywordQuizPreSerrvice import doKeyword, doQuiz

router=APIRouter(
    prefix="/studies",
    # tags=["요약, 키워드, 퀴즈 생성"]
)

@router.post("/summary", tags=["요약 생성"], response_model=SummaryDTO)
async def summary_text(originalDTO:OriginalText):
    originTextSplitList=chkOriginTextLen(originalDTO.originalText)#데이터 정제 및 list로 변환
    maxLen=len(originTextSplitList)

    originFormattedList=splitOriginText(originTextSplitList)
    originTextList=splitOriginTextList(originFormattedList, maxLen)
    output=doSummary(originTextList)

    response_dto=SummaryDTO(originalText=originalDTO.originalText, summaryText=output)
    return response_dto

@router.post("/keyword",  tags=["키워드"], response_model=KeywordDTO)
async def keyword__text(originDTO:OriginalText):
    originTextSplitList=chkOriginTextLen(originDTO.originalText)
    maxLen=len(originTextSplitList)

    originFormattedList=splitOriginText(originTextSplitList)
    originTextList=splitOriginTextList(originFormattedList, maxLen)
    keywordList=doKeyword(originTextList)

    response_dto=KeywordDTO(originalText=originDTO.originalText, keyword=keywordList)
    return response_dto

@router.post("/quiz", tags=["리마인드 퀴즈"], response_model=QuizDTO)
async def quiz_text(originDTO:OriginalText):
    originTextSplitList=chkOriginTextLen(originDTO.originalText)
    maxLen=len(originTextSplitList)

    originFormattedList=splitOriginText(originTextSplitList)
    originTextList=splitOriginTextList(originFormattedList, maxLen)
    quizList=doQuiz(originTextList)

    quizDTO=QuizDTO(originalText=originDTO.originalText, quiz=quizList)
    return quizDTO