package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.interfaces.FilmService;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    /**
     * Получение фильма по их уникальному идентификатору
     */
    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable Long filmId) {
        log.info("Получение фильма: " + filmId);
        return filmService.get(filmId);
    }

    /**
     * Добавление нового фильма
     */
    @PostMapping
    public Film createFilm(@Valid @RequestBody Film newFilm) {
        log.info("Добавление информации о фильме: " + newFilm);
        return filmService.create(newFilm);
    }

    /**
     * Обновление данных существующего фильма
     */
    @PutMapping
    @Validated({Update.class})
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        log.info("Обновление информации о фильме: " + newFilm.getId());
        return filmService.update(newFilm);
    }

    /**
     * Получение списка фильмов
     */
    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Получение информации о фильмах");
        return filmService.getAllFilms();
    }

    /**
     * Возвращает список из первых count фильмов по количеству лайков.
     * Если значение параметра count не задано, вернет первые 10.
     */
    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam int count) {
        if (count == 0) {
            count = 10;
        }
        log.info("Составление списка из первых " + count + " фильмов по количеству лайков");
        return filmService.getPopularFilms(count);
    }

    /**
     * Пользователь ставит лайк фильму
     */
    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("Проставление лайка фильму " + filmId + " пользователем " + userId);
        filmService.addLike(filmId, userId);
    }

    /**
     * Пользователь удаляет лайк
     */
    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("Удаление лайка у фильма " + filmId + " пользователем " + userId);
        filmService.deleteLike(filmId, userId);
    }
}