package org.example.usecase.ml;

import org.example.model.dto.AiMessageDto;
import org.example.model.dto.AiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "aiClient", url = "http://hackaton-ml:8001")
public interface AIClient {

    @GetMapping("/send-message/bot")
    AiResponse getAiResponse(@RequestBody List<AiMessageDto> requestBody);
}
