from loguru import logger

from aiogram import types
from aiogram.dispatcher import FSMContext
from aiogram.types import ReplyKeyboardMarkup, ReplyKeyboardRemove
from config.config import EventStoreSettings
from services.bot.launcher import bot, dp
from services.bot.states.problem import ProblemState
from services.bot.states.utils import (
    increment_dislike_count,
    reset_dislike_count,
    set_waiting_user_request_state,
)
from services.bot.utils.events_logging import log_new_message, log_new_session
from services.bot.utils.keyboards import Keyboard
from services.bot.utils.phrases import Phrase
from services.message import send_dispather_message_to_user_mock


def func():
    pass
