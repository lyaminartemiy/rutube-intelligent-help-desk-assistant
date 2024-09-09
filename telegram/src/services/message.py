from services.bot.utils.launcher import bot


async def send_message_to_user(user_id, text):
    await bot.send_message(user_id, text)
    print(f"Сообщение отправлено пользователю с ID {user_id}")
