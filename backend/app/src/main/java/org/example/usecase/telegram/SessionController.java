package org.example.usecase.telegram;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Session Management", description = "Operations related to session management")
public interface SessionController {

    @Operation(summary = "Создать новую сессию",
            description = "Создает новую сессию для указанного чата")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сессия успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректный идентификатор чата"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    void createNewSession(
            @Parameter(description = "Идентификатор чата", required = true) String chatId
    );

    @Operation(summary = "Отправить сессию в техническую поддержку",
            description = "Отправляет сессию, связанную с указанным чатом, в техническую поддержку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сессия успешно отправлена в техническую поддержку"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    void sendSessionToTechSupport(
            @Parameter(description = "Идентификатор чата", required = true) String chatId
    );
}
