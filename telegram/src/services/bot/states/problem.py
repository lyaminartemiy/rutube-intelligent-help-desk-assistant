from aiogram.dispatcher.filters.state import State, StatesGroup


class ProblemState(StatesGroup):
    waiting_for_problem_description = State()
