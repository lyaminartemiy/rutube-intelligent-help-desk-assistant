import pandas as pd
import numpy as np
from loguru import logger
import contextlib
import os
import sys
import shutil

import fastapi

import torch
from config.config import ModelsConfig, PromptConfig, QASystemConfig
from dotenv import load_dotenv
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain_chroma import Chroma
from sentence_transformers import CrossEncoder
from transformers import pipeline
from utils.embeddings import generate_embeddings, CustomEmbeddings

__import__("sqlite3")


from transformers import AutoModelForCausalLM, AutoTokenizer


@contextlib.asynccontextmanager
async def lifespan(app: fastapi.FastAPI):
    # app.state.tokenizer = AutoTokenizer.from_pretrained(ModelsConfig.GEMMA_MODEL_NAME, use_fast=False)
    # app.state.model = AutoModelForCausalLM.from_pretrained(ModelsConfig.GEMMA_MODEL_NAME, device_map="sequential")
    app.state.tokenizer = AutoTokenizer.from_pretrained("google/gemma-2-9b-it")
    app.state.model = AutoModelForCausalLM.from_pretrained(
        "google/gemma-2-9b-it",
        device_map="cuda",
        torch_dtype=torch.bfloat16,
    )
    logger.info("Gemma model and tokenizer loaded")

    app.state.cross_encoder = CrossEncoder(
        model_name=ModelsConfig.CROSS_ENCODER_NAME,
        max_length=ModelsConfig.CROSS_ENCODER_MAX_LENGTH,
        device=ModelsConfig.CROSS_ENCODER_DEVICE,
    )
    logger.info("Cross-encoder created")

    load_dotenv()
    logger.info("Environment variables loaded")

    sys.modules["sqlite3"] = sys.modules.pop("sqlite3")

    embeddings_model = CustomEmbeddings(model_name="intfloat/multilingual-e5-base")
    app.state.embeddings_model = embeddings_model
    logger.info("Embeddings model loaded")

    app.state.answer_prompt_template = ChatPromptTemplate.from_template(
        PromptConfig.ANSWER_PROMPT_TEMPLATE
    )
    app.state.question_prompt_template = ChatPromptTemplate.from_template(
        PromptConfig.QUESTION_PROMPT_TEMPLATE
    )
    logger.info("Prompt created")

    shutil.rmtree(os.getenv("CHROMA_DB"))
    app.state.docs_retriever = Chroma(
        persist_directory=os.getenv("CHROMA_DB"),
        embedding_function=embeddings_model,
    ).as_retriever(search_kwargs={"k": QASystemConfig.CHROMA_CANDIDATES_COUNT})
    logger.info("Chroma created")

    source_faq = pd.read_parquet(os.getenv("RUTUBE_DOCUMENTS_PATH"))
    logger.info("Source data loaded")

    generate_embeddings(embeddings_model=embeddings_model, source_faq=source_faq)
    logger.info("Embeddings generated")

    app.state.output_parser = StrOutputParser()
    logger.info("Output parser created")

    yield


def get_embeddings_model(request: fastapi.Request) -> CustomEmbeddings:
    return request.app.state.embeddings_model


def get_question_prompt_template(request: fastapi.Request) -> ChatPromptTemplate:
    return request.app.state.question_prompt_template


def get_answer_prompt_template(request: fastapi.Request) -> ChatPromptTemplate:
    return request.app.state.answer_prompt_template


def get_docs_retriever(request: fastapi.Request) -> Chroma:
    return request.app.state.docs_retriever


def get_cross_encoder(request: fastapi.Request) -> CrossEncoder:
    return request.app.state.cross_encoder


def get_output_parser(request: fastapi.Request) -> StrOutputParser:
    return request.app.state.output_parser


def get_gemma_model(request: fastapi.Request) -> AutoModelForCausalLM:
    return request.app.state.model


def get_gemma_tokenizer(request: fastapi.Request) -> AutoTokenizer:
    return request.app.state.tokenizer
