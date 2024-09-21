from aiohttp import web
from services.message import send_message_to_user


async def send_message(request):
    chat_id = int(request.query.get("chat_id"))
    text = request.query.get("text")
    is_answer = request.query.get("is_answer", False)
    await send_message_to_user(chat_id, text, is_answer)
    return web.Response(text="Message sent")
