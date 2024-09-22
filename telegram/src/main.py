import asyncio

from aiohttp import web

import services.bot.handlers
from routers.message import send_bot_message, send_dispatcher_message
from services.bot.launcher import dp
from loguru import logger


async def webserver_start():
    app = web.Application()
    app.router.add_post("/send-message/bot", send_bot_message)
    app.router.add_post("/send-message/dp", send_dispatcher_message)

    runner = web.AppRunner(app)
    await runner.setup()

    site = web.TCPSite(runner, "0.0.0.0", 8000)
    await site.start()

    logger.info("Web server started at http://0.0.0.0:8000")


async def bot_start():
    logger.info("Telegram bot starting...")
    await dp.start_polling()


if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    bot_task = loop.create_task(bot_start())
    webserver_task = loop.create_task(webserver_start())
    loop.run_until_complete(asyncio.gather(bot_task, webserver_task))
