package ru.yandex.practicum.filmorate.service.mapper;

import ru.yandex.practicum.filmorate.dto.film.CreateFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmMapper {

    //Класс является утилитарным и не предназначен для создания объектов
    private FilmMapper() {
        throw new IllegalStateException("Utility class");
    }

    //Метод предназначен для преобразования объекта в объект передачи данных (DTO)
    public static FilmDto mapToFilmDto(Film film) {
        return FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .duration(film.getDuration())
                .releaseDate(film.getReleaseDate())
                .mpa(film.getMpa())
                .genres(film.getGenres())
                .likes(film.getLikes())
                .build();
    }

    //Метод используется для создания нового объекта из данных, которые передаются в качестве аргументов
    public static Film mapToFilm(CreateFilmRequest request) {
        Film film = Film.builder()
                .name(request.getName())
                .description(request.getDescription())
                .duration(request.getDuration())
                .releaseDate(request.getReleaseDate())
                .genres(request.getGenres())
                .likes(request.getLikes())
                .build();

        if (request.getMpa() != null) {
            film.setMpa(request.getMpa());
        }
        return film;
    }


    //Метод используется для обновления существующего объекта из данных, которые передаются в качестве аргументов
    public static Film updateFilmFields(Film film, UpdateFilmRequest request) {
        if (request.hasName()) {
            film.setName(request.getName());
        }

        if (request.hasDescription()) {
            film.setDescription(request.getDescription());
        }

        if (request.hasDuration()) {
            film.setDuration(request.getDuration());
        }

        if (request.hasReleaseDate()) {
            film.setReleaseDate(request.getReleaseDate());
        }

        if (request.hasMpa()) {
            film.setMpa(request.getMpa());
        }

        if (request.hasGenres()) {
            film.setGenres(request.getGenres());
        }

        if (request.hasLikes()) {
            film.setLikes(request.getLikes());
        }

        return film;
    }
}
