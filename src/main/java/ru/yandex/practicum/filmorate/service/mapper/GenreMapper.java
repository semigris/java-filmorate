package ru.yandex.practicum.filmorate.service.mapper;

import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

public class GenreMapper {

    //Класс является утилитарным и не предназначен для создания объектов
    private GenreMapper() {
        throw new IllegalStateException("Utility class");
    }

    //Метод предназначен для преобразования объекта в объект передачи данных (DTO)
    public static GenreDto mapToGenreDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    //Метод используется для создания нового объекта из данных, которые передаются в качестве аргументов
    public static Genre mapToGenre(Long genreId) {
        return Genre.builder()
                .id(genreId)
                .build();
    }


}