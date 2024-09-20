package org.example.usecase.telegram;

import org.example.model.dto.SaveMessageDto;

public interface SaveMessageController {

    void saveMessageToDatabase(SaveMessageDto requestBody);
}
