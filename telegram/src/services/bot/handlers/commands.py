from aiogram import types

from aiogram.types import ReplyKeyboardMarkup
from aiogram.dispatcher import FSMContext
from services.bot.launcher import dp, bot
from services.bot.utils.keyboards import Keyboard
from services.bot.states.problem import ProblemState
from services.bot.states.utils import EventsLogger
from services.bot.utils.phrases import Phrase


@dp.message_handler(commands="start")
@dp.message_handler(commands="start", state=ProblemState.waiting_user_request)
async def cmd_start(message: types.Message, state: FSMContext):
    await EventsLogger.log_new_bot_session(message=message)
    await state.finish()
    markup = ReplyKeyboardMarkup(resize_keyboard=True)
    markup.row(Keyboard.NEW_REPORT)
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.START,
        reply_markup=markup,
    )


@dp.message_handler(commands=["help"])
@dp.message_handler(commands=["help"], state=ProblemState.waiting_user_request)
async def cmd_help(message: types.Message):
    await message.answer(text=Phrase.HELP, parse_mode="Markdown")
