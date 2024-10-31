package ru.yandex.practicum.filmorate.dto.film;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@Builder
public class UpdateFilmRequest {
    @NotNull(groups = {Update.class}, message = "id должен быть указан")
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;
    @NotNull(message = "Дата релиза долджна быть указана")
    private LocalDate releaseDate;
    private Mpa mpa;
    private LinkedHashSet<Genre> genres;

    @AssertTrue(message = "Дата релиза не должна быть раньше 28 декабря 1895 года")
    public boolean isValidReleaseDate() {
        return releaseDate.isAfter(LocalDate.of(1895, 12, 28));
    }

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    public boolean hasDuration() {
        return duration != null;
    }

    public boolean hasReleaseDate() {
        return releaseDate != null;
    }

    public boolean hasMpa() {
        return mpa != null;
    }

    public boolean hasGenres() {
        return genres != null;
    }
}
