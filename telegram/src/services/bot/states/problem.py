from typing import Optional
from aiogram.dispatcher.filters.state import State, StatesGroup


class ProblemState(StatesGroup):
    dislike_count: int = 0
    last_message_id: Optional[int] = None
    previous_message_id: Optional[int] = None
    waiting_user_request = State()
