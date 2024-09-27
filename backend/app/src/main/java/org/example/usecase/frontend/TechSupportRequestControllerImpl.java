package org.example.usecase.frontend;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.entity.Employee;
import org.example.service.MessageService;
import org.example.service.TechSupportRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/requestssss")
@RequiredArgsConstructor
public class TechSupportRequestControllerImpl implements TechSupportRequestController {

    private static final Logger log = LoggerFactory.getLogger(TechSupportRequestControllerImpl.class);
    private final MessageService messageService;

    private final TechSupportRequestService service;

    @Override
    @GetMapping("/{requestId}/dialogue")
    public List<MessageDto> getDialogueByRequestId(@PathVariable Long requestId) {
        log.info("getDialogueByRequestId: {}", requestId);
        return messageService.getMessagesByRequestId(requestId);
    }

    @Override
    @PostMapping("/{requestId}/close")
    public void closeRequestById(@PathVariable Long requestId) {
        service.closeRequestById(requestId);
    }

    @Override
    @PostMapping("/{requestId}/send")
    public MessageDto sendMessageToDialogue(@PathVariable Long requestId, String text, @AuthenticationPrincipal Employee employee) {
        return service.sendMessageToDialogue(requestId, text, employee.getFullName());
    }

    @Override
    @PostMapping("/{requestId}/assign")
    public void assignEmployeeToRequest(@PathVariable Long requestId, @AuthenticationPrincipal Employee employee) {
        service.assignEmployeeToRequest(requestId, employee);
    }
}
