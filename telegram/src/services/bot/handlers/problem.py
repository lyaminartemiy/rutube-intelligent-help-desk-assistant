import asyncio

from aiogram import types
from aiogram.dispatcher import FSMContext
from aiogram.dispatcher.filters import ContentTypeFilter
from aiogram.types import ReplyKeyboardMarkup
from services.bot.handlers.commands import _send_start_message
from services.bot.keyboards.utils import keyboard_phrases as keyboard_phrase
from services.bot.states.problem import ProblemState
from services.bot.utils import phrases
from services.bot.utils.launcher import dp


@dp.message_handler(text=keyboard_phrase["problem_message"])
async def handle_start_problem_message(message: types.Message):
    user_id = message.from_user.id
    print("USER_ID:", user_id)  # TODO: send this user event into database
    await _start_problem_mode(message)


async def _start_problem_mode(message: types.Message):
    markup = ReplyKeyboardMarkup(resize_keyboard=True)
    markup.row(
        keyboard_phrase["restart_problem_message"],
        keyboard_phrase["back_start_message"],
    )
    await message.answer(phrases.problem_answer, reply_markup=markup)
    await ProblemState.waiting_for_problem_description.set()


@dp.message_handler(
    text=keyboard_phrase["restart_problem_message"],
    state=ProblemState.waiting_for_problem_description,
)
async def handle_restart_problem_message(message: types.Message, state: FSMContext):
    user_id = message.from_user.id
    print(f"USER_ID: {user_id}, Message: {message.text}")

    await message.answer(phrases.back_start_answer)
    await state.finish()
    await _start_problem_mode(message)


@dp.message_handler(
    text=keyboard_phrase["back_start_message"],
    state=ProblemState.waiting_for_problem_description,
)
async def handle_back_start_problem_message(message: types.Message, state: FSMContext):
    user_id = message.from_user.id
    print(f"USER_ID: {user_id}, Message: {message.text}")

    await _send_start_message(message)
    await state.finish()


@dp.message_handler(state=ProblemState.waiting_for_problem_description)
async def handle_problem_response_answer(message: types.Message, state: FSMContext):
    user_id = message.from_user.id
    print(f"USER_ID: {user_id}, Message: {message.text}")

    await message.answer(phrases.problem_response_answer)

