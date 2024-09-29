from typing import Optional
from pydantic import (
    BaseModel,
)


class CreateSessionDTO(BaseModel):
    """Model for creating a new session.

    Attributes:
        chat_id (str): The chat ID to create a session for.
    """
    chat_id: str


class UpdateMessageDTO(BaseModel):
    """Model for updating a message.

    Attributes:
        chat_id (str): The chat ID of the message.
        message_id (str): The message ID of the message.
        text (Optional[str]): The text of the message.
        ai_text (Optional[str]): The AI text of the message.
        created_at (Optional[str]): The created at time of the message.
        is_helpful (Optional[bool]): Whether the message is helpful.
    """
    chat_id: str
    message_id: str
    text: Optional[str]
    ai_text: Optional[str]
    created_at: Optional[str]
    is_helpful: Optional[bool]
