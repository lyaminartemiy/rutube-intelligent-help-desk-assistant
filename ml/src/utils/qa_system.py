from sentence_transformers import CrossEncoder


def get_top_k(k, rank_result, docs):
    """
    Get the top k scored documents from the ranking result.

    Args:
        k (int): The number of top documents to retrieve.
        rank_result (list): The ranking result, which is a list of dictionaries
            containing the corpus_id and the score of each document.
        docs (dict): The dictionary containing the documents. The key is the corpus_id
            and the value is the document content.

    Returns:
        A tuple of two lists. The first list contains the top k scored documents
        and the second list contains the corresponding ids.
    """
    # Initialize the lists to store the top k scored documents and their ids
    docs_top_k, ids = [], []

    # Iterate over the ranking result and retrieve the top k scored documents
    for i in range(k):
        # Get the document and its score from the ranking result
        doc = rank_result[i]
        doc_id = doc["corpus_id"]
        doc_score = doc["score"]

        # Append the document and its id to the lists
        docs_top_k.append((docs[doc_id], doc_score))
        ids.append(doc_id)

    # Return the lists of top k scored documents and their ids
    return docs_top_k, ids


def rerank_documents(info, k: int, cross_encoder: CrossEncoder):
    """
    Rerank the documents in the context based on the given question and the given cross encoder model.

    Args:
        info (dict): A dictionary containing the question and the documents context.
        k (int): The number of documents to select from the reranking result.
        cross_encoder (CrossEncoder): The cross encoder model used for reranking.

    Returns:
        dict: The updated dictionary with the reranking result and the updated documents context.
    """
    # Get the question and the documents context
    question = info["question"]

    # Get the text of each document in the context
    docs_context = [doc.page_content for doc in info["docs_context"]]

    # Get the metadata of each document in the context
    docs_metadata = [
        f"{doc.metadata['question']}: {doc.metadata['answer']}"
        for doc in info["docs_context"]
    ]

    # Get the classes of each document in the context
    docs_classes = [doc.metadata for doc in info["docs_context"]]

    # Rank the documents based on the given question
    docs_rank_result = cross_encoder.rank(question, docs_context)

    # Select the top k documents from the reranking result
    docs_res, ids = get_top_k(k, docs_rank_result, docs_context)

    # Update the dictionary with the reranking result and the updated documents context
    info["vector_database_result"] = docs_context
    info["reranker_result"] = docs_res
    info["docs_context"] = [docs_metadata[id] for id in ids]
    info["docs_metadata"] = docs_classes[ids[0]]
    return info
