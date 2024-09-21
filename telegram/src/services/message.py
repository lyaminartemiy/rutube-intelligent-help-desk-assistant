import asyncio
from typing import Any, Coroutine

from loguru import logger

from aiogram import types
from aiogram.dispatcher import FSMContext
from services.bot.launcher import bot
from services.bot.states.problem import ProblemState
from services.bot.states.utils import update_state_assessments
from services.bot.utils.phrases import Phrase


async def send_message_to_user(
    chat_id: int,
    text: str,
    is_answer: bool,
    state: FSMContext = ProblemState.waiting_user_request,
) -> None:
    await bot.send_message(
        chat_id=chat_id,
        text=text if is_answer else Phrase.NO_REPONSE,
    )
    if is_answer:
        inline_keyboard = types.InlineKeyboardMarkup(row_width=2)
        inline_keyboard.add(
            types.InlineKeyboardButton(
                text=Phrase.POSITIVE_FEEDBACK, callback_data="positive_feedback"
            ),
            types.InlineKeyboardButton(
                text=Phrase.NEGATIVE_FEEDBACK, callback_data="negative_feedback"
            ),
        )
        message = await bot.send_message(
            chat_id=chat_id,
            text=Phrase.ANSWER_ASSESMENT,
            reply_markup=inline_keyboard if is_answer else None,
        )
        await state.update_data(last_message_id=message.message_id)
    logger.info(f"Sent message from backend to user: {chat_id} and text: {text}")


async def send_message_to_user_mock(
    message: types.Message, state: FSMContext
) -> Coroutine[Any, Any, types.Message]:
    await asyncio.sleep(1)
    await bot.send_message(
        chat_id=message.from_user.id,
        text="[TODO]: ЗДЕСЬ БУДЕТ ОТВЕТ БОТА НА ВОПРОС!",
    )

    inline_keyboard = types.InlineKeyboardMarkup(row_width=2)
    inline_keyboard.add(
        types.InlineKeyboardButton(
            text=Phrase.POSITIVE_FEEDBACK, callback_data="positive_feedback"
        ),
        types.InlineKeyboardButton(
            text=Phrase.NEGATIVE_FEEDBACK, callback_data="negative_feedback"
        ),
    )
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.ANSWER_ASSESMENT,
        reply_markup=inline_keyboard,
    )
    await state.update_data(last_message_id=message.message_id)
