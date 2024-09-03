from enum import Enum
from typing import List, Optional
from pydantic import (
    BaseModel,
    StrictStr,
    StrictInt,
)


class NewSession(BaseModel):
    chat_id: StrictInt


class NewMessage(BaseModel):
    chat_id: StrictStr
    text: StrictStr
    created_at: StrictStr
    addtitional_content: Optional[List["AddtitionalContentDto"]]


class ContentType(str, Enum):
    AUDIO = "AUDIO"
    VIDEO = "VIDEO"
    OTHER = "OTHER"


class AddtitionalContentDto(BaseModel):
    content_type: ContentType
    file_bytes: bytes
    file_extension: StrictStr


class SendMessageDto(BaseModel):
    chat_id: StrictStr
    text: StrictStr
    author: StrictStr
