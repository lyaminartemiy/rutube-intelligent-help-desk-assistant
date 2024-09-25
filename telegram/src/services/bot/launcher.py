from aiogram import Bot, Dispatcher, types
from aiogram.contrib.fsm_storage.memory import MemoryStorage

bot = Bot(
    token="7295897375:AAElFFnFwF3tKFD1ox8Sh6BjeL2NEQI4CjY",
    parse_mode=types.ParseMode.HTML,
)
storage = MemoryStorage()
dp = Dispatcher(bot, storage=storage)
