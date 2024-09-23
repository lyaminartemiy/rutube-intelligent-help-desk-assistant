import os

from langchain.text_splitter import CharacterTextSplitter
from langchain_openai import OpenAIEmbeddings
from langchain_chroma import Chroma


def generate_embeddings(embeddings_model: OpenAIEmbeddings):
    with open(os.getenv("RUTUBE_DOCUMENTS_PATH")) as f:
        state_of_the_union = f.read()

    text_splitter = CharacterTextSplitter(chunk_size=1000, chunk_overlap=0)
    pages = text_splitter.split_text(state_of_the_union)

    texts = text_splitter.create_documents(pages)
    db = Chroma.from_documents(texts, embeddings_model, persist_directory="./resources/chroma_db")
    print("persist_directory:", db._persist_directory)
