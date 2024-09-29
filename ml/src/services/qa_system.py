from typing import Any, Dict

import torch
from config.config import QASystemConfig, PromptConfig
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from langchain.prompts import ChatPromptTemplate
from langchain_chroma import Chroma
from langchain_core.runnables import (
    RunnableLambda,
    RunnableParallel,
    RunnablePassthrough,
)
from sentence_transformers import CrossEncoder
from utils.qa_system import rerank_documents

from transformers import AutoModelForCausalLM, AutoTokenizer
from config.config import ModelsConfig, PromptConfig


def query_system(
    query_params: Dict[str, Any],
    question_prompt_template: ChatPromptTemplate,
    answer_prompt_template: ChatPromptTemplate,
    doc_retriever: Chroma,
    tokenizer: AutoTokenizer,
    model: AutoModelForCausalLM,
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

    gemma_generate_chain = RunnableLambda(
        lambda x: gemma_inference(
            x, tokenizer=tokenizer, model=model
        )
    )

    prepere_candidates_chain = (
        # fomalize_question_chain
        documents_retrieval_chain
        | rerank_documents_chain
    )

    complete_chain = prepere_candidates_chain | RunnablePassthrough.assign(
        result=gemma_generate_chain
    )

    result = complete_chain.invoke(question)
    return result


# def gemma_inference(info, tokenizer, model, prompt_template: ChatPromptTemplate):
#     question = info["question"]
#     docs_context = info["docs_context"]

#     prompt = PromptConfig.ANSWER_PROMPT_TEMPLATE.format(question=question, docs_context=docs_context)

#     input_ids = tokenizer.apply_chat_template(
#         [{"role": "user", "content": prompt}],
#         truncation=True,
#         add_generation_prompt=True,
#         return_tensors="pt"
#     ).to("cuda" if torch.cuda.is_available() else "cpu")
#     outputs = model.generate(
#         input_ids=input_ids,
#         max_new_tokens=512,
#         do_sample=True,
#         temperature=0.5,
#         top_k=50,
#         top_p=0.95
#     )
#     decoded_output = tokenizer.batch_decode(outputs, skip_special_tokens=False)[0]
#     cleaned_output = decoded_output.replace('<bos>', '').replace('<start_of_turn>', '').replace('<end_of_turn>', '').strip()
#     answer = cleaned_output.split('model')[-1].strip()
#     print("ОТВЕТ МОДЕЛИ:", answer)
#     info["result"] = answer
#     return answer


def gemma_inference(info, tokenizer, model):
    question = info["question"]
    docs_context = info["docs_context"]

    # prompt = PromptConfig.ANSWER_PROMPT_TEMPLATE.format(question=question, docs_context=docs_context)
    prompt = f"Вопрос: {question}\nКонтекст: {docs_context}\nОтвет:"
    print("PROMPT:", prompt, end="\n\n")
    # message = {"role": "user", "content": prompt}
    encoded_input = tokenizer(prompt, return_tensors="pt", add_special_tokens=False).to("cuda")
    print("ENCODE_INPUT:", encoded_input, end="\n\n")

    output = model.generate(
        input_ids=encoded_input["input_ids"],
        do_sample=False,
        max_new_tokens=256,
        temperature=0.3,
        top_k=30,
        top_p=0.95,
    )
    print("OUTPUT:", output, end="\n\n")

    decoded_output = tokenizer.decode(output[0], skip_special_tokens=True)
    print("DECODE_OUTPUT:", decoded_output, end="\n\n")
    
    info["result"] = decoded_output
    return decoded_output


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