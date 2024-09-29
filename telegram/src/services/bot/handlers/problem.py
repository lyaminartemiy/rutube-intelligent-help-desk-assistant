from aiogram import types
from aiogram.dispatcher import FSMContext
from services.bot.launcher import bot, dp
from services.bot.states.problem import ProblemState
from services.bot.states.utils import set_waiting_user_request_state
from services.bot.utils.events_logging import EventsLogger
from services.bot.utils.keyboards import Keyboard
from services.bot.utils.phrases import Phrase
from services.bot.utils.ai_service import post_question_in_ai_service


@dp.message_handler(text=Keyboard.NEW_REPORT)
@dp.message_handler(text=Keyboard.NEW_REPORT, state=ProblemState.waiting_user_request)
@dp.message_handler(
    text=Keyboard.NEW_REPORT, state=ProblemState.waiting_dispatcher_response
)
async def handle_new_report_problem(message: types.Message, state: FSMContext) -> None:
    """
    Handle the user's request to start a new session.

    This function is called when the user sends the command /start.
    It logs the new bot session to the event store and asks the user
    to start a new session.
    """
    # Log the new bot session
    await EventsLogger.log_new_bot_session(message=message)

    # Finish the current state
    await state.finish()

    # Set the bot state to ProblemState.waiting_user_request
    await set_waiting_user_request_state()

    # Send the message to the user
    await bot.send_message(
        chat_id=message.from_user.id,
        text=Phrase.NEW_REPORT,
    )


@dp.message_handler(state=ProblemState.waiting_user_request)
async def handle_problem_answer_from_user(
    message: types.Message, state: FSMContext
) -> None:
    ai_response = await post_question_in_ai_service(question=message.text)
    await EventsLogger.log_user_message(message=message, ai_text=ai_response.get("answer"))


@dp.callback_query_handler(
    text="positive_feedback", state=ProblemState.waiting_user_request
)
async def handle_positive_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    """
    Handle a positive feedback from the user.

    This function is called when the user sends a positive feedback
    to the message sent by the AI model. It logs the positive feedback
    to the event store and removes the feedback buttons from the message.
    """
    # Edit the message to remove the feedback buttons
    await bot.edit_message_reply_markup(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        reply_markup=None,
    )


@dp.callback_query_handler(
    text="negative_feedback", state=ProblemState.waiting_user_request
)
async def handle_negative_feedback(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    """
    Handle a negative feedback from the user.

    This function is called when the user sends a negative feedback
    to the message sent by the AI model. It logs the negative feedback
    to the event store and removes the feedback buttons from the message.
    """
    # Edit the message to remove the feedback buttons
    await bot.edit_message_reply_markup(
        chat_id=callback_query.from_user.id,
        message_id=callback_query.message.message_id,
        reply_markup=None,
    )
