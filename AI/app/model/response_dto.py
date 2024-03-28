from pydantic import BaseModel

class SummaryDTO(BaseModel):
    originalText: str
    summaryText:str

class KeywordDTO(BaseModel):
    # original_text:str
    keyword:list

class QuizDTO(BaseModel):
    # original_text:str
    quiz:str

class TailQuestionDTO(BaseModel):
    original_text:str
    tail_question:str
