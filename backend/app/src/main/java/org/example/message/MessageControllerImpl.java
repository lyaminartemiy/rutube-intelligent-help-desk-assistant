package org.example.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final MessageService messageService;

    @Override
    @PostMapping
    public void createNewMessage(@RequestBody CreateNewMessageDto dto) {
        messageService.createNewMessage(
                dto.chatId(),
                dto.text(),
                dto.additionalContent(),
                dto.createdAt()
        );
    }
}
