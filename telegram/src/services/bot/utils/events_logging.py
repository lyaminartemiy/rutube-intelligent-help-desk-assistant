import asyncio
from typing import Union, Dict, Any

import aiohttp
from loguru import logger

from aiogram import types
from config.config import EventStoreSettings
from models import schemas

event_store_settings = EventStoreSettings()


def clean_params(**kwargs) -> Dict[str, Any]:
    """Clean up the parameters by removing any keys with None values."""
    return {key: value for key, value in kwargs.items() if value is not None}


def _create_new_session_model(message: types.Message) -> schemas.CreateSessionDTO:
    """Create a new session model."""
    return schemas.CreateSessionDTO(chat_id=str(message.from_user.id))


def _create_user_message_model(message: types.Message) -> schemas.UpdateMessageDTO:
    """Create a user message model."""
    return schemas.UpdateMessageDTO(
        chat_id=str(message.chat.id),
        message_id=str(message.message_id),
        text=message.text,
        created_at=str(message.date.astimezone().isoformat()),
        is_helpful=None,
    )


def _create_bot_message_model(message: types.Message) -> schemas.UpdateMessageDTO:
    """Create a bot message model."""
    print("[INFO]: MESSAGE_ID: ", message.message_id)
    return schemas.UpdateMessageDTO(
        chat_id=str(message.chat.id),
        message_id=str(message.message_id),
        text=None,
        created_at=None,
        is_helpful=None,
    )


def _create_feedback_message(
    message: Union[types.Message, types.CallbackQuery], is_positive: bool
) -> schemas.UpdateMessageDTO:
    """Create a feedback message model."""
    chat_id = message.chat.id if isinstance(message, types.Message) else message.message.chat.id
    message_id = message.message_id if isinstance(message, types.Message) else message.message.message_id

    return schemas.UpdateMessageDTO(
        chat_id=str(chat_id),
        message_id=str(message_id),
        text=None,
        created_at=None,
        is_helpful=is_positive,
    )


async def _log_event_with_params(
    data: Union[schemas.CreateSessionDTO, schemas.UpdateMessageDTO], endpoint: str
) -> None:
    """Log an event to the event store."""
    async with aiohttp.ClientSession() as session:
        async with session.post(
            url=endpoint,
            params={camel_case(key): value for key, value in clean_params(**data.model_dump()).items()},
        ) as response:
            if response.status != 200:
                logger.error(f"Failed to log event with params: {data.model_dump()}")
                logger.exception(response)


async def _log_event_with_json(
    data: Union[schemas.CreateSessionDTO, schemas.UpdateMessageDTO], endpoint: str
) -> None:
    """Log an event to the event store."""
    message_data = clean_params(**data.model_dump())
    print("message_data:", message_data)
    async with aiohttp.ClientSession() as session:
        print("МЫ В СЕССИИ!")
        async with session.post(
            url=endpoint,
            json=message_data,
            headers={"Content-Type": "application/json"},
        ) as response:
            print(response.status)
            pass


def camel_case(snake_str: str) -> str:
    """Convert a snake case string to camel case."""
    components = snake_str.split("_")
    return components[0] + "".join(x.title() for x in components[1:])


class EventsLogger:
    """Class for logging events to the event store."""

    @staticmethod
    async def log_new_bot_session(message: types.Message) -> None:
        """Log a new session to the event store."""
        new_session = _create_new_session_model(message)
        logger.info(f"Created new session for user: {message.from_user.id}")
        try:
            await _log_event_with_params(
                new_session,
                event_store_settings.BASE_URL
                + event_store_settings.CREATE_BOT_SESSION_ENDPOINT,
            )
        except Exception as exc:
            logger.error(f"Failed to log new session for user: {message.from_user.id}")
            logger.exception(exc)

    @staticmethod
    async def log_new_dp_session(message: types.Message) -> None:
        """Log a new session to the event store."""
        new_session = _create_new_session_model(message)
        logger.info(f"Created new session for user: {message.from_user.id}")
        try:
            await _log_event_with_params(
                new_session,
                event_store_settings.BASE_URL
                + event_store_settings.CREATE_DP_SESSION_ENDPOINT,
            )
        except Exception as exc:
            logger.error(f"Failed to log new session for user: {message.from_user.id}")
            logger.exception(exc)

    @staticmethod
    async def log_user_message(message: types.Message) -> None:
        """Log a new user message to the event store."""
        message_data = _create_user_message_model(message)
        logger.info(f"Logging new message from user: {message.from_user.id}")
        try:
            await _log_event_with_json(
                message_data,
                event_store_settings.BASE_URL + event_store_settings.MESSAGE_ENDPOINT,
            )
        except Exception as exc:
            logger.error(f"Failed to log new message from user: {message.from_user.id}")
            logger.exception(exc)

    @staticmethod
    async def log_new_bot_message(message: types.Message) -> None:
        """Log a new bot message to the event store."""
        message_data = _create_bot_message_model(message)
        print("пытаемся отправить данные для сохранения ботовского смс:", message_data.model_dump())
        logger.info(f"Logging new message from bot to user: {message.from_user.id}")
        try:
            await _log_event_with_json(
                message_data,
                event_store_settings.BASE_URL + event_store_settings.MESSAGE_ENDPOINT,
            )
        except Exception as exc:
            logger.info(
                f"Failed to log new message from bot to user: {message.from_user.id}"
            )
            logger.exception(exc)

    @staticmethod
    async def log_user_feedback(callback_query: types.CallbackQuery, is_positive: bool) -> None:
        """Log a user's feedback to the event store."""
        feedback_data = _create_feedback_message(callback_query, is_positive)
        logger.info(
            f"Logging feedback from user: {callback_query.from_user.id} for message: {callback_query.message.message_id}"
        )
        try:
            await _log_event_with_json(
                feedback_data,
                event_store_settings.BASE_URL + event_store_settings.MESSAGE_ENDPOINT,
            )
        except Exception as exc:
            logger.error(f"Failed to log feedback from user: {callback_query.from_user.id}")
            logger.exception(exc)
