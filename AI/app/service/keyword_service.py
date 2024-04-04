# -*- coding: utf-8 -*-
import torch
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM

tokenizer = AutoTokenizer.from_pretrained("rycont/KoQuestionBART")
model = AutoModelForSeq2SeqLM.from_pretrained("rycont/KoQuestionBART")

def out_keyword(origin_text):
    text="키워드 추출:3<unused1>"+ origin_text

    raw_input_ids = tokenizer.encode(text)
    input_ids = [tokenizer.bos_token_id] + raw_input_ids + [tokenizer.eos_token_id]

    question_ids = model.generate(torch.tensor([input_ids]), num_beams=4, max_length=512, eos_token_id=1)
    question_ids=tokenizer.decode(question_ids.squeeze().tolist(), skip_special_tokens=False)
    
    question_ids=question_ids.replace("</s>","")
    question_ids=question_ids.replace(" ","")
    question_ids=question_ids.split("<unused2>")
    for s in question_ids:
        if(len(s)>20):
            question_ids.remove(s)
    
    return set(question_ids)
