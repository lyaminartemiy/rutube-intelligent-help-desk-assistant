package org.example.service;


import org.example.model.dto.MessageDto;
import org.example.model.entity.Message;
import org.example.model.notDto.MessageSide;
import org.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getMessagesByRequestId(Long requestId) {
        return messageRepository.findBySession_Request_Id(requestId).stream().map(m -> new MessageDto(m.getMessageText(), m.getCreatedAt(), MessageSide.valueOf(m.getSide().toString()),m.getIsHelpful())).toList();
    }
}