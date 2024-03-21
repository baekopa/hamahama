from pydantic import BaseModel

class SummaryDTO(BaseModel):
    originalText: str
    summaryText:str




