package org.example.usecase.ml;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.AiResponse;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AIClient aiClient;
    private final MessageRepository messageRepository;

    public AiResponse getAnswerFromAi(Session currentSession, Message userMessageToAnswer) {
        ArrayList<Message> aiContext = new ArrayList<>(messageRepository.findBySession(currentSession));
        aiContext.add(userMessageToAnswer);
        return new AiResponse("Заглушка ответа", true);
//        return aiClient.getAiResponse(
//                aiContext.stream().map(message -> new MessageDto(
//                        message.getMessageText(),
//                        message.getCreatedAt(),
//                        message.getSide(),
//                        message.getIsHelpful()
//                )).toList()
//        );
    }
}
