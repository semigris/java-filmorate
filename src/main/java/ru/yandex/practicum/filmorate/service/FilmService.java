package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.film.CreateFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.mapper.FilmMapper;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final MpaService mpaService;
    private final GenreService genreService;

    public FilmDto get(Long filmId) {
        return filmRepository.get(filmId)
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new NotFoundException("Фильм с id " + filmId + " не найден"));
    }

    public FilmDto create(CreateFilmRequest newFilm) {
        Optional.ofNullable(newFilm)
                .orElseThrow(() -> new NotFoundException("Информация о фильме не может быть NULL"));
        if (newFilm.getMpa() != null) {
            try {
                mpaService.get(newFilm.getMpa().getId());
            } catch (Exception e) {
                throw new NotValidException("Рейтинг с id " + newFilm.getMpa().getId() + " не найден");
            }
        }
        if (newFilm.getGenres() != null) {
            for (var genre : newFilm.getGenres()) {
                try {
                    genreService.get(genre.getId());
                } catch (Exception e) {
                    throw new NotValidException("Жанр с id " + genre.getId() + " не найден");
                }
            }
        }
        Film film = FilmMapper.mapToFilm(newFilm);
        filmRepository.create(film);
        if (newFilm.getGenres() != null) {
            for (var genre : newFilm.getGenres()) {
                filmRepository.addGenre(film.getId(), genre.getId());
            }
        } else {
            film.setGenres(new LinkedHashSet<>());
        }

        return FilmMapper.mapToFilmDto(film);
    }

    public FilmDto update(UpdateFilmRequest newFilm) {
        Film filmForUpdate = filmRepository.get(newFilm.getId())
                .orElseThrow(() -> new NotFoundException("Фильм с id " + newFilm.getId() + " не найден"));
        Film film = FilmMapper.updateFilmFields(filmForUpdate, newFilm);
        filmRepository.update(film);
        return FilmMapper.mapToFilmDto(film);
    }

    public void delete(Long filmId) {
        filmRepository.get(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id " + filmId + " не найден"));
        filmRepository.delete(filmId);
    }

    public Collection<FilmDto> getAllFilms() {
        return filmRepository.getAllFilms().stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    public Collection<FilmDto> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count).stream()
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }

    public void addLike(Long filmId, Long userId) {
        Optional.ofNullable(userRepository.get(userId))
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        filmRepository.get(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id " + filmId + " не найден"));
        filmRepository.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        Optional.ofNullable(userRepository.get(userId))
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        filmRepository.get(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id " + filmId + " не найден"));
        filmRepository.deleteLike(filmId, userId);
    }
}
