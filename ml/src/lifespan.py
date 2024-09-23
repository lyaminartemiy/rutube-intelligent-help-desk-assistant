from loguru import logger
import contextlib
import os
import sys

import fastapi

from config.config import ModelsConfig, PromptConfig, QASystemConfig
from dotenv import load_dotenv
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain_chroma import Chroma
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from sentence_transformers import CrossEncoder
from utils.embeddings import generate_embeddings

__import__("sqlite3")


@contextlib.asynccontextmanager
async def lifespan(app: fastapi.FastAPI):
    load_dotenv()
    logger.info("Переменные окружения загружены")

    sys.modules["sqlite3"] = sys.modules.pop("sqlite3")

    embeddings_model = OpenAIEmbeddings(model=ModelsConfig.EMBEDDING_MODEL_NAME)
    app.state.embeddings_model = embeddings_model
    logger.info("Модель для создания эмбеддингов загружена")

    generate_embeddings(embeddings_model)
    logger.info("Эмбеддинги сгенерированы")

    app.state.prompt_template = ChatPromptTemplate.from_template(PromptConfig.PROMPT_TEMPLATE)
    logger.info("Промпт создан")

    app.state.docs_retriever = Chroma(
        persist_directory=os.getenv("CHROMA_DB"),
        embedding_function=embeddings_model,
    ).as_retriever(search_kwargs={"k": QASystemConfig.CANDIDATES_COUNT})
    logger.info("Хрома создана")

    app.state.llm = ChatOpenAI(model_name=ModelsConfig.LLM_MODEL_NAME)
    logger.info("LLM создана")

    app.state.cross_encoder = CrossEncoder(
        model_name=ModelsConfig.CROSS_ENCODER_NAME,
        max_length=ModelsConfig.CROSS_ENCODER_MAX_LENGTH,
        device=ModelsConfig.CROSS_ENCODER_DEVICE,
    )
    logger.info("Кросс-энкодер создан")

    app.state.output_parser = StrOutputParser()
    logger.info("Парсер создан")

    yield


def get_embeddings_model(request: fastapi.Request) -> OpenAIEmbeddings:
    return request.app.state.embeddings_model


def get_prompt_template(request: fastapi.Request) -> ChatPromptTemplate:
    return request.app.state.prompt_template


def get_docs_retriever(request: fastapi.Request) -> Chroma:
    return request.app.state.docs_retriever


def get_llm(request: fastapi.Request) -> ChatOpenAI:
    return request.app.state.llm


def get_cross_encoder(request: fastapi.Request) -> CrossEncoder:
    return request.app.state.cross_encoder


def get_output_parser(request: fastapi.Request) -> StrOutputParser:
    return request.app.state.output_parser
