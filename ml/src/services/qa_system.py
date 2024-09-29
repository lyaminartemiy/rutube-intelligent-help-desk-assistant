from typing import Any, Dict

from config.config import PromptConfig, QASystemConfig
from langchain_chroma import Chroma
from langchain_core.runnables import (RunnableLambda, RunnableParallel,
                                      RunnablePassthrough)
from sentence_transformers import CrossEncoder
from transformers import AutoModelForCausalLM, AutoTokenizer
from utils.qa_system import rerank_documents


def query_system(
    query_params: Dict[str, Any],
    doc_retriever: Chroma,
    tokenizer: AutoTokenizer,
    model: AutoModelForCausalLM,
    cross_encoder: CrossEncoder,
) -> Dict[str, Any]:
    """
    Query the question answering system.

    The pipeline consists of the following steps:
    1. Retrieve relevant documents from the database using Chroma.
    2. Rerank the documents using the cross-encoder.
    3. Generate the final answer using the GEMMA model.

    Parameters
    ----------
    query_params : Dict[str, Any]
        A dictionary containing the question to be answered.
    doc_retriever : Chroma
        The Chroma document retriever.
    tokenizer : AutoTokenizer
        The tokenizer to use to tokenize the question and the retrieved documents.
    model : AutoModelForCausalLM
        The GEMMA model to use to generate the final answer.
    cross_encoder : CrossEncoder
        The cross-encoder to use to rerank the documents.

    Returns
    -------
    Dict[str, Any]
        A dictionary containing the answer to the question and the relevant documents.
    """
    question = query_params.get("question", "")

    # Retrieve relevant documents from the database
    documents_retrieval_chain = RunnableParallel(
        {"docs_context": doc_retriever, "question": RunnablePassthrough()},
    )

    # Rerank the documents using the cross-encoder
    rerank_documents_chain = RunnableLambda(
        lambda x: rerank_documents(
            x, k=QASystemConfig.RERANKER_CANDIDATES_COUNT, cross_encoder=cross_encoder
        )
    )

    # Generate the final answer using the GEMMA model
    gemma_generate_chain = RunnableLambda(
        lambda x: llm_inference(x, tokenizer=tokenizer, model=model)
    )

    # Prepare the candidates for reranking
    prepere_candidates_chain = (
        # fomalize_question_chain
        documents_retrieval_chain
        | rerank_documents_chain
    )

    # The complete pipeline
    complete_chain = prepere_candidates_chain | RunnablePassthrough.assign(
        result=gemma_generate_chain
    )

    # Execute the pipeline
    result = complete_chain.invoke(question)
    return result


def llm_inference(info, tokenizer: AutoTokenizer, model: AutoModelForCausalLM):
    """
    Perform LLM inference.

    Parameters
    ----------
    info : Dict[str, Any]
        A dictionary containing the question and the context of the relevant documents.
    tokenizer : AutoTokenizer
        A tokenizer for encoding the input prompt.
    model : AutoModelForCausalLM
        A LLM model for generating the answer.

    Returns
    -------
    str
        The generated answer.
    """
    # Get the question and the context of the relevant documents
    question = info["question"]
    docs_context = info["docs_context"]

    # Construct the prompt for the llm model
    # The prompt is a combination of the question and the context of the relevant documents
    # The context is included to provide additional information for the model to generate the answer
    prompt = PromptConfig.ANSWER_PROMPT_TEMPLATE.format(
        question=question, docs_context=docs_context
    )

    # Encode the prompt using the tokenizer
    # The encoding is done to convert the prompt into a format that can be used by the GEMMA model
    # The encoding includes the input IDs, attention masks, and token type IDs
    encoded_input = tokenizer(prompt, return_tensors="pt", add_special_tokens=False).to(
        "cuda"
    )

    # Generate the answer using the llm model
    # The model is used to generate a sequence of tokens based on the input prompt
    # The generation is done using a greedy algorithm, meaning that the model will generate the most likely token at each step
    # The generation is also done with a maximum length of 512 tokens, which is the maximum length that the GEMMA model can generate
    output = model.generate(
        input_ids=encoded_input["input_ids"],
        do_sample=False,
        max_new_tokens=512,
        temperature=0,
        top_k=30,
    )

    # Decode the generated output
    # The decoding is done to convert the generated tokens back into a string
    decoded_output = tokenizer.decode(output[0], skip_special_tokens=True)

    # Extract the answer from the decoded output
    # The answer is extracted by splitting the decoded output on the "Answer:" string
    # The answer is then returned as the result
    info["result"] = decoded_output

    # Split the decoded output to get the answer
    decoded_output = decoded_output.split("Ответ:")
    if len(decoded_output) < 2:
        return "Я не знаю."
    else:
        return decoded_output[1].strip()
