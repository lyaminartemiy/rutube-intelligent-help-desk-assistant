from typing import Optional
from pydantic import BaseModel


class QASystemDTO(BaseModel):
    text: str
    created_at: str
    side: str
    is_helpful: Optional[bool]


class Request(BaseModel):
    question: str


class Response(BaseModel):
    answer: str
    class_1: str
    class_2: str

    # TODO: delete this
    vector_database_result: list
    reranker_result: list
