from aiogram import types
from services.bot.utils.phrases import Phrase
from services.bot.launcher import dp
from aiogram.dispatcher import FSMContext


@dp.message_handler(state="*")
async def handle_other_problem_description(message: types.Message, state: FSMContext):
    if await state.get_state() is None:
        await message.answer(Phrase.NO_REPONSE)
