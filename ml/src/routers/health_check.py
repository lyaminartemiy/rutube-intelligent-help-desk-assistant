import fastapi

router = fastapi.APIRouter()


@router.get("/")
def index():
    return {"text": "Интеллектуальный помощник оператора службы поддержки."}
