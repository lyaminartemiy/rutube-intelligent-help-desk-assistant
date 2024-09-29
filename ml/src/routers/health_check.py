import fastapi

router = fastapi.APIRouter()


@router.get("/")
def index():
    """
    Return a simple string with a description of the service.
    """
    return {"text": "Интеллектуальный помощник оператора службы поддержки."}
