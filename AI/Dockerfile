FROM python:3.9.13

WORKDIR /app
COPY ./app /app

RUN apt-get update
RUN pip install -r /app/requirements.txt

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "9000"]