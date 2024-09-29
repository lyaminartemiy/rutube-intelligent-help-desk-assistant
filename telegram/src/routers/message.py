from aiohttp import web
from services.message import send_dispatcher_message_to_user


async def send_dispatcher_message(request: web.Request) -> web.Response:
    """
    Send a message from the dispatcher to a user.

    The message is sent to the user with the specified chat_id
    and the response is returned as a JSON object.

    Example of JSON object:
    {
        "chat_id": 123,
        "message_id": 456,
        "text": "Hello, world!"
    }
    """
    # Get the JSON data from the request
    request_json = await request.json()

    # Extract the chat_id, message_id, and text from the JSON data
    chat_id: int = int(request_json["chat_id"])
    message_id: int = int(request_json["message_id"])
    text: str = request_json["text"]

    # Send the message to the user
    response = await send_dispatcher_message_to_user(chat_id, message_id, text)

    # Return the response as a JSON object
    return web.Response(body=response.model_dump_json(), content_type="application/json")
