from pydantic import BaseModel
from typing import List

class OriginalText(BaseModel):
    originalText:str

class QuizRequest(BaseModel):
    summaryText:str

class OriginalTextList(BaseModel):
    submittedNoteList: List[str]


class DifferenceRequest(BaseModel):
    differenceTextList: List[str]