from pydantic_settings import BaseSettings


class EventStoreSettings(BaseSettings):
    store_url: str = "https://example.com/api"
