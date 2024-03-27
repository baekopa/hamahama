import torch
from transformers import PreTrainedTokenizerFast
from transformers.models.bart import BartForConditionalGeneration

def load_model():
    model = BartForConditionalGeneration.from_pretrained('../MODEL/kobart_summary')
    return model

def ko_summary(origin_text):
    model = load_model()
    tokenizer = PreTrainedTokenizerFast.from_pretrained('digit82/kobart-summarization')
    text = origin_text

    if text:
        text = text.replace('\n', '')
        input_ids = tokenizer.encode(text)
        input_ids = torch.tensor(input_ids)
        input_ids = input_ids.unsqueeze(0)
        output = model.generate(input_ids, eos_token_id=1, max_length=512, num_beams=5)
        output = tokenizer.decode(output[0], skip_special_tokens=True)
    return output
