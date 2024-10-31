package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.repository.GenreRepository;
import ru.yandex.practicum.filmorate.service.mapper.GenreMapper;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreDto get(Long genreId) {
        return genreRepository.get(genreId)
                .map(GenreMapper::mapToGenreDto)
                .orElseThrow(() -> new NotFoundException("Жанр с id " + genreId + " не найден"));
    }

    public Collection<GenreDto> getAllGenres() {
        return genreRepository.getAllGenres().stream()
                .map(GenreMapper::mapToGenreDto)
                .toList();
    }
}
