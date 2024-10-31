package ru.yandex.practicum.filmorate.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateUserRequest {
    @Email(message = "Электронная почта некорректна")
    @NotEmpty(message = "Электронная почта не может быть пустой")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
    private String login;
    private String name;
    @NotNull(message = "Дата рождения долджна быть указана")
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
