from typing import Union, Dict, Any

import aiohttp

from config import config


async def post_question_in_ai_service(question: str) -> None:
    """Log an event to the AI Service."""
    url = config.AIServiceSettings.BASE_URL + config.AIServiceSettings.QUESTION_ENDPOINT
    print("url: ", url)
    async with aiohttp.ClientSession() as session:
        async with session.post(
            url=url,
            json={"question": question},
            headers={"Content-Type": "application/json"},
        ) as response:
            return response
