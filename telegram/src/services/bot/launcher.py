from aiogram import Bot, Dispatcher, types
from aiogram.contrib.fsm_storage.memory import MemoryStorage

bot = Bot(
    token="7667046307:AAHUduosnvHapXBM5eG3efWYGmTG85Ru1b4",
    parse_mode=types.ParseMode.HTML,
)
storage = MemoryStorage()
dp = Dispatcher(bot, storage=storage)
