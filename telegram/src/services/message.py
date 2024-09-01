from services.bot.utils.launcher import bot


async def send_message_to_user(user_id, text):
    try:
        await bot.send_message(user_id, text)
        print(f"Сообщение отправлено пользователю с ID {user_id}")
    except Exception as e:
        print(f"Ошибка при отправке сообщения: {e}")
