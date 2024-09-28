# Use an Ubuntu-based image with CUDA
FROM nvidia/cuda:11.8.0-base-ubuntu20.04

# Set environment variables
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1
ENV DEBIAN_FRONTEND=noninteractive

# Install system dependencies
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        software-properties-common \
        build-essential \
        libffi-dev \
        libssl-dev \
        bash \
    && add-apt-repository ppa:deadsnakes/ppa \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
        python3.11 \
        python3.11-distutils \
        python3-pip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Create a volume for storing models
VOLUME ["/root/.cache/huggingface"]

# Copy the requirements file into the container
COPY requirements.txt .

# Install Python dependencies
RUN pip3 install --no-cache-dir -r requirements.txt

# Copy the rest of the application code into the container
COPY .env /app/.env
COPY src /app/src

# Set the working directory to src
WORKDIR /app/src

# Expose the port your app runs on
EXPOSE 8001

# Specify the command to run on container start
CMD [ "python3", "main.py" ]
