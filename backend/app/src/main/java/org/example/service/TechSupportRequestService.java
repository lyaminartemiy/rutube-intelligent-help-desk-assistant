package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.Employee;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.MessageRepository;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.example.usecase.telegram.impl.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechSupportRequestService {

    private static final Logger log = LoggerFactory.getLogger(TechSupportRequestService.class);
    private final TechSupportRequestRepository repository;
    private final SessionRepository sessionRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;
    private final SendMessageService sendMessageService;
    private final EmployeeRepository employeeRepository;
    private final MessageRepository messageRepository;


    public void closeRequestById(Long requestId) {
        TechSupportRequest request = repository.findById(requestId).get();
        request.setStatus(TechSupportRequest.Status.CLOSED);
        techSupportRequestRepository.save(request);
        sendMessageToDialogue(requestId, request.getSession().getMessages().getLast().getMessageText(), "Бот", false);
    }

    public MessageDto sendMessageToDialogue(Long requestId, String text, String authorName, Boolean isEditedByTechSupport) {
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).get();
        Session session = request.getSession();
        Message lastMessageFromDialogue = session.getMessages().getLast();
        assert lastMessageFromDialogue.getSide().equals(Message.Side.BOT);
        lastMessageFromDialogue.setMessageText(text);
        if (isEditedByTechSupport) {
            lastMessageFromDialogue.setSide(Message.Side.TECH_SUPPORT_EMPLOYEE);
            lastMessageFromDialogue.setAuthor(request.getAssignedEmployees().getFirst().getFullName());
        }
        Message userQuestion = session.getMessages().get(session.getMessages().size() - 2);
        var response = sendMessageService.sendMessageFromTechSupport(session, text, authorName, userQuestion.getMessageId());
        lastMessageFromDialogue.setMessageId(response.messageId());
        request.setStatus(TechSupportRequest.Status.CLOSED);
        messageRepository.save(lastMessageFromDialogue);
        techSupportRequestRepository.save(request);
        return new MessageDto(
                lastMessageFromDialogue.getMessageText(),
                lastMessageFromDialogue.getCreatedAt(),
                lastMessageFromDialogue.getSide(),
                lastMessageFromDialogue.getIsHelpful()
        );
    }

    public void assignEmployeeToRequest(Long requestId, Employee employee) {
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).get();
        if (!request.getAssignedEmployees().stream().map(Employee::getId).toList().contains(employee.getId())) {
            log.info("Assign employee with id {} to request id {}", employee.getId(), requestId);
            request.getAssignedEmployees().add(employee);
            request.setStatus(TechSupportRequest.Status.IN_PROGRESS);
            List<TechSupportRequest> requestsInProgress = employee.getRequestsInProgress();
            requestsInProgress.add(request);
            employeeRepository.save(employee);
            techSupportRequestRepository.save(request);
        }
    }
}
