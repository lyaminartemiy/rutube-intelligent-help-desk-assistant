package org.example.tgbot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramBotService {

    private final TelegramBotFeignClient tgBotClient;

    public void sendMessage(String chatId, String text, String author) {
        tgBotClient.sendMessageToBot(new SendMessageDto(
                chatId,
                text,
                author
        ));
    }
}