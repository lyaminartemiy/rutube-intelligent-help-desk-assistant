package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.MessageRepository;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.example.usecase.ml.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UpdateMessageService {

    private static final Logger log = LoggerFactory.getLogger(UpdateMessageService.class);
    private final MessageRepository messageRepository;
    private final SessionRepository sessionRepository;


    private final SendMessageService sendMessageService;
    private final AiService aiService;
    private final TechSupportRequestRepository techSupportRequestRepository;

    public void createOrUpdateMessageInDatabase(
            String chatId,
            String messageId,
            String text,
            ZonedDateTime createdAt,
            Boolean isHelpful,
            String aiText
    ) {
        System.out.println("chatId: " + chatId);
        System.out.println("messageId: " + messageId);
        System.out.println("text: " + text);
        System.out.println("createdAt: " + createdAt);
        System.out.println("isHelpful: " + isHelpful);
        messageRepository.findOneByMessageId(messageId).ifPresentOrElse(
                message -> {
                    log.info("Мы попали в постановку оценки");
                    if (isHelpful != null) {
                        message.setIsHelpful(isHelpful);
                    }
                },
                () -> {
                    Session currentSession = sessionRepository.findByChatIdAndStatus(chatId, Session.Status.OPEN).getFirst();
                    if (text != null && !text.isEmpty()) {
                        log.info("Мы попали в момент, когда питон присылает сообщение пользователя");
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
                        messageRepository.save(userMessageToAnswer);
//                        if (currentSession.getRequest() == null) {
//                            AiResponse aiResponse = aiService.getAnswerFromAi(currentSession, userMessageToAnswer);
//                            messageRepository.save(sendMessageService.sendMessageFromBot(currentSession, aiResponse));
////                            messageRepository.save(sendMessageService.sendMessageFromBot(currentSession, aiResponse));
//                        }
                        Message botMessage = messageRepository.save(
                                Message.builder()
                                        .messageText(aiText)
                                        .createdAt(createdAt)
                                        .side(Message.Side.BOT)
                                        .session(currentSession)
                                        .author("Бот")
                                        .build()
                        );
                        messageRepository.save(botMessage);
                        TechSupportRequest newRequest = TechSupportRequest.builder()
                                .title(text)
                                .status(TechSupportRequest.Status.OPEN)
                                .session(currentSession)
                                .assignedEmployees(new ArrayList<>())
                                .build();
                        techSupportRequestRepository.save(newRequest);
                    } else {
                        log.info("Мы попали в момент, когда питон присылает messageId для его установки в ботовское сообщение");
                        Message lastMessageInSession = messageRepository.findAllBySession_Id(currentSession.getId()).getLast();
                        log.info("prev messageId: {}", lastMessageInSession.getMessageId());
                        lastMessageInSession.setMessageId(messageId);
                        log.info("last messageId: {}", lastMessageInSession.getMessageId());
                        log.info("messageText: {}", lastMessageInSession.getMessageText());
                    }
                }
        );
    }
}
