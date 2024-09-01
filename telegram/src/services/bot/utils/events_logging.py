import functools
import requests
from aiogram import types
from models.schemas import NewSession, NewMessage
from config.config import EventStoreSettings


def create_new_session(message: types.Message) -> NewSession:
    new_session = NewSession(chat_id=message.chat.id)
    return new_session


def create_new_message(message: types.Message) -> NewMessage:
    new_message = NewMessage(
        chat_id=str(message.chat.id),
        text=message.text,
        created_at=str(message.date),
        addtitional_content=None,
    )
    return new_message


def log_new_session(func):
    @functools.wraps(func)
    async def wrapper(message: types.Message, *args, **kwargs):
        data = create_new_session(message).model_dump()
        
        # TODO: раскомментить для проверки отправки events
        # requests.post(
        #     EventStoreSettings.store_url,
        #     data=data,
        #     headers={"Content-Type": "application/json"},
        # )
        print(f"LOG_NEW_SESSION: {data}")
        
        await func(message, *args, **kwargs)
    
    return wrapper


def log_new_message(func):
    @functools.wraps(func)
    async def wrapper(message: types.Message, *args, **kwargs):
        data = create_new_message(message).model_dump()
        
        # TODO: раскомментить для проверки отправки events
        # requests.post(
        #     EventStoreSettings.store_url,
        #     data=data,
        #     headers={"Content-Type": "application/json"},
        # )
        print(f"LOG_NEW_MESSAGE: {data}")
        
        await func(message, *args, **kwargs)
    
    return wrapper
