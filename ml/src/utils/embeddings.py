import os
from typing import List

import pandas as pd

from langchain.schema import Document
from langchain_chroma import Chroma
from langchain_core.embeddings import Embeddings
from sentence_transformers import SentenceTransformer


class CustomEmbeddings(Embeddings):
    """
    A custom embeddings model using the SentenceTransformer model.

    Attributes:
        model (SentenceTransformer): The SentenceTransformer model to use for the embeddings.
    """

    def __init__(self, model_name: str):
        """
        Initialize the embeddings model.

        Args:
            model_name (str): The name of the Hugging Face model to use for the embeddings.
        """
        super().__init__()
        self.model = SentenceTransformer(model_name)

    def embed_documents(self, texts: List[str]) -> List[List[float]]:
        """
        Encode a list of texts into embeddings.

        Args:
            texts (List[str]): The list of texts to encode.

        Returns:
            List[List[float]]: The embeddings for the given texts.
        """
        return self.model.encode(texts, convert_to_tensor=False).tolist()

    def embed_query(self, query: str) -> List[float]:
        """
        Encode a query into an embedding.

        Args:
            query (str): The query to encode.

        Returns:
            List[float]: The embedding for the given query.
        """
        return self.model.encode(query, convert_to_tensor=False).tolist()


def generate_embeddings(
    embeddings_model: CustomEmbeddings, source_faq: pd.DataFrame
) -> None:
    """
    Generate embeddings for the given FAQ and save them to Chroma database.

    Args:
        embeddings_model (CustomEmbeddings): The model to use for generating embeddings.
        source_faq (pd.DataFrame): The FAQ data to generate embeddings for.

    Returns:
        None

    The function first deletes all existing embeddings from the Chroma database, then
    creates new embeddings using the provided embeddings model and saves them to the
    Chroma database.
    """
    # Delete existing embeddings from the Chroma database
    db = Chroma(persist_directory=os.getenv("CHROMA_DB"))
    db.delete(ids=[str(i) for i in range(1, len(source_faq) + 1)])

    # Create new embeddings using the provided embeddings model
    faq = source_faq.apply(
        lambda x: Document(
            page_content=f"{x['Вопрос из БЗ']}",
            metadata={
                "question": x["Вопрос из БЗ"],
                "answer": x["Ответ из БЗ"],
                "class_1": x["Классификатор 1 уровня"],
                "class_2": x["Классификатор 2 уровня"],
            },
        ),
        axis=1,
    ).to_list()

    # Save the new embeddings to the Chroma database
    db = Chroma.from_documents(
        faq, embeddings_model, persist_directory=os.getenv("CHROMA_DB")
    )
