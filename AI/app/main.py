import uvicorn
from fastapi import FastAPI
from routers import study

app=FastAPI()
app.include_router(study.router)

origins =["*"]

# app.add_middleware(
#     CORSMiddleware,
#     allow_origins=origins,
#     allow_credentials=True,
#     allow_methods=["*"],
#     allow_headers=["*"]
# )

@app.get("/")
async def root():
    return {"message": "Hello World"}

if __name__=='__main__':
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)