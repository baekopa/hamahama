from service.summary_pre_service import chk_origin_text_len, split_origin_text, split_origin_text_list, split_summary_text_for_remind_quiz

def process_text(original_text: str):
    origin_text_split_list = chk_origin_text_len(original_text)
    max_len = len(origin_text_split_list)
    origin_formatted_list = split_origin_text(origin_text_split_list)
    origin_text_list = split_origin_text_list(origin_formatted_list, max_len)

    return origin_text_list

def process_for_remind_quiz(summary_text:str):
    origin_text_split_list = chk_origin_text_len(summary_text)
    max_len = len(origin_text_split_list)
    origin_formatted_list = split_origin_text(origin_text_split_list)
    create_quiz_summary=split_summary_text_for_remind_quiz(origin_formatted_list,max_len)
    
    return create_quiz_summary

