package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.MessageRepository;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final MessageRepository messageRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;
    private final SessionRepository sessionRepository;

    public List<MessageDto> getMessagesByRequestId(Long requestId) {
        log.info("getMessagesByRequestId: {}", requestId);
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).get();
        log.info(request.toString());
        return messageRepository.findBySession(request.getSession()).stream()
                .map(m -> new MessageDto(
                        m.getMessageText(),
                        m.getCreatedAt(),
                        m.getSide(),
                        m.getIsHelpful())
                )
                .toList();
    }
}