
from openai import OpenAI
import os

def do_difference(prompts):
    api_key = os.getenv("OPENAI_API_KEY")
    command = os.getenv("COMMAND_DIFFERENCE")
    client = OpenAI(api_key=api_key)
    
    system_content = command
    
    messages = [
        {"role": "system", "content": system_content},
    ]
    for prompt in prompts:
        messages.append({"role": "user", "content": prompt})
    
    completion = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    
    generated_text = str(completion.choices[0].message.content)
    return generated_text