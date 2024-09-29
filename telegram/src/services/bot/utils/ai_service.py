from typing import Dict, Any

import aiohttp


async def post_question_in_ai_service(question: str) -> Dict[str, Any]:
    """
    Post a question to the AI Service and return the response.

    The question is sent to the AI Service using the POST method.
    The response is returned as a JSON object.

    Args:
        question (str): The question to ask the AI Service.

    Returns:
        Dict[str, Any]: The response from the AI Service.
    """
    async with aiohttp.ClientSession() as session:
        async with session.post(
            url="http://176.109.100.31:8001/predict",
            json={"question": question},
            headers={"Content-Type": "application/json"},
            timeout=120 * 1000,
        ) as response:
            response_json = await response.json()
            return response_json
