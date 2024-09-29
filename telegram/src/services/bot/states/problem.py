from typing import Optional
from aiogram.dispatcher.filters.state import State, StatesGroup


class ProblemState(StatesGroup):
    """
    States for the problem handler.

    Attributes:
        dislike_count (int): The number of times the user has disliked the response.
        last_message_id (Optional[int]): The ID of the last message sent by the bot.

    States:
        waiting_user_request (State): The state where the bot is waiting for the user to send a new problem.
        waiting_dispatcher_response (State): The state where the bot is waiting for the dispatcher to respond.
    """
    dislike_count: int = 0
    last_message_id: Optional[int] = None

    waiting_user_request = State()
    waiting_dispatcher_response = State()
