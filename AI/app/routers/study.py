from fastapi import APIRouter
from model.Meeting import Meeting
from model.SummaryDTO import SummaryDTO
from service.summaryPreService import chkOriginTextLen, splitOriginText, splitOriginTextList, doSummary

router=APIRouter(
    prefix="/studies",
    tags=["studies"]
)

@router.post("/meeting", response_model=SummaryDTO)
async def read_minutes(meeting:Meeting):
    originTextSplitList=chkOriginTextLen(meeting.meetingText)
    maxLen=len(originTextSplitList)
    originFormattedStr=splitOriginText(originTextSplitList)
    originTextList=splitOriginTextList(originFormattedStr, maxLen)
    output=doSummary(originTextList)

    response_dto=SummaryDTO(originalText=meeting.meetingText, summaryText=output)

    return response_dto


# @router.get("/prestudy")
# async def read_prestudy():
#     return {}