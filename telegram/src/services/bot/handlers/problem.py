from loguru import logger

from aiogram import types
from aiogram.dispatcher import FSMContext
from aiogram.types import ReplyKeyboardMarkup, ReplyKeyboardRemove
from config.config import EventStoreSettings
from services.bot.launcher import bot, dp
from services.bot.states.problem import ProblemState
from services.bot.states.utils import (
    increment_dislike_count,
    reset_dislike_count,
    set_waiting_user_request_state,
)
from services.bot.utils.events_logging import log_new_message, log_new_session
from services.bot.utils.keyboards import Keyboard
from services.bot.utils.phrases import Phrase
from services.message import (
    send_ai_message_to_user_mock,
    send_dispather_message_to_user_mock,
)


@dp.message_handler(text=Keyboard.NEW_REPORT)
@dp.message_handler(text=Keyboard.NEW_REPORT, state=ProblemState.waiting_user_request)
@dp.message_handler(
    text=Keyboard.NEW_REPORT, state=ProblemState.waiting_dispatcher_response
)
async def handle_new_report_problem(message: types.Message, state: FSMContext) -> None:
    await state.finish()
    await set_waiting_user_request_state()
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.NEW_REPORT,
    )
    logger.info(f"New report from user: {message.from_user.id}")


@dp.message_handler(state=ProblemState.waiting_user_request)
async def handle_problem_answer_from_ai(
    message: types.Message, state: FSMContext
) -> None:
    await send_ai_message_to_user_mock(message=message, state=state)
    logger.info(f"New message from user: {message.from_user.id} text: {message.text}")


@dp.message_handler(state=ProblemState.waiting_dispatcher_response)
async def handle_problem_answer_from_dispatcher(message: types.Message) -> None:
    await send_dispather_message_to_user_mock(message=message)
    logger.info(f"New message from user: {message.from_user.id} text: {message.text}")


@dp.callback_query_handler(
    text="positive_feedback", state=ProblemState.waiting_user_request
)
async def handle_positive_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    await bot.edit_message_reply_markup(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        reply_markup=None,
    )
    await reset_dislike_count(message=callback_query, state=state)
    logger.info(
        f"Positive feedback from user: {callback_query.from_user.id} message_id: {callback_query.message.message_id}"
    )


@dp.callback_query_handler(
    text="negative_feedback", state=ProblemState.waiting_user_request
)
async def handle_negative_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    await bot.edit_message_reply_markup(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        reply_markup=None,
    )
    await increment_dislike_count(callback_query, state)
    logger.info(
        f"Negative feedback from user: {callback_query.from_user.id} message_id: {callback_query.message.message_id}"
    )
