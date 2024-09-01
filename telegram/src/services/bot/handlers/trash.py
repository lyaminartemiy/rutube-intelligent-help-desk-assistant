from aiogram import types
from services.bot.utils import phrases
from services.bot.utils.launcher import dp
from aiogram.dispatcher import FSMContext


@dp.message_handler(state="*")
async def handle_other_problem_description(message: types.Message, state: FSMContext):
    if await state.get_state() is None:
        await message.answer(phrases.did_not_understand_answer)
