package org.example.usecase.telegram;

import org.example.model.dto.SendMessageDto;
import org.springframework.http.ResponseEntity;

public interface SendMessageClient {

    ResponseEntity<Void> sendMessage(SendMessageDto requestBody);
}
