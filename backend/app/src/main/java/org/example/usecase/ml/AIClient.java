package org.example.usecase.ml;

import org.example.model.dto.AiResponse;
import org.example.model.dto.MessageDto;

import java.util.List;

public interface AIClient {

    AiResponse getAiResponse(List<MessageDto> requestBody);
}
