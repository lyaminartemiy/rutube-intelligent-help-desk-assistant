from typing import Union, Dict, Any

import aiohttp

from config import config


async def post_question_in_ai_service(question: str) -> None:
    """Log an event to the AI Service."""
    async with aiohttp.ClientSession() as session:
        async with session.post(
            url=config.AIServiceSettings.BASE_URL + config.AIServiceSettings.QUESTION_ENDPOINT,
            json={"question": question},
            headers={"Content-Type": "application/json"},
        ) as response:
            return response
