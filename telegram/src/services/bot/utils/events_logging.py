from typing import Callable
import aiohttp
from aiogram import types
from models.schemas import NewSession, NewMessage
from config.config import EventStoreSettings
from loguru import logger


event_store_settings = EventStoreSettings()


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


async def log_new_message(endpoint: str, message: dict):
    data = create_new_message(message).model_dump()
    logger.info(f"INFO: data - {data}")
    async with aiohttp.ClientSession() as session:
        async with session.post(f"{event_store_settings.base_url}{endpoint}", json=data) as response:
            if response.status != 200:
                logger.info(f"Ошибка отправки запроса: {response.status}")


async def log_new_session(endpoint: str, message: dict):
    data = create_new_session(message).model_dump()
    logger.info(f"INFO: data - {data}")
    async with aiohttp.ClientSession() as session:
        async with session.post(f"{event_store_settings.base_url}{endpoint}", json=data) as response:
            if response.status != 200:
                logger.info(f"Ошибка отправки запроса: {response.status}")
