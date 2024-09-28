from typing import List
import os

import pandas as pd

from langchain.schema import Document
from langchain_chroma import Chroma
from resources.faq import data
from langchain_core.embeddings import Embeddings
from sentence_transformers import SentenceTransformer


class CustomEmbeddings(Embeddings):
    def __init__(self, model_name: str):
        super().__init__()
        self.model = SentenceTransformer(model_name)

    def embed_documents(self, texts: List[str]) -> List[List[float]]:
        return self.model.encode(texts, convert_to_tensor=False).tolist()

    def embed_query(self, query: str) -> List[float]:
        return self.model.encode(query, convert_to_tensor=False).tolist()


def generate_embeddings(
    embeddings_model: CustomEmbeddings, source_faq: pd.DataFrame
) -> None:
    db = Chroma(persist_directory=os.getenv("CHROMA_DB"))
    db.delete(ids=[str(i) for i in range(1, len(data) + 1)])

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

    db = Chroma.from_documents(
        faq, embeddings_model, persist_directory=os.getenv("CHROMA_DB")
    )
