from services.bot.states.problem import ProblemState


async def set_waiting_user_request_state() -> None:
    """
    Set the bot state to ProblemState.waiting_user_request.

    This function is used to set the bot state to waiting for a user request.
    """
    await ProblemState.waiting_user_request.set()
