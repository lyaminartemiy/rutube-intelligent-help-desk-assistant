package org.example.message;

import lombok.RequiredArgsConstructor;
import org.example.session.SessionService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final AdditionalContentRepository additionalContentRepository;
    private final SessionService sessionService;

    public void createNewMessage(
            String chatId,
            String text,
            List<CreateNewMessageDto.AdditionalContentDto> additionalContentDtos,
            ZonedDateTime createdAt
    ) {
        Message message = Message.builder()
                .text(text)
                .createdAt(createdAt)
                .session(sessionService.getOpenedSessionByChatId(chatId))
                .build();
        List<AdditionalContent> additionalContent = additionalContentDtos.stream()
                .map(dto -> AdditionalContent.builder()
                        .fileStorageLink(UUID.randomUUID()) // TODO реализовать MinIO
                        .fileExtension(dto.fileExtension())
                        .type(dto.contentType())
                        .message(message)
                        .build())
                .toList();
        messageRepository.save(message);
        additionalContentRepository.saveAll(additionalContent);
    }
}
