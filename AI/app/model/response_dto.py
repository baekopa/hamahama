from pydantic import BaseModel

class SummaryDTO(BaseModel):
    original_text: str
    summary_text:str

class KeywordDTO(BaseModel):
    original_text:str
    keyword:list

class QuizDTO(BaseModel):
    original_text:str
    quiz:list

class TailQuestionDTO(BaseModel):
    original_text:str
    tail_question:str
