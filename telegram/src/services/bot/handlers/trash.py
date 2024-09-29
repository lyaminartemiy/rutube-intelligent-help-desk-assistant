from aiogram import types
from services.bot.utils.phrases import Phrase
from services.bot.launcher import dp
from aiogram.dispatcher import FSMContext


@dp.message_handler(state="*")
async def handle_other_problem_description(message: types.Message, state: FSMContext):
    """
    Handle a message that doesn't match any of the other handlers.

    If the state is None (i.e., the user hasn't started a new session yet),
    send a message to the user indicating that they haven't started a session.
    """
    if await state.get_state() is None:
        await message.answer(Phrase.NO_REPONSE)
