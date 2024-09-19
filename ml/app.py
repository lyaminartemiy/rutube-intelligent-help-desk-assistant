import streamlit as st
from langchain_core.runnables import RunnableParallel, RunnablePassthrough, RunnableLambda
from langchain_openai import OpenAIEmbeddings
from langchain.chat_models import ChatOpenAI
from langchain.prompts import ChatPromptTemplate
from langchain.schema.output_parser import StrOutputParser
from sentence_transformers import CrossEncoder
import os
from dotenv import load_dotenv
from config import login, password
import sqlite3
__import__('sqlite3')

#proxy = f'{login}:{password}@194.33.32.10:8000'
#proxy = 'c8nof1:Ze2Go7@45.130.129.164:8000'
#proxy_url = f"http://{proxy}"

#os.environ['HTTP_PROXY'] = proxy_url
#os.environ['HTTPS_PROXY'] = proxy_url


import sys
sys.modules['sqlite3'] = sys.modules.pop('sqlite3')
from langchain_chroma import Chroma

load_dotenv()

embeddings = OpenAIEmbeddings(model='text-embedding-3-small')

docs_retriever = Chroma(
    #persist_directory=os.getenv('MONITORING_DOCS_DB'), embedding_function=embeddings
    persist_directory=os.getenv('CHROMA_DB'), embedding_function=embeddings
).as_retriever(search_kwargs={"k": 3})



prompt_str = """Ты — помощник по документам системы мониторинга. Твоя задача — отвечать на вопросы пользователей, используя предоставленные куски документации.

Вопрос пользователя:
{question}

Кусок документации:
{docs_context}


Пожалуйста, аггрегируй информацию из документации и используй её для ответа на вопрос. Если ты не знаешь ответа или не уверена, просто скажи: "Я не знаю".

Пример использования:

Вопрос пользователя: Как настроить мониторинг производительности?
Кусок документации: Для настройки мониторинга производительности необходимо выполнить следующие шаги...

Ответ: Чтобы настроить мониторинг производительности, необходимо выполнить следующие шаги..."""

prompt = ChatPromptTemplate.from_template(prompt_str)


def extract_doc(info):
    for k, v in info.items():
        if type(v) is not str:
            info[k] = v[0][0]
    return info


def reranker(info, k):
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
    #so_context = [doc.page_content for doc in info['so_context']]
    #print(question, docs_context, so_context)

    cross_encoder_model = CrossEncoder('DiTy/cross-encoder-russian-msmarco', max_length=512, device='cpu')

    docs_rank_result = cross_encoder_model.rank(question, docs_context)
    #so_rank_result = cross_encoder_model.rank(question)#, so_context)
    #print(docs_rank_result, so_rank_result)

    docs_res = get_top_k(docs_rank_result, docs_context)
    #so_res = get_top_k(so_rank_result)#, so_context)

    info['docs_context'] = docs_res
    #info['so_context'] = so_res

    return info


def qa(question):
    retrieval = RunnableParallel(
        {
            "docs_context": docs_retriever,# "so_context": so_retriever,
            "question": RunnablePassthrough()
        }
    )

    rerank = RunnableLambda(lambda x: reranker(info=x, k=3))

    get_doc = RunnableLambda(extract_doc)

    model = ChatOpenAI(model_name='gpt-4o-mini')
    
    output_parser = StrOutputParser()

    retrieval_chain = retrieval | rerank
    model_chain = get_doc | prompt | model | output_parser

    complete_chain = retrieval_chain | RunnablePassthrough.assign(result=model_chain)

    out = complete_chain.invoke(question)

    return out


def main():
    st.title("RutubeSupportBot")
    question = st.text_input("Ваш вопрос:")
    
    if question:
        out = qa(question)

        docs_context, docs_score = out['docs_context'][0]
        #so_context, so_score = out['so_context'][0]
        answer = out['result']

        #st.subheader('Chunk from docs:')
        #st.write(docs_context)
        st.subheader('Score for chunk from docs:')
        st.write(docs_score)

        #st.subheader('Chunk from SO:')
        #st.write(so_context)
        #st.subheader('Score for chunk from SO:')
        #st.write(so_score)
        
        st.subheader('Ответ:')
        st.write(answer)


if __name__ == '__main__':
    main()
