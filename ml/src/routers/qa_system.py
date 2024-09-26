from typing import Any, Dict, List

import fastapi
from fastapi import Depends

import lifespan
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain_chroma import Chroma
from langchain_openai import ChatOpenAI
from sentence_transformers import CrossEncoder
from services.qa_system import query_system
from models.schemas import QASystemDTO
from transformers import AutoModelForCausalLM, AutoTokenizer

router = fastapi.APIRouter()


@router.post("/api/qa")
async def get_qa(
    user_input: List[QASystemDTO],
    prompt_template: ChatPromptTemplate = Depends(lifespan.get_prompt_template),
    doc_retriever: Chroma = Depends(lifespan.get_docs_retriever),
    llm: ChatOpenAI = Depends(lifespan.get_llm),
    cross_encoder: CrossEncoder = Depends(lifespan.get_cross_encoder),
    output_parser: StrOutputParser = Depends(lifespan.get_output_parser),
) -> Dict[str, Any]:
    """Get the answer to a question from the question answering system."""
    query_params = {"question": user_input[-1].text}
    qa_response = query_system(
        query_params=query_params,
        prompt_template=prompt_template,
        doc_retriever=doc_retriever,
        llm=llm,
        cross_encoder=cross_encoder,
        output_parser=output_parser,
    )
    return {
        "text": qa_response,
        "is_answer": qa_response != "Я не знаю.",
    }
