from langchain.embeddings import OpenAIEmbeddings
from langchain.vectorstores import Chroma


from dotenv import load_dotenv


load_dotenv()

from langchain.text_splitter import RecursiveCharacterTextSplitter, CharacterTextSplitter
from langchain.embeddings import OpenAIEmbeddings
from langchain.vectorstores import Chroma

# Define the long text

with open("messages.txt") as f:
    state_of_the_union = f.read()
text_splitter = CharacterTextSplitter(chunk_size=1000, chunk_overlap=0)
pages = text_splitter.split_text(state_of_the_union)
print(pages)

for p in pages:
    print("=" * 20)
    print(p)
# Initialize the embeddings model and Chroma DB
embeddings_model = OpenAIEmbeddings()
db = Chroma(embedding_function=embeddings_model)

# Embed and store the chunks in Chroma D
texts = text_splitter.create_documents(pages)
"""
for chunk in texts:
    print(chunk)
    db.add_documents([chunk])

print(db)
"""
db = Chroma.from_documents(texts, embeddings_model, persist_directory="./chroma_db")
db.persist()

"""
# Perform a similarity search
query = "Who is Virat Kohli?"
results = db.similarity_search(query)

# Display results
for result in results:
    print(f"Document: {result['original_text']}")
    print(f"Metadata: {result['other_metadata']}")

"""