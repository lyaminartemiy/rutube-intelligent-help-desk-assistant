package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.Employee;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
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


    public void closeRequestById(Long requestId) {
        repository.findById(requestId).get().setStatus(TechSupportRequest.Status.CLOSED);
    }

    public MessageDto sendMessageToDialogue(Long requestId, String text, String authorName, Boolean isEditedByTechSupport) {
        TechSupportRequest request = techSupportRequestRepository.findById(requestId).get();
        Session session = request.getSession();
        Message lastMessageFromDialogue = session.getMessages().getLast();
        assert lastMessageFromDialogue.getSide().equals(Message.Side.BOT);
        lastMessageFromDialogue.setMessageText(text);
        if (isEditedByTechSupport) {
            lastMessageFromDialogue.setSide(Message.Side.TECH_SUPPORT_EMPLOYEE);
        }

        Message message = sendMessageService.sendMessageFromTechSupport(session, text, authorName);
        request.setStatus(TechSupportRequest.Status.CLOSED);
        return new MessageDto(
                message.getMessageText(),
                message.getCreatedAt(),
                message.getSide(),
                message.getIsHelpful()
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
