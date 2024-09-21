from aiogram import types
from aiogram.dispatcher import FSMContext
from services.bot.states.problem import ProblemState
from services.bot.utils.phrases import Phrase


async def waiting_user_request():
    await ProblemState.waiting_user_request.set()


async def reset_dislike_count(state: FSMContext):
    await state.update_data(dislike_count=0)

    
async def update_state_assessments(message: types.Message, state: FSMContext):
    data = await state.get_data()
    session_assessments = data.get("session_assessments", [])
    session_assessments.append(message)
    await state.update_data(session_assessments=session_assessments)


async def check_dislike_count(
    callback_query: types.CallbackQuery, state: FSMContext
) -> None:
    data = await state.get_data()
    dislike_count = data.get("dislike_count", 0)
    dislike_count += 1

    if dislike_count >= 2:
        await callback_query.message.answer(
            text=Phrase.DISLIKE_LIMIT,
        )

    await state.update_data(dislike_count=dislike_count)
