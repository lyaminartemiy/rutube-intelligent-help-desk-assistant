package org.example.usecase.frontend;

import org.example.model.dto.MessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class TechSupportRequestControllerImpl implements TechSupportRequestController {
    @Override
    @GetMapping("/{requestId}/dialogue")
    public List<MessageDto> getDialogueByRequestId(@PathVariable Long requestId) {
        return List.of();
    }

    @Override
    @PostMapping("/{requestId}/close")
    public void closeRequestById(@PathVariable Long requestId) {

    }

    @Override
    @PostMapping("/{requestId}/send")
    public void sendMessageToDialogue(@PathVariable Long requestId, String text) {

    }
}
