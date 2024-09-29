from aiogram import types

from aiogram.types import ReplyKeyboardMarkup
from aiogram.dispatcher import FSMContext
from services.bot.launcher import dp, bot
from services.bot.utils.keyboards import Keyboard
from services.bot.states.problem import ProblemState
from services.bot.utils.events_logging import EventsLogger
from services.bot.utils.phrases import Phrase


@dp.message_handler(commands=["start"])
@dp.message_handler(commands=["start"], state=ProblemState.waiting_user_request)
async def cmd_start(message: types.Message, state: FSMContext):
    """
    Handle the start command.

    This function is called when the user sends the command /start.
    It logs the new bot session to the event store and asks the user
    to start a new session.
    """
    # Log the new bot session
    await EventsLogger.log_new_bot_session(message=message)

    # Finish the current state
    await state.finish()

    # Create the keyboard
    markup = ReplyKeyboardMarkup(resize_keyboard=True)
    markup.row(Keyboard.NEW_REPORT)

    # Send the message to the user
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.START,
        reply_markup=markup,
    )


@dp.message_handler(commands=["help"])
@dp.message_handler(commands=["help"], state=ProblemState.waiting_user_request)
async def cmd_help(message: types.Message):
    """
    Handle the help command.

    This function is called when the user sends the command /help.
    It logs the user's request for help and sends the help message.
    """
    # Log the user's request for help
    await EventsLogger.log_user_request(message=message, ai_text=None)

    # Send the help message
    await message.answer(text=Phrase.HELP, parse_mode="Markdown")
