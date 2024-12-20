package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.yandex.practicum.filmorate.interfaces.Update;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Getter
@EqualsAndHashCode(of = "email")
public class User {
    @NotNull(groups = {Update.class}, message = "id должен быть указан")
    private Long id;
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
    private final Set<Long> friends = new HashSet<>();
}
