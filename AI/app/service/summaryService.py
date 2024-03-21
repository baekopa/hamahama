import torch
import streamlit as st
from transformers import PreTrainedTokenizerFast
from transformers.models.bart import BartForConditionalGeneration

#@st.cache
# @st.cache(hash_funcs={torch.nn.parameter.Parameter: lambda _: None})
def load_model():
    model = BartForConditionalGeneration.from_pretrained('../MODEL/kobart_summary')
    # tokenizer = get_kobart_tokenizer()
    return model
    
#def load_model():
#    model = BartForConditionalGeneration.from_pretrained('./kobart_summary')
    # tokenizer = get_kobart_tokenizer()
#    return model

def KoSummary(originText):
    #tokenizer = get_kobart_tokenizer()
    model = load_model()
    tokenizer = PreTrainedTokenizerFast.from_pretrained('digit82/kobart-summarization')
    # st.title("KoBART 요약 Test")
    text = originText

    if text:
        text = text.replace('\n', '')
        input_ids = tokenizer.encode(text)
        input_ids = torch.tensor(input_ids)
        input_ids = input_ids.unsqueeze(0)
        output = model.generate(input_ids, eos_token_id=1, max_length=512, num_beams=5)
        output = tokenizer.decode(output[0], skip_special_tokens=True)
    return output