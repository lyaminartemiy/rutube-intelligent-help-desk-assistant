package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.AiResponse;
import org.example.model.dto.SendBotMessageDto;
import org.example.model.dto.SendMessageResponse;
import org.example.model.dto.SendTechSupportMessageDto;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.usecase.telegram.SendMessageClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class SendMessageService {

    private final SendMessageClient sendMessageClient;

    public Message sendMessageFromBot(
            Session session,
            AiResponse aiResponse
    ) {
            if (aiResponse.isQuestionAnswered()) {
                SendMessageResponse sendMessageResponse = sendMessageClient.sendMessageFromBot(
                        new SendBotMessageDto(
                                session.getChatId(),
                                aiResponse.text(),
                                true
                        )
                ).getBody();
                return Message.builder()
                        .messageId(sendMessageResponse.messageId())
                        .messageText(null)
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
                ).getBody();
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

    public ResponseEntity<SendMessageResponse> sendMessageFromTechSupport(SendTechSupportMessageDto requestBody) {
        return null;
    }
}
