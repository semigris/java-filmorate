package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.FilmRepository;
import ru.yandex.practicum.filmorate.interfaces.FilmService;
import ru.yandex.practicum.filmorate.interfaces.UserRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DefaultFilmService implements FilmService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    @Override
    public Film get(Long filmId) {
        Film film = filmRepository.get(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        return filmRepository.get(filmId);
    }

    @Override
    public Film create(Film newFilm) {
        if (newFilm == null) {
            throw new NotFoundException("Информация о фильме не может быть NULL");
        }
        return filmRepository.create(newFilm);
    }

    @Override
    public Film update(Film newFilm) {
        if (filmRepository.get(newFilm.getId()) == null) {
            throw new NotFoundException("Фильм с id " + newFilm.getId() + " не найден");
        }
        return filmRepository.update(newFilm);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else if (filmRepository.get(filmId) == null) {
            throw new NotFoundException("Фильм с id " + userId + " не найден");
        }
        filmRepository.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else if (filmRepository.get(filmId) == null) {
            throw new NotFoundException("Фильм с id " + userId + " не найден");
        }
        filmRepository.deleteLike(filmId, userId);
    }
}
