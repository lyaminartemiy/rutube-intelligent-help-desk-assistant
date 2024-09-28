from typing import Any, Dict, List

import fastapi
from fastapi import Depends

import lifespan
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain_chroma import Chroma
from sentence_transformers import CrossEncoder
from services.qa_system import query_system
from models.schemas import QASystemDTO, Request, Response
from transformers import AutoModelForCausalLM, AutoTokenizer

router = fastapi.APIRouter()


@router.post("/predict")
async def predict_sentiment(
    request: Request,
    question_prompt_template: ChatPromptTemplate = Depends(lifespan.get_question_prompt_template),
    answer_prompt_template: ChatPromptTemplate = Depends(lifespan.get_answer_prompt_template),
    doc_retriever: Chroma = Depends(lifespan.get_docs_retriever),
    tokenizer: AutoTokenizer = Depends(lifespan.get_gemma_tokenizer),
    model: AutoModelForCausalLM = Depends(lifespan.get_gemma_model),
    cross_encoder: CrossEncoder = Depends(lifespan.get_cross_encoder),
    output_parser: StrOutputParser = Depends(lifespan.get_output_parser),
) -> dict:
    query_params = {"question": request.question}
    qa_response = query_system(
        query_params=query_params,
        question_prompt_template=question_prompt_template,
        answer_prompt_template=answer_prompt_template,
        doc_retriever=doc_retriever,
        tokenizer=tokenizer,
        model=model,
        cross_encoder=cross_encoder,
        output_parser=output_parser,
    )
    answer = qa_response.get("result", "")
    docs_metadata = qa_response.get("docs_metadata", [None])[0]
    
    vector_database_result = qa_response.get("vector_database_result", "")
    reranker_result = [
        (item[0], float(item[1])) for item in qa_response.get("reranker_result", "")
    ]

    print("ANSWER:", answer)
    print("METADATA:", docs_metadata)
    print("VECTOR DATABASE RESULT:", vector_database_result)
    print("RERANKER RESULT:", reranker_result)

    response = dict(
        answer=answer,
        class_1=docs_metadata.get("class_1", ""),
        class_2=docs_metadata.get("class_2", ""),

        vector_database_result=vector_database_result,
        reranker_result=reranker_result,
    )
    return response
