from typing import Any, Dict, List, Tuple

from sentence_transformers import CrossEncoder


# def extract_documents(info):
#     for k, v in info.items():
#         if type(v) is not str:
#             info[k] = v[0][0]
#     return info


def get_top_k(k, rank_result, docs, metadata):
    docs_top_k, metadata_top_k = [], []
    for i in range(k):
        doc = rank_result[i]
        doc_id = doc["corpus_id"]
        doc_score = doc["score"]
        docs_top_k.append((docs[doc_id], doc_score))
        metadata_top_k.append(metadata[doc_id])
    return docs_top_k, metadata_top_k


def rerank_documents(
    info, k: int, cross_encoder: CrossEncoder
) -> List[Tuple[Any, float]]:
    question = info["question"]

    docs_context = [doc.page_content for doc in info["docs_context"]]
    docs_answers = [doc.metadata["answer"] for doc in info["docs_context"]]
    docs_metadata = [doc.metadata for doc in info["docs_context"]]

    # print("\nВходной вопрос:", question)

    docs_rank_result = cross_encoder.rank(question, docs_context)
    # print("Результаты ранжирования:", docs_rank_result)

    docs_res, meta_res = get_top_k(k, docs_rank_result, docs_context, docs_metadata)

    info["vector_database_result"] = docs_context
    info["reranker_result"] = docs_res

    # print("Результаты получения топ-К:")
    # for i, doc in enumerate(docs_res):
    #     print(f"Вопрос топ-{i}", doc, "ответ на вопрос: ", docs_metadata[i])

    info["docs_context"] = docs_res
    info["docs_metadata"] = meta_res
    return info
