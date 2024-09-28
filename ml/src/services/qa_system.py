from typing import Any, Dict

from config.config import QASystemConfig
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain_chroma import Chroma
from langchain_core.runnables import (
    RunnableLambda,
    RunnableParallel,
    RunnablePassthrough,
)
from langchain_openai import ChatOpenAI
from sentence_transformers import CrossEncoder
from utils.qa_system import rerank_documents

from transformers import AutoModelForCausalLM, AutoTokenizer
from config.config import ModelsConfig, PromptConfig


def query_system(
    query_params: Dict[str, Any],
    question_prompt_template: ChatPromptTemplate,
    answer_prompt_template: ChatPromptTemplate,
    doc_retriever: Chroma,
    llm: ChatOpenAI,
    cross_encoder: CrossEncoder,
    output_parser: StrOutputParser,
) -> Dict[str, Any]:
    """Query the question answering system."""
    question = query_params.get("question", "")

    # fomalize_question_chain = RunnableLambda(
    #     lambda x: fomalize_question(
    #         x, question_prompt_template=question_prompt_template, llm=llm
    #     )
    # )

    documents_retrieval_chain = RunnableParallel(
        {"docs_context": doc_retriever, "question": RunnablePassthrough()},
    )
    
    rerank_documents_chain = RunnableLambda(
        lambda x: rerank_documents(
            x, k=QASystemConfig.RERANKER_CANDIDATES_COUNT, cross_encoder=cross_encoder
        )
    )

    prepere_candidates_chain = (
        # fomalize_question_chain
        documents_retrieval_chain
        | rerank_documents_chain
    )
    model_chain = answer_prompt_template | llm | output_parser

    complete_chain = prepere_candidates_chain | RunnablePassthrough.assign(
        result=model_chain
    )

    result = complete_chain.invoke(question)
    return result


# def fomalize_question(
#     question,
#     question_prompt_template: ChatPromptTemplate,
#     llm: ChatOpenAI,
# ) -> str:
#     """Formalize the question."""
#     formalized_question_prompt = question_prompt_template.format(question=question)
#     response = llm(formalized_question_prompt)
    
#     print(f"Исходный вопрос: {question}")
#     print(f"Формализованный вопрос: {response.content}")
    
#     return response.content
