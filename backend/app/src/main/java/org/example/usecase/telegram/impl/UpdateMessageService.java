package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.AiResponse;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.repository.MessageRepository;
import org.example.repository.SessionRepository;
import org.example.usecase.ml.AiService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class UpdateMessageService {

    private final MessageRepository messageRepository;
    private final SessionRepository sessionRepository;


    private final SendMessageService sendMessageService;
    private final AiService aiService;

    public void createOrUpdateMessageInDatabase(
            String chatId,
            String messageId,
            String text,
            ZonedDateTime createdAt,
            Boolean isHelpful
    ) {
        messageRepository.findOneByMessageId(messageId).ifPresentOrElse(
                message -> {
                    if (isHelpful != null) {
                        message.setIsHelpful(isHelpful);
                    }
                },
                () -> {
                    Session currentSession = sessionRepository.findByChatIdAndStatus(chatId, Session.Status.OPEN).getFirst();
                    if (text != null) {
                        Message userMessageToAnswer = messageRepository.save(
                                Message.builder()
                                        .messageId(messageId)
                                        .messageText(text)
                                        .createdAt(createdAt)
                                        .side(Message.Side.USER)
                                        .session(currentSession)
                                        .author("Пользователь")
                                        .build()
                        );

                        AiResponse aiResponse = aiService.getAnswerFromAi(currentSession, userMessageToAnswer);
                        messageRepository.save(sendMessageService.sendMessageFromBot(currentSession, aiResponse));
                    } else {
                        Message lastMessageInSession = messageRepository.findBySession(currentSession).getLast();
                        lastMessageInSession.setMessageId(messageId);
                    }
                }
        );
    }
}
