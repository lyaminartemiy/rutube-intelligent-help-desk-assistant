import logging
import subprocess
from datetime import datetime

from fastapi import FastAPI

import services.bot.handlers
from aiogram import executor
from routers.message import router
from services.bot.utils.launcher import dp

app = FastAPI()
app.include_router(router)

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s]: %(message)s",
    # handlers=[
    #     logging.FileHandler(f"../logs/app_{str(datetime.today())}.log"),
    #     logging.StreamHandler(),
    # ],
)

logger = logging.getLogger(__name__)


def run_telegram_bot():
    logger.info("TELEGRAM BOT start application...")
    executor.start_polling(dp, skip_updates=False)


if __name__ == "__main__":
    # Run FastAPI Server
    uvicorn_process = subprocess.Popen(
        [
            "uvicorn",
            "main:app",
            "--host",
            "0.0.0.0",
            "--port",
            "8080",
            "--log-level",
            "info",
        ]
    )

    # Run Telegram Bot
    run_telegram_bot()

    uvicorn_process.wait()
