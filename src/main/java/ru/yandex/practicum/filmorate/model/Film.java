package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.interfaces.Update;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@Builder
@Getter
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
public class Film {
    @NotNull(groups = {Update.class}, message = "id должен быть указан")
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;

    @NotNull(message = "Дата релиза долджна быть указана")
    @AssertTrue(message = "Дата релиза не должна быть раньше 28 декабря 1895 года")
    public boolean isValidReleaseDate() {
        return releaseDate.isAfter(LocalDate.of(1895, 12, 28));
    }

    private LocalDate releaseDate;
    private Mpa mpa;
    LinkedHashSet<Genre> genres;
}