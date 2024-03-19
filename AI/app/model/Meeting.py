from pydantic import BaseModel

class Meeting(BaseModel):
    meeting_id: str
    # meeting_title: str
    # 추가 필드 등


