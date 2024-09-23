from typing import Any, Dict, List, Tuple

from sentence_transformers import CrossEncoder


def extract_documents(info):
    for k, v in info.items():
        if type(v) is not str:
            info[k] = v[0][0]
    return info


def rerank_documents(info, k: int, cross_encoder: CrossEncoder) -> List[Tuple[Any, float]]:
    def get_top_k(rank_result, docs):
        top_k = []
        for i in range(k):
            doc = rank_result[i]
            doc_id = doc['corpus_id']
            doc_score = doc['score']
            top_k.append((docs[doc_id], doc_score))
        return top_k

    question = info['question']
    docs_context = [doc.page_content for doc in info['docs_context']]
    docs_rank_result = cross_encoder.rank(question, docs_context)
    docs_res = get_top_k(docs_rank_result, docs_context)
    info['docs_context'] = docs_res
    return info
