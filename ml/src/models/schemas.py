from pydantic import BaseModel


class QASystemDTO(BaseModel):
    text: str
    created_at: str
    side: str
    is_helpful: bool
