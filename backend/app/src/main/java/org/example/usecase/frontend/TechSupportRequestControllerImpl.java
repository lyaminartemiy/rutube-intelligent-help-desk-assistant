package org.example.usecase.frontend;

import org.example.model.dto.MessageDto;
import org.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/requests")
public class TechSupportRequestControllerImpl implements TechSupportRequestController {

    @Autowired
    private MessageService messageService;

    @Override
    @GetMapping("/{requestId}/dialogue")
    public List<MessageDto> getDialogueByRequestId(@PathVariable Long requestId) {
        return messageService.getMessagesByRequestId(requestId);
    }

    @MessageMapping("/getMessages")
    @SendTo("/topic/messages")
    @Override
    public List<MessageDto> getDialogueByRequestIdWs(@PathVariable Long requestId) {
        return messageService.getMessagesByRequestId(requestId);
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
