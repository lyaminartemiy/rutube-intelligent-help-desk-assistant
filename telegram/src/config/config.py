from pydantic_settings import BaseSettings


class EventStoreSettings(BaseSettings):
    """
    Settings for event store service.

    Attributes:
        BASE_URL (str): Base URL of event store service.
        MESSAGE_ENDPOINT (str): Endpoint for message sending.
        CREATE_BOT_SESSION_ENDPOINT (str): Endpoint for bot session creation.
        CREATE_DP_SESSION_ENDPOINT (str): Endpoint for dispatcher session creation.
    """
    BASE_URL: str = "http://hackaton-backend:8080"
    MESSAGE_ENDPOINT: str = "/api/messages"  # Message Sending
    CREATE_BOT_SESSION_ENDPOINT: str = "/api/sessions/bot"  # Bot Session Creation
    CREATE_DP_SESSION_ENDPOINT: str = "/api/sessions/dp"  # Dispatcher Session Creation


class AIServiceSettings(BaseSettings):
    """
    Settings for AI service.

    Attributes:
        BASE_URL (str): Base URL of AI service.
        QUESTION_ENDPOINT (str): Endpoint for question sending.
        HEALTH_STATUS_ENDPOINT (str): Endpoint for health status sending.
    """
    BASE_URL: str = "http://176.109.100.31:8001"
    QUESTION_ENDPOINT: str = "/predict"  # Question Sending
    HEALTH_STATUS_ENDPOINT: str = "/"  # Health Status Sending
