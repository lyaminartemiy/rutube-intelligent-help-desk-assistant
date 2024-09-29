import fastapi
from fastapi import Depends

import lifespan
from langchain_chroma import Chroma
from models.schemas import Request, Response
from sentence_transformers import CrossEncoder
from services.qa_system import query_system
from transformers import AutoModelForCausalLM, AutoTokenizer

router = fastapi.APIRouter()


@router.post("/predict", response_model=Response)
async def predict_sentiment(
    request: Request,
    doc_retriever: Chroma = Depends(lifespan.get_docs_retriever),
    tokenizer: AutoTokenizer = Depends(lifespan.get_llm_tokenizer),
    model: AutoModelForCausalLM = Depends(lifespan.get_llm_model),
    cross_encoder: CrossEncoder = Depends(lifespan.get_cross_encoder),
) -> Response:
    """
    Query the question answering system.

    Args:
        request: The request body containing the user's question.
        question_prompt_template: The template to use for generating the question prompt.
        answer_prompt_template: The template to use for generating the answer prompt.
        doc_retriever: The document retriever to use.
        tokenizer: The tokenizer to use.
        model: The model to use.
        cross_encoder: The cross-encoder to use.
        output_parser: The output parser to use.

    Returns:
        A dict with the following keys:
            - answer: The answer to the user's question.
            - class_1: The class of the first document that was retrieved.
            - class_2: The class of the second document that was retrieved.
    """
    # Get the question from the request body.
    query_params = {"question": request.question}
    # Query the QA system.
    qa_response = query_system(
        query_params=query_params,
        doc_retriever=doc_retriever,
        tokenizer=tokenizer,
        model=model,
        cross_encoder=cross_encoder,
    )
    # Get the answer from the QA system.
    answer = qa_response.get("result", "")
    # Get the metadata from the QA system.
    docs_metadata = qa_response.get("docs_metadata", None)
    # Return the answer and the classes.
    return Response(
        answer=answer,
        class_1=docs_metadata.get("class_1", ""),
        class_2=docs_metadata.get("class_2", ""),
    )


@router.post("/predict/test")
async def predict_sentiment(
    request: Request,
    doc_retriever: Chroma = Depends(lifespan.get_docs_retriever),
    tokenizer: AutoTokenizer = Depends(lifespan.get_llm_tokenizer),
    model: AutoModelForCausalLM = Depends(lifespan.get_llm_model),
    cross_encoder: CrossEncoder = Depends(lifespan.get_cross_encoder),
) -> dict:
    """
    Query the question answering system.

    Args:
        request: The request body containing the user's question.
        question_prompt_template: The template to use for generating the question prompt.
        answer_prompt_template: The template to use for generating the answer prompt.
        doc_retriever: The document retriever to use.
        tokenizer: The tokenizer to use.
        model: The model to use.
        cross_encoder: The cross-encoder to use.
        output_parser: The output parser to use.

    Returns:
        A dict with the following keys:
            - answer: The answer to the user's question.
            - class_1: The class of the first document that was retrieved.
            - class_2: The class of the second document that was retrieved.
            - vector_database_result: The result of the vector database search.
            - reranker_result: The result of the reranking.
    """
    # Get the question from the request body.
    query_params = {"question": request.question}
    # Query the QA system.
    qa_response = query_system(
        query_params=query_params,
        doc_retriever=doc_retriever,
        tokenizer=tokenizer,
        model=model,
        cross_encoder=cross_encoder,
    )
    # Get the answer from the QA system.
    answer = qa_response.get("result", "")
    # Get the metadata from the QA system.
    docs_metadata = qa_response.get("docs_metadata", None)

    # Get the result of the vector database search.
    vector_database_result = qa_response.get("vector_database_result", "")
    # Get the result of the reranking.
    reranker_result = [
        (item[0], float(item[1])) for item in qa_response.get("reranker_result", "")
    ]

    # Return the answer and the classes.
    return dict(
        answer=answer,
        class_1=docs_metadata.get("class_1", ""),
        class_2=docs_metadata.get("class_2", ""),
        vector_database_result=vector_database_result,
        reranker_result=reranker_result,
    )
