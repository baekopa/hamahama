from service.summaryService import KoSummary

def chkOriginTextLen(originText):
    splitted_str = list(originText.split('.'))
    return splitted_str

def splitOriginText(originTextSplitList):
    # 각 문자열에 줄바꿈 적용
    originFormattedStr = ""
    for i, s in enumerate(originTextSplitList):
        if i == len(originTextSplitList) - 1:  # 마지막 문자열일 때는 '.'를 붙임
            originFormattedStr += s.strip() + ''
        else:
            originFormattedStr += s.strip() + '.\n'
    return originFormattedStr

def splitOriginTextList(originFormattedStr, maxLen):
    listText=list(originFormattedStr.split('\n'))
    cnt=0
    temp_str=""
    outputList=[]
    for i in range(maxLen):
        if cnt==15:
            outputList.append(temp_str)
            cnt=0
            temp_str=""
        temp_str+=listText[i]
        cnt+=1
    outputList.append(temp_str)
    return outputList

def doSummary(originTextList):
    output=""
    for i in range(len(originTextList)):
        if i==len(originTextList)-1:
            output+=KoSummary(originTextList[i])
        else:
            output+=KoSummary(originTextList[i])+"\n"
    return output