import threading
import aiohttp
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


# async def start_fastapi():
#     config = uvicorn.Config("main:app", host="0.0.0.0", port=8001, log_level="info")
#     server = uvicorn.Server(config)
#     await server.serve()


# async def start_bot():
#     async with aiohttp.ClientSession() as session:
#         await dp.start_polling()


# async def main():
#     # await asyncio.gather(
#     #     start_fastapi(),
#     #     start_bot(),
#     # )

#     tasks = [asyncio.create_task(start_fastapi()), asyncio.create_task(start_bot())]
#     done, pending = await asyncio.wait(tasks, timeout=60)  # 60 seconds timeout
#     if pending:
#         print("Tasks timed out:", pending)


# if __name__ == "__main__":
#     asyncio.run(main())


def start_fastapi():
    uvicorn.run(app, host="0.0.0.0", port=8001)


# Function to run Telegram bot
def start_telegram_bot():
    loop = asyncio.new_event_loop() # Create a new event loop for the thread
    asyncio.set_event_loop(loop) # Set the event loop for the current thread
    executor.start_polling(dp, skip_updates=True)


if __name__ == "__main__":
    # Create and start threads for FastAPI and Telegram bot
    fastapi_thread = threading.Thread(target=start_fastapi)
    telegram_bot_thread = threading.Thread(target=start_telegram_bot)

    # Start both threads
    fastapi_thread.start()
    telegram_bot_thread.start()

    # Join the threads to keep the main program running
    fastapi_thread.join()
    telegram_bot_thread.join()
