from openai import OpenAI
import os

def do_deduplication(prompt):
    api_key = os.getenv("OPENAI_API_KEY")
    command = os.getenv("COMMAND_DEDUPLICATION")
    client = OpenAI(api_key=api_key)
    
    messages = [
        {"role": "system", "content": command},
        {"role": "user", "content": prompt}
    ]
    
    completion = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    
    generated_text = str(completion.choices[0].message.content)
    return generated_text