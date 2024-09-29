import contextlib
import os
import shutil
import sys

import fastapi
import pandas as pd
import torch

from config.config import ModelsConfig, QASystemConfig
from dotenv import load_dotenv
from langchain_chroma import Chroma
from sentence_transformers import CrossEncoder
from transformers import AutoModelForCausalLM, AutoTokenizer
from utils.embeddings import CustomEmbeddings, generate_embeddings

__import__("sqlite3")


@contextlib.asynccontextmanager
async def lifespan(app: fastapi.FastAPI):
    """
    FastAPI lifecycle event to load models and data into memory.

    The state of the app is populated with the following:
        - `tokenizer`: `AutoTokenizer` instance for the language model.
        - `model`: `AutoModelForCausalLM` instance for the language model.
        - `cross_encoder`: `CrossEncoder` instance for the cross-encoder.
        - `embeddings_model`: `CustomEmbeddings` instance for generating embeddings.
        - `docs_retriever`: `Chroma` instance for retrieving documents.
    """
    # Load the language model and cross-encoder
    app.state.tokenizer = AutoTokenizer.from_pretrained(
        ModelsConfig.LLM_MODEL_NAME, use_fast=False
    )
    app.state.model = AutoModelForCausalLM.from_pretrained(
        ModelsConfig.LLM_MODEL_NAME,
        device_map="sequential",
        torch_dtype=torch.float16,
    )

    app.state.cross_encoder = CrossEncoder(
        model_name=ModelsConfig.CROSS_ENCODER_NAME,
        max_length=ModelsConfig.CROSS_ENCODER_MAX_LENGTH,
        device=ModelsConfig.CROSS_ENCODER_DEVICE,
    )

    # Load environments parameters
    load_dotenv()

    sys.modules["sqlite3"] = sys.modules.pop("sqlite3")

    # Load the embeddings model
    embeddings_model = CustomEmbeddings(model_name=ModelsConfig.EMBEDDING_MODEL_NAME)
    app.state.embeddings_model = embeddings_model

    # Initialize the Chroma retriever
    shutil.rmtree(os.getenv("CHROMA_DB"))
    app.state.docs_retriever = Chroma(
        persist_directory=os.getenv("CHROMA_DB"),
        embedding_function=embeddings_model,
    ).as_retriever(search_kwargs={"k": QASystemConfig.CHROMA_CANDIDATES_COUNT})

    # Generate embeddings for the documents
    source_faq = pd.read_parquet(os.getenv("RUTUBE_DOCUMENTS_PATH"))
    generate_embeddings(embeddings_model=embeddings_model, source_faq=source_faq)

    yield


def get_embeddings_model(request: fastapi.Request) -> CustomEmbeddings:
    """
    Get the embeddings model from the request's app state.

    Args:
    - request (fastapi.Request): The request containing the app state.

    Returns:
    - CustomEmbeddings: The embeddings model.
    """
    return request.app.state.embeddings_model


def get_docs_retriever(request: fastapi.Request) -> Chroma:
    """
    Get the document retriever from the request's app state.

    Args:
    - request (fastapi.Request): The request containing the app state.

    Returns:
    - Chroma: The document retriever.
    """
    return request.app.state.docs_retriever


def get_cross_encoder(request: fastapi.Request) -> CrossEncoder:
    """
    Get the cross encoder from the request's app state.

    Args:
    - request (fastapi.Request): The request containing the app state.

    Returns:
    - CrossEncoder: The cross encoder.
    """
    return request.app.state.cross_encoder


def get_llm_model(request: fastapi.Request) -> AutoModelForCausalLM:
    """
    Get the large language model from the request's app state.

    Args:
    - request (fastapi.Request): The request containing the app state.

    Returns:
    - AutoModelForCausalLM: The large language model.
    """
    return request.app.state.model


def get_llm_tokenizer(request: fastapi.Request) -> AutoTokenizer:
    """
    Get the large language model's tokenizer from the request's app state.

    Args:
    - request (fastapi.Request): The request containing the app state.

    Returns:
    - AutoTokenizer: The tokenizer for the large language model.
    """
    return request.app.state.tokenizer
