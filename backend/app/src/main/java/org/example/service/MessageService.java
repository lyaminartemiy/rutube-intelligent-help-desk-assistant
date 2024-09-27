package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.TechSupportRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final TechSupportRequestRepository techSupportRequestRepository;

    public List<MessageDto> getMessagesByRequestId(Long requestId) {
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).orElseThrow(() -> new IllegalStateException("ПИЗДЕЦ"));
        assert request != null;
        Session requestSession = request.getSession();
        List<Message> messages = requestSession.getMessages();
        log.info(String.valueOf(messages.size()));
        return messages.stream().map(
                m -> new MessageDto(
                        m.getMessageText(),
                        m.getCreatedAt(),
                        m.getSide(),
                        m.getIsHelpful()
                )
        ).toList();
    }
}