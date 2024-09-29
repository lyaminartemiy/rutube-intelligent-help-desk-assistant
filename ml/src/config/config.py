class GPUConfig:
    USE_GPU: bool = True


class QASystemConfig:
    CHROMA_CANDIDATES_COUNT: int = 10
    RERANKER_CANDIDATES_COUNT: int = 3


class ModelsConfig:
    EMBEDDING_MODEL_NAME: str = "text-embedding-ada-002"
    LLM_MODEL_NAME: str = "gpt-4o-mini"
    CROSS_ENCODER_NAME: str = "DiTy/cross-encoder-russian-msmarco"
    CROSS_ENCODER_MAX_LENGTH: int = 512
    CROSS_ENCODER_DEVICE: str = "cpu"
    T5_MODEL_NAME: str = "t5-base"
    GEMMA_MODEL_NAME: str = "Vikhrmodels/Vikhr-Gemma-2B-instruct"


class PromptConfig:
#     ANSWER_PROMPT_TEMPLATE: str = """Ты — система, отвечающая на вопросы пользователей о видеосервисе RUTUBE, используя базу знаний, основанную на документации. Когда пользователь задает вопрос, ты должен:
# Проверить, содержится ли информация, необходимая для ответа, в фрагментах документации.
# Если информация найдена, дай четкий и информативный ответ, основываясь на документации.
# Если информация не найдена или вопрос не связан с документацией, ответь: "Я не знаю."

# Вопрос пользователя:
# {question}

# Фрагмент документации:
# {docs_context}

# Пожалуйста, аггрегируй информацию из документации и используй её для ответа на вопрос.

# Пример использования:
# Вопрос пользователя: Как настроить мониторинг производительности?

# Фрагмент документации: Для настройки мониторинга производительности необходимо выполнить следующие шаги...

# Ответ: Чтобы настроить мониторинг производительности, необходимо выполнить следующие шаги..."""
    ANSWER_PROMPT_TEMPLATE: str = """Ты — система, отвечающая на вопросы пользователей о видеосервисе RUTUBE. 
Учитывая следующий вопрос и фрагмент документации, пожалуйста, ответь на вопрос. Если ты не уверена в ответе, просто скажи 'Я не знаю'.

Вопрос: {question}

Фрагмент документации (вопрос: ответ): {docs_context}"""

    QUESTION_PROMPT_TEMPLATE: str = """Пожалуйста, помоги формализовать следующий вопрос: {question}. Сделай его более четким и лаконичным.

Пример использования:
Вопрос пользователя: Здравствуйте! Можно уточнить причины Правил https://rutube.ru/info/taboo_agreement/ по которым удаляются ролики? что за нарушение правил RUTUBE.

Ответ:
По какой причине могут удалить ролик на RUTUBE?
"""
