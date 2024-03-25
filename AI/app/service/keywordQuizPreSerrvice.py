from service.keywordService import outKeyword
from service.quizService import quizCreate

def doKeyword(originTextList):
    output=[]
    for i in range(len(originTextList)):
        output.extend(outKeyword(originTextList[i]))

    return output

def doQuiz(originTextList):
    output=[]
    for i in range(len(originTextList)):
        output.append(quizCreate(originTextList[i]))

    return output