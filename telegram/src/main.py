import asyncio

from aiohttp import web

import services.bot.handlers
from routers.message import send_dispatcher_message
from services.bot.launcher import dp
from loguru import logger
from dotenv import load_dotenv


async def webserver_start():
    """
    Start the web server.

    The web server is needed to receive messages from the frontend.
    """
    app = web.Application()
    app.router.add_post("/api/send-message/dp", send_dispatcher_message)

    # Create the application runner
    runner = web.AppRunner(app)

    # Set up the application
    await runner.setup()

    # Set up the site
    site = web.TCPSite(runner, "0.0.0.0", 8000)

    # Start the site
    await site.start()

    # Log that the web server started
    logger.info("Web server started at http://0.0.0.0:8000")


async def bot_start() -> None:
    """
    Start the Telegram bot.

    This function starts the Telegram bot.
    """
    logger.info("Telegram bot starting...")
    # Start the bot
    await dp.start_polling()


if __name__ == "__main__":
    load_dotenv()
    loop = asyncio.get_event_loop()
    bot_task = loop.create_task(bot_start())
    webserver_task = loop.create_task(webserver_start())
    loop.run_until_complete(asyncio.gather(bot_task, webserver_task))
