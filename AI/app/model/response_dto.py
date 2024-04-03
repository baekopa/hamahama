from pydantic import BaseModel

class SummaryDTO(BaseModel):
    originalText: str
    summaryText:str

class KeywordDTO(BaseModel):
    # original_text:str
    keyword:list

class QuizDTO(BaseModel):
    quiz:str

class TailQuestionDTO(BaseModel):
    tailQuestion:str

class UniquificationDTO(BaseModel):
    uniquification:str

class DeduplicationDTO(BaseModel):
    deduplication:str

class DifferenceDTO(BaseModel):
    difference:str
