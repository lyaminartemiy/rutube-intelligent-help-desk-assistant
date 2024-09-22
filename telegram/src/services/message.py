import asyncio
from typing import Any, Coroutine

from loguru import logger

from aiogram import types
from aiogram.dispatcher import FSMContext
from services.bot.launcher import dp, bot
from services.bot.states.utils import EventsLogger
from services.bot.states.problem import ProblemState
from services.bot.utils.phrases import Phrase
from models import schemas


async def send_bot_message_to_user(
    chat_id: str,
    text: str,
    is_answer: bool,
) -> schemas.UpdateMessageDTO:
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
        logger.info(f"[INFO]: ОТПРАВЛЯЕМ СООБЩЕНИЕ ИЗ БОТА")
        message = await bot.send_message(
            chat_id=int(chat_id),
            text=text,
            reply_markup=inline_keyboard,
        )
        logger.info(f"[INFO]: БОТ ОТППРАВИЛ MESSAGE_ID: {message.message_id}")
    else:
        message = await bot.send_message(
            chat_id=int(chat_id),
            text=Phrase.NO_REPONSE,
        )

    logger.info(f"[INFO]: Sent message from bot to user: {chat_id} with text: {text}")
    # state = dp.current_state(chat=int(chat_id), user=int(chat_id))
    # await state.update_data(last_message_id=message.message_id)
    # await EventsLogger.log_new_bot_message(message=message)
    return schemas.UpdateMessageDTO(
        chat_id=str(message.chat.id),
        message_id=str(message.message_id),
        text=None,
        created_at=None,
        is_helpful=None,
    )


async def send_dispatcher_message_to_user(
    chat_id: int,
    text: str,
) -> None:
    await bot.send_message(
        chat_id=chat_id,
        text=text,
    )
    logger.info(f"Sent message from dispatcher to user: {chat_id} and text: {text}")


async def send_ai_message_to_user_mock(
    message: types.Message, state: FSMContext
) -> Coroutine[Any, Any, types.Message]:
    await asyncio.sleep(1)

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
        chat_id=message.from_user.id,
        text="[TODO]: ЗДЕСЬ БУДЕТ ОТВЕТ БОТА НА ВОПРОС!",
        reply_markup=inline_keyboard,
    )
    await state.update_data(last_message_id=message.message_id)


async def send_dispather_message_to_user_mock(
    message: types.Message,
) -> Coroutine[Any, Any, types.Message]:
    await asyncio.sleep(1)
    await bot.send_message(
        chat_id=message.from_user.id,
        text="[TODO]: ЗДЕСЬ БУДЕТ ОТВЕТ ДИСПЕТЧЕРА  ВОПРОС!",
        reply_markup=None,
    )
