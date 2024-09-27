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
        log.info("1");
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).orElseThrow(() -> new IllegalStateException("ПИЗДЕЦ"));
        log.info("2");
        assert request != null;
        log.info("3");
        Session requestSession = request.getSession();
        log.info("4");
        List<Message> messages = requestSession.getMessages();
        log.info("5");
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