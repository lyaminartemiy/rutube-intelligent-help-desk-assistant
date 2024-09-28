from loguru import logger
from aiohttp import web
from services.message import send_bot_message_to_user, send_dispatcher_message_to_user


async def send_bot_message(request: web.Request) -> web.Response:
    """Send a message from the bot to a user."""
    request_json = await request.json()
    chat_id: int = int(request_json["chat_id"])
    text: str = request_json["text"]
    is_answer: bool = request_json["is_answer"]

    logger.info(f"[INFO]: Received message from bot to user: {chat_id} and text: {text}")

    model = await send_bot_message_to_user(chat_id, text, is_answer)
    return web.Response(body=model.model_dump_json(), content_type="application/json")


async def send_dispatcher_message(request: web.Request) -> web.Response:
    """Send a message from the dispatcher to a user."""
    request_json = await request.json()
    chat_id: int = int(request_json["chat_id"])
    message_id: int = int(request_json["message_id"])
    text: str = request_json["text"]

    response = await send_dispatcher_message_to_user(chat_id, message_id, text)
    return web.Response(body=response.model_dump_json(), content_type="application/json")
