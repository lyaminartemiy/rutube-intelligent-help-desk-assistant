from aiogram import types
from aiogram.types import ReplyKeyboardMarkup

from services.bot.utils.launcher import dp
from services.bot.keyboards.utils import keyboard_phrases as keyboard_phrase
from services.bot.utils import phrases


@dp.message_handler(commands="start")
@dp.message_handler(text=keyboard_phrase["back_start_message"])
async def cmd_start(message: types.Message):
    await _send_start_message(message)


async def _send_start_message(message: types.Message):
    markup = ReplyKeyboardMarkup(resize_keyboard=True)
    markup.row(keyboard_phrase["problem_message"], keyboard_phrase["faq_message"])

    await message.answer(text=phrases.start_phrase, reply_markup=markup)


@dp.message_handler(commands=["help"])
async def cmd_help(message: types.Message):
    await message.answer(text=phrases.help_text, parse_mode="Markdown")
