from typing import Optional
from pydantic import (
    BaseModel,
)


class CreateSessionDTO(BaseModel):
    chat_id: str


class UpdateMessageDTO(BaseModel):
    chat_id: str
    message_id: str
    text: Optional[str]
    ai_text: Optional[str]
    created_at: Optional[str]
    is_helpful: Optional[bool]


class SendBotMessageDTO(BaseModel):
    chat_id: str
    text: Optional[str]
    is_answer: bool


class SendMessageFromBotResponse(BaseModel):
    chat_id: str
    message_id: str


class SendTechSupportMessageDTO(BaseModel):
    chat_id: str
    text: Optional[str]
    is_done: bool


class SendMessageResponse(BaseModel):
    chat_id: str
    message_id: str
