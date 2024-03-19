from fastapi import APIRouter
from model.Meeting import Meeting
from app.model.SummaryDTO import SummaryDTO

router=APIRouter(
    prefix="/studies",
    tags=["studies"]
)

@router.post("/meeting", response_model=SummaryDTO)
async def read_minutes(ment:Meeting):
    
    response_dto=SummaryDTO(originalText=ment.meeting_id, summaryText="요약했지요..ㅋ")
    return response_dto


# @router.get("/prestudy")
# async def read_prestudy():
#     return {}