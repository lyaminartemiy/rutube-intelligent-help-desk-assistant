from typing import Union, Dict, Any

import aiohttp


async def post_question_in_ai_service(question: str) -> None:
    """Log an event to the AI Service."""
    async with aiohttp.ClientSession() as session:
        async with session.post(
            url="http://176.109.100.31:8001/predict",
            json={"question": question},
            headers={"Content-Type": "application/json"},
            timeout=120 * 1000,
        ) as response:
            response_json = await response.json()
            print("RESPONSE:", response_json)
            return response_json
