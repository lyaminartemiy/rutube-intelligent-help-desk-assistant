from pydantic_settings import BaseSettings


class EventStoreSettings(BaseSettings):
    base_url: str = "http://hackaton-backend:8080"
    message_endpoint: str = "/api/messages"  # Message sending
    create_session_endpoint: str = "/api/sessions"  # Session creation
    close_session_endpoint: str = "/api/sessions/close"  # Session close
