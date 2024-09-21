package org.example.usecase.telegram;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.UpdateMessageDto;

@Tag(name = "Message Management", description = "Operations related to updating messages")
public interface UpdateMessageController {

    @Operation(summary = "Обновить сообщение в базе данных",
            description = "Обновляет сообщение в базе данных на основе предоставленных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления сообщения"),
            @ApiResponse(responseCode = "404", description = "Сообщение не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    void updateMessageInDatabase(
            @Parameter(description = "Данные для обновления сообщения", required = true) UpdateMessageDto requestBody
    );
}
