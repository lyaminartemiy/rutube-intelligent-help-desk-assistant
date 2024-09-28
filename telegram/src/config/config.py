from pydantic_settings import BaseSettings


class EventStoreSettings(BaseSettings):
    BASE_URL: str = "http://hackaton-backend:8080"
    MESSAGE_ENDPOINT: str = "/api/messages"  # Message Sending
    CREATE_BOT_SESSION_ENDPOINT: str = "/api/sessions/bot"  # Bot Session Creation
    CREATE_DP_SESSION_ENDPOINT: str = "/api/sessions/dp"  # Dispatcher Session Creation


class AIServiceSettings(BaseSettings):
    BASE_URL: str = "http://176.109.100.31:8001"
    QUESTION_ENDPOINT: str = "/predict"  # Question Sending
    HEALTH_STATUS_ENDPOINT: str = "/"  # Health Status Sending
