import asyncio
from aiogram import types
from aiogram.types import ReplyKeyboardMarkup

from services.bot.keyboards.utils import keyboard_phrases as keyboard_phrase
from services.bot.utils.launcher import dp
from services.bot.utils import phrases


@dp.message_handler(text=keyboard_phrase["faq_message"])
async def faq_mode(message: types.Message):
    user_id = message.from_user.id
    print("USER_ID:", user_id)

    markup = ReplyKeyboardMarkup(resize_keyboard=True)
    markup.row(keyboard_phrase["back_start_message"])

    await message.answer(phrases.faq_answer, reply_markup=markup)
