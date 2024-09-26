#!/bin/bash

SERVER_USER="root"
SERVER_IP="192.168.1.14"
REMOTE_FOLDER="~/distr"
LOCAL_FOLDER="/home/lexa2hk/IdeaProjects/rutube-intelligent-help-desk-assistant"

ssh "${SERVER_USER}@${SERVER_IP}" "rm -rf ${REMOTE_FOLDER}"
scp -r "${LOCAL_FOLDER}" "${SERVER_USER}@${SERVER_IP}:${REMOTE_FOLDER}"
ssh "${SERVER_USER}@${SERVER_IP}" "cd ${REMOTE_FOLDER} && docker compose up -d"

echo "Deployment completed successfully."