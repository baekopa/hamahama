from service.summaryService import KoSummary

def chkOriginTextLen(originText):
    originText.replace("  "," ")
    originText.replace(". ",".\n")
    splitted_str = list(originText.split('\n'))
    return splitted_str

def splitOriginText(originTextSplitList):
    originFormattedStr = ""
    for i, s in enumerate(originTextSplitList):
        if i != len(originTextSplitList) -1:
            continue
        s.replace("\n","")
    return originTextSplitList

def splitOriginTextList(listText, maxLen):
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