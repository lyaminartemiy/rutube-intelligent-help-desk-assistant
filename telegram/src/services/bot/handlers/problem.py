from loguru import logger

from aiogram import types
from aiogram.dispatcher import FSMContext
from aiogram.types import ReplyKeyboardMarkup, ReplyKeyboardRemove
from config.config import EventStoreSettings
from services.bot.launcher import bot, dp
from services.bot.states.problem import ProblemState
from services.bot.states.utils import (check_dislike_count,
                                       reset_dislike_count,
                                       waiting_user_request)
from services.bot.utils.events_logging import log_new_message, log_new_session
from services.bot.utils.keyboards import Keyboard
from services.bot.utils.phrases import Phrase
from services.message import send_message_to_user_mock


@dp.message_handler(text=Keyboard.NEW_REPORT)
@dp.message_handler(text=Keyboard.NEW_REPORT, state=ProblemState.waiting_user_request)
async def handle_new_report_problem(message: types.Message, state: FSMContext) -> None:
    await state.finish()
    await waiting_user_request()
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.NEW_REPORT,
    )


@dp.message_handler(state=ProblemState.waiting_user_request)
async def handle_problem_response_answer(message: types.Message, state: FSMContext) -> None:
    await send_message_to_user_mock(message=message, state=state)


@dp.callback_query_handler(
    text="positive_feedback", state=ProblemState.waiting_user_request
)
async def handle_positive_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    await bot.edit_message_text(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        text=Phrase.POSITIVE_ANSWER,
        reply_markup=None,
    )
    await reset_dislike_count(state=state)


@dp.callback_query_handler(
    text="negative_feedback", state=ProblemState.waiting_user_request
)
async def handle_negative_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    await bot.edit_message_text(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        text=Phrase.NEGATIVE_ANSWER,
        reply_markup=None,
    )
    await check_dislike_count(callback_query, state)
