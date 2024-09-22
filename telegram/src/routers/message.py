from aiohttp import web
from services.message import send_bot_message_to_user, send_dispatcher_message_to_user


async def send_bot_message(request: web.Request) -> web.Response:
    """Send a message from the bot to a user."""
    chat_id: int = int(request.query["chat_id"])
    text: str = request.query["text"]
    is_answer: bool = request.query.get("is_answer", False)

    await send_bot_message_to_user(chat_id, text, is_answer)
    return web.Response(text="Message sent")


async def send_dispatcher_message(request: web.Request) -> web.Response:
    """Send a message from the dispatcher to a user."""
    chat_id: int = int(request.query["chat_id"])
    text: str = request.query["text"]

    await send_dispatcher_message_to_user(chat_id, text)
    return web.Response(text="Message sent")
