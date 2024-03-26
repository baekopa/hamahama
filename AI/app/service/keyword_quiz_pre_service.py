from service.keyword_service import out_keyword
from service.quiz_service import quiz_create

def do_keyword(origin_text_list):
    output = []
    for i in range(len(origin_text_list)):
        output.extend(out_keyword(origin_text_list[i]))

    return output

def do_quiz(origin_text_list):
    output = []
    for i in range(len(origin_text_list)):
        output.append(quiz_create(origin_text_list[i]))

    return output
