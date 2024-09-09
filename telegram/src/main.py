import asyncio
import uvicorn
import logging
import subprocess

from fastapi import FastAPI

import services.bot.handlers
from aiogram import executor
from routers.message import router
from services.bot.utils.launcher import dp, bot

app = FastAPI()
app.include_router(router)

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s]: %(message)s",
)

logger = logging.getLogger(__name__)


def run_telegram_bot():
    logger.info("TELEGRAM BOT start application...")
    executor.start_polling(dp, skip_updates=False)


async def start_fastapi():
    config = uvicorn.Config("main:app", host="0.0.0.0", port="8001", log_level="info")
    server = uvicorn.Server(config)
    await server.serve()


async def start_bot():
    await dp.start_polling()


async def main():
    await asyncio.gather(
        start_fastapi(),
        start_bot(),
    )


if __name__ == "__main__":
    asyncio.run(main())
