package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.TechSupportRequestDto;

import java.security.Principal;
import java.util.List;

@Tag(name = "Tech Support Request List", description = "Operations related to listing tech support requests")
public interface TechSupportRequestListController {

    @Operation(summary = "Получить все открытые обращения",
            description = "Возвращает список всех открытых обращений (включая новые через вебсокет)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список открытых обращений успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<TechSupportRequestDto> getAllOpenRequests();

    @Operation(summary = "Получить все открытые незанятые обращения",
            description = "Возвращает список всех открытых незанятых обращений (включая новые через вебсокет)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список открытых незанятых обращений успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<TechSupportRequestDto> getAllUnassignedRequests();

    @Operation(summary = "Полнотекстовый поиск по всем открытым обращениям",
            description = "Возвращает список открытых обращений, соответствующих критериям поиска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список открытых обращений по критериям поиска успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<TechSupportRequestDto> getOpenRequestsBySearch(
            @Parameter(description = "Критерий поиска", required = true) String search
    );

    @Operation(summary = "Получить все открытые обращения, закрепленные за сотрудником",
            description = "Возвращает список открытых обращений, закрепленных за текущим сотрудником")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список открытых обращений, закрепленных за сотрудником, успешно получен"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<TechSupportRequestDto> getAssignedRequestsByEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Полнотекстовый поиск по всем открытым обращениям по сотруднику",
            description = "Возвращает список открытых обращений, закрепленных за текущим сотрудником, соответствующих критериям поиска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список открытых обращений по критериям поиска успешно получен"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<TechSupportRequestDto> getAssignedRequestsByEmployeeAndSearch(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal,
            @Parameter(description = "Критерий поиска", required = true) String search
    );

    @Operation(summary = "Получить историю всех обращений, обработанных сотрудником",
            description = "Возвращает список всех обращений, обработанных текущим сотрудником")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История обращений успешно получена"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<TechSupportRequestDto> getAllRequestsAssignedToEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );
}
