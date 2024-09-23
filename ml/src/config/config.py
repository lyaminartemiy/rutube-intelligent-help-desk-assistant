class QASystemConfig:
    CANDIDATES_COUNT: int = 3


class ModelsConfig:
    EMBEDDING_MODEL_NAME: str = "text-embedding-3-small"
    LLM_MODEL_NAME: str = "gpt-4o-mini"
    CROSS_ENCODER_NAME: str = "DiTy/cross-encoder-russian-msmarco"
    CROSS_ENCODER_MAX_LENGTH: int = 512
    CROSS_ENCODER_DEVICE: str = "cpu"


class PromptConfig:
    PROMPT_TEMPLATE: str = """Ты — помощник по документам системы мониторинга. Твоя задача — отвечать на вопросы пользователей, используя предоставленные куски документации.

Вопрос пользователя:
{question}

Кусок документации:
{docs_context}


Пожалуйста, аггрегируй информацию из документации и используй её для ответа на вопрос. Если ты не знаешь ответа или не уверена, просто скажи: "Я не знаю".

Пример использования:

Вопрос пользователя: Как настроить мониторинг производительности?
Кусок документации: Для настройки мониторинга производительности необходимо выполнить следующие шаги...

Ответ: Чтобы настроить мониторинг производительности, необходимо выполнить следующие шаги..."""
