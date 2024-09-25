package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.FileDto;
import org.example.model.dto.ProfilePictureDto;
import org.example.model.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Tag(name = "Employee Profile Management", description = "Operations related to employee profile management")
public interface EmployeeProfileController {

    @Operation(summary = "Получить профиль сотрудника",
            description = "Возвращает профиль текущего сотрудника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль сотрудника успешно получен"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    UserProfileDto getEmployeeProfile(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Загрузить фото профиля",
            description = "Позволяет сотруднику загрузить фото профиля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фото профиля успешно загружено"),
            @ApiResponse(responseCode = "400", description = "Некорректный файл"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    FileDto uploadEmployeeProfilePicture(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal,
            @Parameter(description = "Файл изображения", required = true) MultipartFile file
    );

    @Operation(summary = "Скачать фото профиля",
            description = "Возвращает фото профиля для отображения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фото профиля успешно получено",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfilePictureDto.class))),
            @ApiResponse(responseCode = "404", description = "Фото профиля не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    ProfilePictureDto downloadEmployeeProfilePicture(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );
}
