# Rutube Техподдержка 

Микросервисная система, позволяющая эффективно обрабатывать запросы от пользователей

## Требования

- Docker
- CUDA GPU (32G+ e.g. V100)

## Архитектура контейнеров
### WebUI
Веб-интерфейс работника техподдржки и бизнес-администратора. Позволяет обрабатывать входящие в бот запросы

- Angular
- TypeScript

### Backend

- Java 21
- Spring Boot
- MinIO (S3)
- Micrometer

### Model

- TODO ML
- torch
- FastApi
- ChormaDB

### Telegram Bot

- python-telegram-bot

### Инфрастукрутные контейнеры
- Prometheus - База данных временных рядов для метрик
- Grafana - визуализатор метрик
- PostgreSQL - Реляционная БД для хранения 

## Запуск с Docker compose

1. Склонируйте репозиторий
```shell
git clone  https://github.com/lyaminartemiy/rutube-intelligent-assistant.git
```

2. Создайте `.env` файл для указания токена телеграм-бота:
```
TG_TOKEN=123123123123123123
```
3. Скопируйте файл по пути `rutube-intelligent-assistant/ml`

```shell
cp .env rutube-intelligent-assistant/ml/.env
```
4. Запустите сборку проекта
```shell
docker compose up --build
```

**Веб-сервис будет доступен по адресу http://localhost:4200/**

## Документация 

Документация к бизнес-API будет доступна по адресу: http://localhost:8080/swagger-ui/index.html

# Endpoints

### Authentication

#### 1. Send Signup Data
- **URL**: /auth/signup/send
- **Method**: POST
- **Summary**: Sends registration data to a provided email.
- **Request Body**:
    - fullName: string
    - email: string
    - role: enum (ADMIN, TECH_SUPPORT_EMPLOYEE)
- **Responses**:
    - 200: Data successfully sent.
    - 400: Invalid registration data.
    - 500: Server error.

#### 2. Login
- **URL**: /auth/login
- **Method**: POST
- **Summary**: Logs in the user and returns an access token.
- **Request Body**:
    - username: string
    - password: string
- **Responses**:
    - 200: Successful login.
    - 401: Invalid credentials.
    - 500: Server error.

### Session Management

#### 1. Create New Session
- **URL**: /api/sessions/bot
- **Method**: POST
- **Summary**: Creates a new session for a specified chat.
- **Parameters**:
    - chatId: string (required)
- **Responses**:
    - 200: Session created.
    - 400: Invalid chat ID.
    - 500: Server error.

#### 2. Send Session to Tech Support
- **URL**: /api/sessions/dp
- **Method**: POST
- **Summary**: Sends the specified session to technical support.
- **Parameters**:
    - chatId: string (required)
- **Responses**:
    - 200: Session successfully sent.
    - 404: Session not found.
    - 500: Server error.

### Tech Support Requests

#### 1. Send Message to Dialogue
- **URL**: /api/requests/{requestId}/send
- **Method**: POST
- **Summary**: Sends a message in the dialogue for the specified request.
- **Parameters**:
    - requestId: integer (required)
- **Request Body**:
    - text: string
    - isEditedByTechSupport: boolean
- **Responses**:
    - 200: Message sent.
    - 400: Invalid message text.
    - 404: Request not found.
    - 500: Server error.

#### 2. Close Request
- **URL**: /api/requests/{requestId}/close
- **Method**: POST
- **Summary**: Closes the specified request.
- **Parameters**:
    - requestId: integer (required)
- **Responses**:
    - 200: Request closed.
    - 404: Request not found.
    - 500: Server error.

#### 3. Assign Employee to Request
- **URL**: /api/requests/{requestId}/assign
- **Method**: POST
- **Summary**: Assigns an employee to a request.
- **Parameters**:
    - requestId: integer (required)
- **Responses**:
    - 200: Employee assigned.

### Employee Management

#### 1. Full-Text Search Employees
- **URL**: /api/employees/search
- **Method**: GET
- **Summary**: Returns a list of employees matching search criteria.
- **Parameters**:
    - search: string (required)
- **Responses**:
    - 200: List of employees.
    - 400: Invalid request.
    - 500: Server error.

#### 2. Get All Employees
- **URL**: /api/employees/all
- **Method**: GET
- **Summary**: Returns a list of all tech support employees.
- **Responses**:
    - 200: List of employees.
    - 500: Server error.
