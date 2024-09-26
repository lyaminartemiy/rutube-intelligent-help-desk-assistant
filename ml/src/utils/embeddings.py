import os
import shutil

from langchain_openai import OpenAIEmbeddings
from langchain_chroma import Chroma
from langchain.schema import Document
from resources.faq import data


def generate_embeddings(embeddings_model: OpenAIEmbeddings) -> Chroma:
    db = Chroma(persist_directory=os.getenv("CHROMA_DB"))
    db.delete(ids=[str(i) for i in range(1, len(data) + 1)])

    faq = []
    for question, answer in data.items():
        faq.append(
            Document(
                page_content=question,
                metadata={"answer": answer}
            )
        )

    db = Chroma.from_documents(
        faq, embeddings_model, persist_directory=os.getenv("CHROMA_DB")
    )


# from langchain.text_splitter import CharacterTextSplitter
# def generate_embeddings(embeddings_model: OpenAIEmbeddings):
#     with open(os.getenv("RUTUBE_DOCUMENTS_PATH")) as f:
#         state_of_the_union = f.read()

#     text_splitter = CharacterTextSplitter(chunk_size=1000, chunk_overlap=0)
#     pages = text_splitter.split_text(state_of_the_union)

#     texts = text_splitter.create_documents(pages)
#     db = Chroma.from_documents(texts, embeddings_model, persist_directory="./resources/chroma_db")
#     print("persist_directory:", db._persist_directory)
