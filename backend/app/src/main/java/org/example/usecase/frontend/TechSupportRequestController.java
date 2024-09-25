package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.MessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Tag(name = "Tech Support Requests", description = "Operations related to tech support requests")
public interface TechSupportRequestController {

    @Operation(summary = "Получить весь диалог по обращению",
            description = "Возвращает весь диалог по указанному обращению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Диалог успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", description = "Обращение не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<MessageDto> getDialogueByRequestId(
            @Parameter(description = "ID обращения", required = true) Long requestId
    );

    List<MessageDto> getDialogueByRequestIdWs(@PathVariable Long requestId);

    @Operation(summary = "Закрыть обращение по номеру",
            description = "Закрывает указанное обращение по его номеру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обращение успешно закрыто"),
            @ApiResponse(responseCode = "404", description = "Обращение не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    void closeRequestById(
            @Parameter(description = "ID обращения", required = true) Long requestId
    );

    @Operation(summary = "Отправить сообщение в бэкэнд",
            description = "Отправляет сообщение в диалог по указанному обращению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение успешно отправлено"),
            @ApiResponse(responseCode = "404", description = "Обращение не найдено"),
            @ApiResponse(responseCode = "400", description = "Некорректный текст сообщения"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    void sendMessageToDialogue(
            @Parameter(description = "ID обращения", required = true) Long requestId,
            @Parameter(description = "Текст сообщения", required = true) String text
    );

    @Operation(summary = "Назначить сотрудника на обращение",
            description = "Назначить сотрудника на обращение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сотрудник назначен")
    })
    void assignEmployeeToRequest(@Parameter(description = "ID обращения", required = true) Long requestId,
                                 Principal principal);
}
