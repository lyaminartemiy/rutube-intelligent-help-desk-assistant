package org.example.usecase.telegram;

import org.example.model.dto.SendMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface SendMessageClient {

    ResponseEntity<Void> sendMessage(@RequestBody SendMessageDto requestBody);
}
