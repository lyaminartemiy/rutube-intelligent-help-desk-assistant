from pydantic import BaseModel


class Request(BaseModel):
    """
    Request object for the QA system.

    Attributes:
        question (str): The question asked by the user.
    """
    question: str


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
