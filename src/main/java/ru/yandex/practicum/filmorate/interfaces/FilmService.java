package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film get(Long filmId);

    Film create(Film newFilm);

    Film update(Film newFilm);

    Collection<Film> getAllFilms();

    Collection<Film> getPopularFilms(int count);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
