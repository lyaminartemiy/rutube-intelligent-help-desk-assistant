import os

from langchain.text_splitter import CharacterTextSplitter
from langchain_openai import OpenAIEmbeddings
from langchain_chroma import Chroma
from langchain.schema import Document
# from resources.messages import data


def generate_embeddings(embeddings_model: OpenAIEmbeddings):
    with open(os.getenv("RUTUBE_DOCUMENTS_PATH")) as f:
        state_of_the_union = f.read()

    text_splitter = CharacterTextSplitter(chunk_size=1000, chunk_overlap=0)
    pages = text_splitter.split_text(state_of_the_union)

    texts = text_splitter.create_documents(pages)
    db = Chroma.from_documents(texts, embeddings_model, persist_directory="./resources/chroma_db")
    print("persist_directory:", db._persist_directory)


# def generate_embeddings(embeddings_model: OpenAIEmbeddings):
#     # Создание списка документов из данных
#     texts = []
#     for section, qa_pairs in data.items():
#         for question, answer in qa_pairs.items():
#             # Создаем документ для каждого вопроса и ответа
#             text_content = f"Вопрос: {question}\nОтвет: {answer}"
#             texts.append(Document(page_content=text_content))
    
#     for i,text in enumerate(texts):
#         print("i:", i, "page_content:", text.page_content)

#     # Создание эмбеддингов и добавление в ChromaDB
#     db = Chroma.from_documents(texts, embeddings_model, persist_directory="./resources/chroma_db")
