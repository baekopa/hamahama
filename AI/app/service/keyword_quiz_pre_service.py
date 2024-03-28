from service.keyword_service import out_keyword
from service.quiz_service import quiz_create

def do_keyword(origin_text_list):
    output = []
    for i in range(len(origin_text_list)):
        output.extend(out_keyword(origin_text_list[i]))

    return output

def do_quiz(summary_text_for_quiz):
    return quiz_create(summary_text_for_quiz)
    # output = []
    # for i in range(len(summary_text_for_quiz)):
    #     output.append(quiz_create(summary_text_for_quiz[i]))

    # return output
