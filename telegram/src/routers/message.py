import asyncio
import fastapi
from services.message import send_message_to_user

router = fastapi.APIRouter()


@router.get("/api/send_message")
async def send_message(
    user_id: int,
    text: str,
):
    asyncio.create_task(send_message_to_user(user_id=user_id, text=text))
