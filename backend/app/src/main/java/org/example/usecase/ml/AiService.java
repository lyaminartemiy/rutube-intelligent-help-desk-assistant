package org.example.usecase.ml;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.AiResponse;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

    private final AIClient aiClient;
    private final MessageRepository messageRepository;

    public AiResponse getAnswerFromAi(Session currentSession, Message userMessageToAnswer) {
        log.info("Мы попали в момент, когда бэк идет в мл сервис");
        ArrayList<Message> aiContext = new ArrayList<>(messageRepository.findAllBySession_Id(currentSession.getId()));
        aiContext.add(userMessageToAnswer);
        return new AiResponse("Заглушка ответа", true);
//        return aiClient.getAiResponse(
//                aiContext.stream().map(message -> new AiMessageDto(
//                        message.getMessageText(),
//                        message.getCreatedAt(),
//                        message.getSide(),
//                        message.getIsHelpful()
//                )).toList()
//        );
    }
}
