from service.summary_service import ko_summary

def remove_hama_tags(origin_text):
    return ' '.join([origin_text.replace(f"[하마{i}]", "") for i in range(1, 7)])

def chk_origin_text_len(origin_text):
    origin_text.replace("  "," ")
    origin_text.replace(". ",".\n")
    splitted_str = list(origin_text.split('\n'))
    return splitted_str

def split_origin_text(origin_text_split_list):
    for i, s in enumerate(origin_text_split_list):
        if i != len(origin_text_split_list) - 1:
            continue
        s.replace("\n","")
    return origin_text_split_list

def split_origin_text_list(list_text, max_len):
    cnt = 0
    temp_str = ""
    output_list = []
    for i in range(max_len):
        if cnt == 17:
            output_list.append(temp_str)
            cnt = 0
            temp_str = ""
        temp_str += list_text[i]
        cnt += 1
    output_list.append(temp_str)
    return output_list

def split_summary_text_for_remind_quiz(list_text, max_len):
    cnt = 0
    temp_str = ""
    output_list = []
    for i in range(max_len):
        temp_str += list_text[i]

    return temp_str

def do_summary(origin_text_list):
    output = ""
    for i in range(len(origin_text_list)):
        if i == len(origin_text_list) - 1:
            output += ko_summary(origin_text_list[i])
        else:
            output += ko_summary(origin_text_list[i]) + "\n"
    return output
