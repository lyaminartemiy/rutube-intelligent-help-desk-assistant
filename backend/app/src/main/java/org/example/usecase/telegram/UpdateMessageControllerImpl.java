package org.example.usecase.telegram;

import org.example.model.dto.UpdateMessageDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class UpdateMessageControllerImpl implements UpdateMessageController {
    @Override
    @PutMapping
    public void updateMessageInDatabase(@RequestBody  UpdateMessageDto requestBody) {

    }
}
