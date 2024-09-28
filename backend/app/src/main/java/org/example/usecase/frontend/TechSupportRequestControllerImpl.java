package org.example.usecase.frontend;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.model.dto.SendMessageToDialogueDto;
import org.example.model.entity.Employee;
import org.example.service.MessageService;
import org.example.service.TechSupportRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class TechSupportRequestControllerImpl implements TechSupportRequestController {

    private static final Logger log = LoggerFactory.getLogger(TechSupportRequestControllerImpl.class);
    private final MessageService messageService;

    private final TechSupportRequestService service;

    @Override
    @GetMapping("/{requestId}/dialogue")
    public ResponseEntity<List<MessageDto>> getDialogueByRequestId(@PathVariable Long requestId) {
        return ResponseEntity.ok(messageService.getMessagesByRequestId(requestId));
    }

    @Override
    @PostMapping("/{requestId}/close")
    public void closeRequestById(@PathVariable Long requestId) {
        service.closeRequestById(requestId);
    }

    @Override
    @PostMapping("/{requestId}/send")
    public MessageDto sendMessageToDialogue(@PathVariable Long requestId, @RequestBody SendMessageToDialogueDto smDto, @AuthenticationPrincipal Employee employee) {
        return service.sendMessageToDialogue(requestId, smDto.text(), employee.getFullName(), smDto.isEditedByTechSupport());
    }

    @Override
    @PostMapping("/{requestId}/assign")
    public void assignEmployeeToRequest(@PathVariable Long requestId, @AuthenticationPrincipal Employee employee) {
        service.assignEmployeeToRequest(requestId, employee);
    }
}
