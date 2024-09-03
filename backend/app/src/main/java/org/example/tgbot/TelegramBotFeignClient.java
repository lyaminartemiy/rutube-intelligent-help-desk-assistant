package org.example.tgbot;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "telegramBotFeignClient", url = "http://localhost:8080")
public interface TelegramBotFeignClient {

    @PostMapping("/api/messages")
    void sendMessageToBot(@RequestBody SendMessageDto dto);
}
