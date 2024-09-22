package org.example.usecase.ml;

import org.example.model.dto.AiResponse;
import org.example.model.dto.MessageDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AIClient {

    AiResponse getAiResponse(@RequestBody List<MessageDto> requestBody);
}
