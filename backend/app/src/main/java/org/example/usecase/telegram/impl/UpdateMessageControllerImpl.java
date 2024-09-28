package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.UpdateMessageDto;
import org.example.usecase.telegram.UpdateMessageController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class UpdateMessageControllerImpl implements UpdateMessageController {

    private final UpdateMessageService service;

    @Override
    @PostMapping
    public void updateMessageInDatabase(@RequestBody UpdateMessageDto requestBody) {
        service.createOrUpdateMessageInDatabase(
                requestBody.chatId(),
                requestBody.messageId(),
                requestBody.text(),
                requestBody.createdAt(),
                requestBody.isHelpful(),
                requestBody.aiText()
        );
    }
}
