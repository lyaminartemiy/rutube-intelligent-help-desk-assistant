package org.example.usecase.telegram;

import org.example.model.dto.SendBotMessageDto;
import org.example.model.dto.SendMessageResponse;
import org.example.model.dto.SendTechSupportMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sendMessageClient", url = "http://hackaton-tgbot:8002")
public interface SendMessageClient {

    @PostMapping("/send-message/bot")
    ResponseEntity<SendMessageResponse> sendMessageFromBot(@RequestBody SendBotMessageDto requestBody);

    @PostMapping("/send-message/dp")
    ResponseEntity<SendMessageResponse> sendMessageFromTechSupport(@RequestBody SendTechSupportMessageDto requestBody);
}
