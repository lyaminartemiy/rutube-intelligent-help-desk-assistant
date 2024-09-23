import uvicorn
from fastapi import FastAPI

from lifespan import lifespan
from routers.qa_system import router as qa_system_router


app = FastAPI(lifespan=lifespan)
app.include_router(qa_system_router)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
