from typing import Any, Dict

from config.config import QASystemConfig
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain_chroma import Chroma
from langchain_core.runnables import (RunnableLambda, RunnableParallel,
                                      RunnablePassthrough)
from langchain_openai import ChatOpenAI
from sentence_transformers import CrossEncoder
from utils.qa_system import extract_documents, rerank_documents


def query_system(
    query_params: Dict[str, Any],
    prompt_template: ChatPromptTemplate,
    docs_retriever: Chroma,
    llm: ChatOpenAI,
    cross_encoder: CrossEncoder,
    output_parser: StrOutputParser,
) -> Dict[str, Any]:
    """Query the question answering system."""
    question = query_params.get("question", "")

    docs_retriever = RunnableParallel(
        {"docs_context": docs_retriever, "question": RunnablePassthrough()}
    )
    rerank = RunnableLambda(
        lambda x: rerank_documents(
            info=x, k=3, cross_encoder=cross_encoder
        )
    )
    extract_doc = RunnableLambda(extract_documents)

    docs_retrieval_chain = docs_retriever | rerank
    model_chain = extract_doc | prompt_template | llm | output_parser

    complete_chain = docs_retrieval_chain | RunnablePassthrough.assign(
        result=model_chain
    )
    result = complete_chain.invoke(question)
    return result["result"]
