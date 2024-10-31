package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{genreId}")
    public GenreDto get(@PathVariable Long genreId) {
        log.info("Получение информации о жанре " + genreId);
        return genreService.get(genreId);
    }

    @GetMapping
    public Collection<GenreDto> getAllGenres() {
        log.info("Составление списка всех жанров");
        return genreService.getAllGenres();
    }
}
