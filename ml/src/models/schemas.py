from typing import Optional

from pydantic import BaseModel


class QASystemDTO(BaseModel):
    """
    Data Transfer Object (DTO) for storing and retrieving QA system data.

    Attributes:
        text (str): The text of the question or answer.
        created_at (str): The timestamp when the question or answer was created.
        side (str): The side of the conversation (user or assistant).
        is_helpful (Optional[bool]): Whether the answer was helpful or not.
    """


class Request(BaseModel):
    """
    Request object for the QA system.

    Attributes:
        question (str): The question asked by the user.
    """


class Response(BaseModel):
    """
    Response object for the QA system.

    Attributes:
        answer (str): The answer to the user's question.
        class_1 (str): The first class of the question.
        class_2 (str): The second class of the question.
    """

    answer: str
    class_1: str
    class_2: str
