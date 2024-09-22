from loguru import logger
from typing import Union
from aiogram import types
from aiogram.dispatcher import FSMContext
from services.bot.states.problem import ProblemState
from services.bot.utils.phrases import Phrase


async def set_waiting_user_request_state() -> None:
    """Set the bot state to ProblemState.waiting_user_request."""
    await ProblemState.waiting_user_request.set()


async def is_last_message(
    message: Union[types.Message, types.CallbackQuery], state: FSMContext
) -> bool:
    """Check if the message is the last message in the conversation."""
    data = await state.get_data()
    last_message_id = data.get("last_message_id")
    message_id = (
        message.message_id
        if isinstance(message, types.Message)
        else message.message.message_id
    )
    return last_message_id == message_id


async def reset_dislike_count(message: types.CallbackQuery, state: FSMContext) -> None:
    """Reset the dislike count to 0 if the message is the last message in the conversation."""
    if await is_last_message(message, state):
        await state.update_data(dislike_count=0)

    logger.info(
        f"Dislike count from user: {message.from_user.id} message_id: {message.message.message_id}"
    )


async def increment_dislike_count(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    """Increment the dislike count if the message is the last message in the conversation."""
    data = await state.get_data()
    if await is_last_message(callback_query, state):
        dislike_count = data.get("dislike_count", 0) + 1
        await state.update_data(dislike_count=dislike_count)

        if dislike_count >= 2:
            await callback_query.message.answer(
                text=Phrase.DISPATCHER_WAITING, parse_mode="Markdown"
            )
            await state.finish()
            await ProblemState.waiting_dispatcher_response.set()
            logger.info(
                "Started waiting for dispatcher response for user: %s",
                callback_query.from_user.id,
            )

        logger.info(
            f"Dislike count from user: {callback_query.from_user.id} count: {dislike_count}"
        )
