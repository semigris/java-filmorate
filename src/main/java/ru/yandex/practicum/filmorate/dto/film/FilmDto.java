package ru.yandex.practicum.filmorate.dto.film;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@Builder
public class FilmDto {
    private Long id;
    private String name;
    private String description;
    private Long duration;
    private LocalDate releaseDate;
    private Mpa mpa;
    private LinkedHashSet<Genre> genres;
    private LinkedHashSet<Long> likes;
}
