package org.example;

import org.example.message.CreateNewMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "telegramBotFeignClient", url = "")
public interface TelegramBotFeignClient {

    @PostMapping("/api/messages")
    void sendMessageToBot(@RequestBody SendMessageDto dto);
}
