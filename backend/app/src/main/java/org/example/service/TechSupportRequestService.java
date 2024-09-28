package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.Employee;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.example.usecase.telegram.impl.SendMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechSupportRequestService {

    private final TechSupportRequestRepository repository;
    private final SessionRepository sessionRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;
    private final SendMessageService sendMessageService;


    public void closeRequestById(Long requestId) {
        repository.findById(requestId).get().setStatus(TechSupportRequest.Status.CLOSED);
    }

    public MessageDto sendMessageToDialogue(Long requestId, String text, String authorName, Boolean isEditedByTechSupport) {
        Session session = techSupportRequestRepository.findById(requestId).get().getSession();
        Message lastMessageFromDialogue = session.getMessages().getLast();
        assert lastMessageFromDialogue.getSide().equals(Message.Side.BOT);
        lastMessageFromDialogue.setMessageText(text);
        if (isEditedByTechSupport) {
            lastMessageFromDialogue.setSide(Message.Side.TECH_SUPPORT_EMPLOYEE);
        }
        Message message = sendMessageService.sendMessageFromTechSupport(session, text, authorName);
        return new MessageDto(
                message.getMessageText(),
                message.getCreatedAt(),
                message.getSide(),
                message.getIsHelpful()
        );
    }

    public void assignEmployeeToRequest(Long requestId, Employee employee) {
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).get();
        request.getAssignedEmployees().add(employee);
        techSupportRequestRepository.save(request);
    }
}
