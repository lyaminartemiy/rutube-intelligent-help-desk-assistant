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
