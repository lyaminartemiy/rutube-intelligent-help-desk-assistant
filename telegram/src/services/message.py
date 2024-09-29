from aiogram import types
from services.bot.launcher import bot
from services.bot.utils.phrases import Phrase
from models import schemas


async def send_dispatcher_message_to_user(
    chat_id: str,
    message_id: str,
    text: str,
) -> schemas.UpdateMessageDTO:
    """
    Send a message from the dispatcher to a user.

    The message is sent to the user with the specified chat_id
    and message_id. The response is returned as a JSON object.
    """
    keyboard = types.InlineKeyboardMarkup(row_width=2)
    # Add the positive and negative feedback buttons to the message
    keyboard.add(
        types.InlineKeyboardButton(
            text=Phrase.POSITIVE_FEEDBACK, callback_data="positive_feedback"
        ),
        types.InlineKeyboardButton(
            text=Phrase.NEGATIVE_FEEDBACK, callback_data="negative_feedback"
        ),
    )

    # Send the message to the user
    message = await bot.send_message(
        chat_id=int(chat_id),
        text=text,
        reply_markup=keyboard,
        reply_to_message_id=message_id,
    )

    # Return the response as a JSON object
    return schemas.UpdateMessageDTO(
        chat_id=str(message.chat.id),
        message_id=str(message.message_id),
        text=None,
        created_at=None,
        is_helpful=None,
        ai_text=None,
    )
