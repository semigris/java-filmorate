package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.interfaces.Update;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "name")
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

    public Film() {
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(String name, String description, LocalDate releaseDate, Long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}