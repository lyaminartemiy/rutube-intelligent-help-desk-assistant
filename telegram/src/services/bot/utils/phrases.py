class Phrase:
    START = """Добро пожаловать в поддержку RUTUBE!

Позвольте представить нашего интеллектуального помощника — вашего личного гида, который работает 24/7. Теперь все ваши вопросы решаются всего за несколько кликов.

Для начала работы, пожалуйста, выполните следующие шаги:
1. Нажмите на кнопку "Новая сессия".
2. Задавайте любые вопросы, и я постараюсь ответить на них, используя нашу обширную базу знаний.
3. Если вы хотите начать с нуля, создайте новую сессию.

Не стесняйтесь обращаться за помощью — мы всегда готовы оказать вам содействие!"""

    HELP = """Здесь типа полезный текст"""

    NEW_REPORT = """Вы начали новую сессию с помощником. Какой у Вас вопрос?"""

    ANSWER_ASSESMENT = "Пожалуйста, оцените ответ на ваш вопрос:"

    POSITIVE_FEEDBACK = "😊 Полезно"

    NEGATIVE_FEEDBACK = "😕 Не совсем"

    POSITIVE_ANSWER = (
        "Спасибо за положительный отзыв! Если остались вопросы — смело задавайте!"
    )

    NEGATIVE_ANSWER = "Спасибо за отзыв! Мы учтем это и постараемся улучшить наш сервис. Попробуйте задать уточняющий вопрос!"

    NO_REPONSE = "Не понял о чем Вы, попробуйте задать вопрос ещё раз!"

    DISPATCHER_WAITING = """Похоже, что у вас возникли трудности. Перевожу Вас на диспетчера, ожидайте ответа. **ВАЖНО:** При создании новой сессии, вызов диспетчера отменяется.

Если проблема решена - можете начать новую сессию при наличии других вопросов!"""

    DISPATCHER_FINISH = "Сессия с диспетчером завершена. Спасибо за обращение! Если проблема решена - можете начать новую сессию при наличии других вопросов!"
