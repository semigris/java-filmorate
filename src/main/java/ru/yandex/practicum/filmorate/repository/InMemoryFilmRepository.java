package ru.yandex.practicum.filmorate.repository;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.comparator.FilmLikesComparator;
import ru.yandex.practicum.filmorate.interfaces.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmRepository implements FilmRepository {
    private final Map<Long, Film> films = new HashMap<>();
    private long filmId = 1;

    @Override
    public Film get(Long filmId) {
        return films.get(filmId);
    }


    @Override
    public Film create(Film newFilm) {
        newFilm.setId(filmId++);
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public Film update(Film newFilm) {
        Film oldFilm = films.get(newFilm.getId());
        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDuration(newFilm.getDuration());
        return oldFilm;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        FilmLikesComparator compare = new FilmLikesComparator();
        return films.values().stream()
                .sorted(compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        Film film = films.get(filmId);
        film.getLikes().add(userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        Film film = films.get(filmId);
        film.getLikes().remove(userId);
    }
}

