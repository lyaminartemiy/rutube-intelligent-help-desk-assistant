package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.AiResponse;
import org.example.model.dto.SendBotMessageDto;
import org.example.model.dto.SendMessageResponse;
import org.example.model.dto.SendTechSupportMessageDto;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.usecase.telegram.SendMessageClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendMessageService {

    private final SendMessageClient sendMessageClient;

    public Message sendMessageFromBot(
            Session session,
            AiResponse aiResponse
    ) {
        log.info("Мы попали в момент, когда бэк отслыает соообщение бота в питон");
        if (aiResponse.isAnswer()) {
                SendMessageResponse sendMessageResponse = sendMessageClient.sendMessageFromBot(
                        new SendBotMessageDto(
                                session.getChatId(),
                                aiResponse.text(),
                                true
                        )
                );
            System.out.println(sendMessageResponse.toString());
//                SendMessageResponse sendMessageResponse = new SendMessageResponse(
//                        "12345",
//                        "7891"
//                );
                return Message.builder()
                        .messageId(sendMessageResponse.messageId())
                        .messageText(aiResponse.text())
                        .createdAt(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                        .side(Message.Side.BOT)
                        .session(session)
                        .author("Бот")
                        .build();
            } else {
                SendMessageResponse sendMessageResponse = sendMessageClient.sendMessageFromBot(
                        new SendBotMessageDto(
                                session.getChatId(),
                                null,
                                false
                        )
                );
                return Message.builder()
                        .messageId(sendMessageResponse.messageId())
                        .messageText("Не знаю")
                        .createdAt(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                        .side(Message.Side.BOT)
                        .session(session)
                        .author("Бот")
                        .build();
            }

    }

    public SendMessageResponse sendMessageFromTechSupport(Session session, String text, String author, String userReplyMessageId) {
        return sendMessageClient.sendMessageFromTechSupport(new SendTechSupportMessageDto(
                session.getChatId(),
                text,
                userReplyMessageId
        ));
    }
}
