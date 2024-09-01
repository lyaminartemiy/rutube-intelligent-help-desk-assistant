import asyncio
import time

from aiogram import types
from aiogram.dispatcher import FSMContext
from aiogram.dispatcher.filters import ContentTypeFilter
from aiogram.types import ReplyKeyboardMarkup
from services.bot.handlers.commands import _send_start_message
from services.bot.keyboards.utils import keyboard_phrases as keyboard_phrase
from services.bot.states.problem import ProblemState
from services.bot.utils import phrases
from services.bot.utils.launcher import bot, dp


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
    await message.answer(phrases.problem_answer, reply_markup=markup, parse_mode="Markdown")
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

    async def send_follow_up_message():
        inline_keyboard = types.InlineKeyboardMarkup()
        inline_keyboard.add(types.InlineKeyboardButton("Да", callback_data="yes"))
        inline_keyboard.add(types.InlineKeyboardButton("Нет", callback_data="no"))
        await asyncio.sleep(3)
        await bot.send_message(
            user_id, "Вас устроил этот ответ?", reply_markup=inline_keyboard
        )
        await state.set_state(ProblemState.waiting_for_feedback)

    asyncio.create_task(send_follow_up_message())


# Обработчик callback-нажатий
@dp.callback_query_handler(text=["yes", "no"], state=ProblemState.waiting_for_feedback)
async def handle_callback_query(callback_query: types.CallbackQuery, state: FSMContext):
    user_id = callback_query.from_user.id
    callback_data = callback_query.data

    if callback_data == "yes":
        await callback_query.answer("Ответ принят: Да")
        await callback_query.message.edit_text(
            "Спасибо! Мы рады, что ответ был полезен."
        )
        await state.set_state(ProblemState.feedback_received)
        await bot.send_message(
            user_id, "Если у вас есть еще вопросы, пожалуйста, напишите нам."
        )
    elif callback_data == "no":
        await callback_query.answer("Ответ принят: Нет")
        await callback_query.message.edit_text(
            "Спасибо за обратную связь. Мы постараемся улучшить наш ответ."
        )
        await state.set_state(ProblemState.feedback_received)
        await bot.send_message(
            user_id, "Пожалуйста, опишите, что можно улучшить в нашем ответе."
        )


# Обработчик окончания состояния
@dp.message_handler(state=ProblemState.feedback_received)
async def handle_feedback_received(message: types.Message, state: FSMContext):
    await state.finish()
    await message.answer("Спасибо за вашу обратную связь!")
