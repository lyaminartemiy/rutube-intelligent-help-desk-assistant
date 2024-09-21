package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.EmployeeDto;

import java.util.List;

@Tag(name = "Employee Management", description = "Operations related to employee management")
public interface EmployeeController {

    @Operation(summary = "Получить всех сотрудников с ролью ТП",
            description = "Возвращает список всех сотрудников с ролью техподдержки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список сотрудников успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<EmployeeDto> getAllEmployees();

    @Operation(summary = "Полнотекстовый поиск по всем сотрудникам",
            description = "Возвращает список сотрудников, соответствующих критериям поиска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список сотрудников успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<EmployeeDto> getEmployeeBySearch(
            @Parameter(description = "Критерий поиска", required = true) String search
    );
}
