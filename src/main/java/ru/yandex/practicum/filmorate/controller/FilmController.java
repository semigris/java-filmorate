package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    /** Получение списка фильмов */
    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    /** Добавление нового фильма */
    @PostMapping
    public Film create(@RequestBody Film newFilm) throws ValidationException {
        if (newFilm.getName() == null || newFilm.getName().isEmpty()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (newFilm.getDescription().getBytes().length > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
        if (Integer.parseInt(String.valueOf(newFilm.getDuration())) < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        newFilm.setId(getNextId());
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    /** Обновление данных существующего фильма */
    @PutMapping
    public Film update(@RequestBody Film newFilm) throws ValidationException {
        if (newFilm.getId() == null) {
            throw new ValidationException("id должен быть указан");
        }
        for (Film film : films.values()) {
            if (film.equals(newFilm)) {
                throw new ValidationException("Этот фильм уже есть в списке");
            }
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            return oldFilm;
        }
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }
}