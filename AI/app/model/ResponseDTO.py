from pydantic import BaseModel


class SummaryDTO(BaseModel):
    originalText: str
    summaryText:str

class KeywordDTO(BaseModel):
    originalText:str
    # summaryText:str
    keyword:list

class QuizDTO(BaseModel):
    originalText:str
    quiz:list


 