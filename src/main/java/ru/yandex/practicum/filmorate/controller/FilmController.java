package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long filmId = 1;

    /**
     * Получение списка фильмов
     */
    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    /**
     * Добавление нового фильма
     */
    @PostMapping
    public Film create(@Valid @RequestBody Film newFilm) {
        newFilm.setId(filmId++);
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    /**
     * Обновление данных существующего фильма
     */
    @PutMapping
    @Validated({Update.class})
    public Film update(@RequestBody Film newFilm) throws NotFoundException {
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            return oldFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }
}