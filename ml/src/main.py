# import uvicorn
# from fastapi import FastAPI
#
# from lifespan import lifespan
# from routers.qa_system import router as qa_system_router

import torch
#
# app = FastAPI(lifespan=lifespan)
# app.include_router(qa_system_router)

def check_gpu():
    # Check if CUDA is available (GPU support in PyTorch)
    if torch.cuda.is_available():
        print("CUDA is available! PyTorch can use the GPU.")
        print(f"Number of GPUs available: {torch.cuda.device_count()}")

        for i in range(torch.cuda.device_count()):
            print(f"\nGPU {i}: {torch.cuda.get_device_name(i)}")
            print(f"Memory Allocated: {torch.cuda.memory_allocated(i) / 1024**2:.2f} MB")
            print(f"Memory Cached: {torch.cuda.memory_reserved(i) / 1024**2:.2f} MB")
    else:
        print("CUDA is not available. PyTorch cannot use the GPU.")

if __name__ == "__main__":
#     uvicorn.run(app, host="0.0.0.0", port=8000)
    print("ML")
    print(check_gpu)
