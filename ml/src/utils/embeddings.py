import os

import pandas as pd

from langchain.schema import Document
from langchain_chroma import Chroma
from langchain_openai import OpenAIEmbeddings
from resources.faq import data


def generate_embeddings(
    embeddings_model: OpenAIEmbeddings, source_faq: pd.DataFrame
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
