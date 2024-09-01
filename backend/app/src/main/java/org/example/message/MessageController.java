package org.example.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public interface MessageController {

    /**
     * Добавляет сообщение к текущей сессии, используя уникальный идентификатор chatId
     *
     * @param dto данные из сообщения
     */
    @Operation(summary = "Adds a message to the current session",
            description = "Adds a message to the current session using the unique chatId identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    void createNewMessage(
            @RequestBody(description = "Data from the message", required = true,
                    content = @Content(schema = @Schema(implementation = CreateNewMessageDto.class)))
            CreateNewMessageDto dto
    );
}
