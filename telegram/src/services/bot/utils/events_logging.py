from typing import Union, Dict, Any

import aiohttp
from loguru import logger

from aiogram import types
from config.config import EventStoreSettings
from models import schemas

event_store_settings = EventStoreSettings()


def clean_params(**kwargs) -> Dict[str, Any]:
    """
    Clean up the parameters by removing any keys with None values.

    This function takes in a dictionary of keyword arguments and returns a new
    dictionary with the same keys and values, but with any keys that have a value
    of None removed.

    Args:
        **kwargs: A dictionary of keyword arguments.

    Returns:
        A new dictionary with the same keys and values, but with any keys that
        have a value of None removed.
    """
    return {key: value for key, value in kwargs.items() if value is not None}


def _create_new_session_model(message: types.Message) -> schemas.CreateSessionDTO:
    """
    Create a new session model.

    This function takes in a message from the user and returns a new session
    model with the chat ID set to the user's ID.

    Args:
        message: A message from the user.

    Returns:
        A new session model with the chat ID set to the user's ID.
    """
    return schemas.CreateSessionDTO(chat_id=str(message.from_user.id))


def _create_user_message_model(
    message: types.Message, ai_text: str
) -> schemas.UpdateMessageDTO:
    """
    Create a user message model.

    This function takes in a message from the user and the text of the AI response
    and returns a user message model with the chat ID, message ID, text of the
    message, text of the AI response, created at time, and is helpful set to None.

    Args:
        message: A message from the user.
        ai_text: The text of the AI response.

    Returns:
        A user message model with the chat ID, message ID, text of the message,
        text of the AI response, created at time, and is helpful set to None.
    """
    return schemas.UpdateMessageDTO(
        chat_id=str(message.chat.id),
        message_id=str(message.message_id),
        text=message.text,
        ai_text=ai_text,
        created_at=str(message.date.astimezone().isoformat()),
        is_helpful=None,
    )


def _create_bot_message_model(message: types.Message) -> schemas.UpdateMessageDTO:
    """
    Create a bot message model.

    This function takes in a message from the bot and returns a bot message model
    with the chat ID, message ID, text of the message, text of the AI response,
    created at time, and is helpful set to None.

    Args:
        message: A message from the bot.

    Returns:
        A bot message model with the chat ID, message ID, text of the message,
        text of the AI response, created at time, and is helpful set to None.
    """
    print("[INFO]: MESSAGE_ID: ", message.message_id)
    return schemas.UpdateMessageDTO(
        chat_id=str(message.chat.id),
        message_id=str(message.message_id),
        text=None,
        ai_text=None,
        created_at=None,
        is_helpful=None,
    )


def _create_feedback_message(
    message: Union[types.Message, types.CallbackQuery], is_positive: bool
) -> schemas.UpdateMessageDTO:
    """
    Create a feedback message model.

    This function takes in a message from the user (either a message or a callback
    query) and a boolean indicating whether the feedback was positive or not.
    It returns a feedback message model with the chat ID, message ID, text of the
    message, text of the AI response, created at time, and is helpful set to the
    value of the boolean.

    Args:
        message: A message from the user (either a message or a callback query).
        is_positive: A boolean indicating whether the feedback was positive or not.

    Returns:
        A feedback message model with the chat ID, message ID, text of the message,
        text of the AI response, created at time, and is helpful set to the value
        of the boolean.
    """
    chat_id = message.chat.id if isinstance(message, types.Message) else message.message.chat.id
    message_id = message.message_id if isinstance(message, types.Message) else message.message.message_id

    return schemas.UpdateMessageDTO(
        chat_id=str(chat_id),
        message_id=str(message_id),
        text=None,
        ai_text=None,
        created_at=None,
        is_helpful=is_positive,
    )


async def _log_event_with_params(
    data: Union[schemas.CreateSessionDTO, schemas.UpdateMessageDTO], endpoint: str
) -> None:
    """
    Log an event to the event store.

    This function takes in a model and an endpoint and logs the event to the
    event store using the POST method with the model as query parameters.

    Args:
        data: The model to log.
        endpoint: The endpoint to log to.
    """
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
    """
    Log an event to the event store.

    This function takes in a model and an endpoint and logs the event to the
    event store using the POST method with the model as JSON data.

    Args:
        data: The model to log.
        endpoint: The endpoint to log to.
    """
    # Clean the params of the model
    message_data = clean_params(**data.model_dump())
    print("message_data:", message_data)

    # Log the event to the event store
    async with aiohttp.ClientSession() as session:
        async with session.post(
            url=endpoint,
            json=message_data,
            headers={"Content-Type": "application/json"},
        ) as response:
            # If the response status is not 200, log an error
            if response.status != 200:
                logger.error(f"Failed to log event with json: {message_data}")
                logger.exception(response)


def camel_case(snake_str: str) -> str:
    """
    Convert a snake case string to camel case.

    This function takes in a string in snake case and returns a string in camel
    case. It splits the string into components separated by underscores,
    capitalizes the first letter of each component (except the first one), and
    then joins them together with no separator.

    :param snake_str: The string in snake case to convert.
    :return: The string in camel case.
    """
    components = snake_str.split("_")
    return components[0] + "".join(x.title() for x in components[1:])


class EventsLogger:
    """
    Class for logging events to the event store.

    This class contains methods for logging new sessions, user messages, bot messages,
    and user feedback to the event store.
    """

    @staticmethod
    async def log_new_bot_session(message: types.Message) -> None:
        """
        Log a new session to the event store.

        :param message: A message from the user.
        """
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
    async def log_user_message(message: types.Message, ai_text: str) -> None:
        """
        Log a new user message to the event store.

        :param message: A message from the user.
        :param ai_text: The text of the AI response.
        """
        message_data = _create_user_message_model(message, ai_text)
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
        """
        Log a new bot message to the event store.

        :param message: A message from the bot.
        """
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
        """
        Log a user's feedback to the event store.

        :param callback_query: A callback query from the user.
        :param is_positive: Whether the feedback was positive or not.
        """
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
