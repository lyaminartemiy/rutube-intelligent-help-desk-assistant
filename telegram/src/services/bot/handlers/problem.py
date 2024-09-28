from aiogram import types
from aiogram.dispatcher import FSMContext
from services.bot.launcher import bot, dp
from services.bot.states.problem import ProblemState
from services.bot.states.utils import (
    increment_dislike_count,
    reset_dislike_count,
    set_waiting_user_request_state,
)
from services.bot.utils.events_logging import EventsLogger
from services.bot.utils.keyboards import Keyboard
from services.bot.utils.phrases import Phrase
from services.bot.utils.ai_service import post_question_in_ai_service
from services.message import (
    send_ai_message_to_user_mock,
    send_dispather_message_to_user_mock,
)


@dp.message_handler(text=Keyboard.NEW_REPORT)
@dp.message_handler(text=Keyboard.NEW_REPORT, state=ProblemState.waiting_user_request)
@dp.message_handler(
    text=Keyboard.NEW_REPORT, state=ProblemState.waiting_dispatcher_response
)
async def handle_new_report_problem(message: types.Message, state: FSMContext) -> None:
    await EventsLogger.log_new_bot_session(message=message)
    await state.finish()
    await set_waiting_user_request_state()
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.NEW_REPORT,
    )


@dp.message_handler(state=ProblemState.waiting_user_request)
async def handle_problem_answer_from_user(
    message: types.Message, state: FSMContext
) -> None:
    ai_response = await post_question_in_ai_service(message=message, state=state)
    await EventsLogger.log_user_message(message=message, ai_text=ai_response["answer"])


@dp.callback_query_handler(
    text="positive_feedback", state=ProblemState.waiting_user_request
)
async def handle_positive_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    await bot.edit_message_reply_markup(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        reply_markup=None,
    )
    # await reset_dislike_count(callback_query=callback_query, state=state)


@dp.callback_query_handler(
    text="negative_feedback", state=ProblemState.waiting_user_request
)
async def handle_negative_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    await bot.edit_message_reply_markup(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        reply_markup=None,
    )
    # await increment_dislike_count(callback_query=callback_query, state=state)
