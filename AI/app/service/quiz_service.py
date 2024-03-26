# -*- coding: utf-8 -*-
import torch
from transformers import PreTrainedTokenizerFast
from transformers import BartForConditionalGeneration

def load_model():
    model = BartForConditionalGeneration.from_pretrained('Sehong/kobart-QuestionGeneration')
    return model

def load_tokenizer():
    tokenizer = PreTrainedTokenizerFast.from_pretrained('Sehong/kobart-QuestionGeneration')
    return tokenizer

def quiz_create(origin_text):
    text = origin_text
    model = load_model()
    tokenizer = load_tokenizer()

    raw_input_ids = tokenizer.encode(text)
    input_ids = [tokenizer.bos_token_id] + raw_input_ids + [tokenizer.eos_token_id]

    question_ids = model.generate(torch.tensor([input_ids]), num_beams=4, max_length=512, eos_token_id=1)
    question_ids = tokenizer.decode(question_ids.squeeze().tolist(), skip_special_tokens=True)
    return question_ids
