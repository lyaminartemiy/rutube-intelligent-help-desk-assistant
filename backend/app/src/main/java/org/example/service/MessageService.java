package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.MessageRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;

    public List<MessageDto> getMessagesByRequestId(Long requestId) {
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).get();
        return messageRepository.findBySession(request.getSession()).stream()
                .map(m -> new MessageDto(
                        m.getMessageText(),
                        m.getCreatedAt(),
                        m.getSide(),
                        m.getIsHelpful())
                )
                .sorted((o1, o2) -> o2.createdAt().compareTo(o1.createdAt()))
                .toList();
    }
}