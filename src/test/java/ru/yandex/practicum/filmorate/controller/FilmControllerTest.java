package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FilmControllerTest {

    private final FilmController filmController = new FilmController();
    Film film = new Film("filmName", "filmDescription", (LocalDate.of(1956, 5, 18)), 120L);

    /** Получение списка фильмов */
    @Test
    void shouldGetAllFilms() throws ValidationException {
        assertEquals(0, filmController.getAllFilms().size(), "Список фильмов не пуст");
        filmController.create(film);
        assertEquals(1, filmController.getAllFilms().size(), "Список фильмов не изменился");
    }

    /** Добавление нового фильма */
    @Test
    void shouldCreateFilm() throws ValidationException {
        Film createdFilm = filmController.create(film);
        assertNotNull(createdFilm.getId(), "id не совпадает");
        assertEquals("filmName", createdFilm.getName(), "Название не совпадает");
        assertEquals("filmDescription", createdFilm.getDescription(), "Описание не совпадает");
        assertEquals((LocalDate.of(1956, 5, 18)), createdFilm.getReleaseDate(), "Дата создания не совпадает");
        assertEquals(120L, createdFilm.getDuration(), "Продолжительность не совпадает");
    }

    @Test
    void shouldCreateFilmEmptyName(){
        film.setName("");
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Название не может быть пустым", thrown.getMessage());
    }

    @Test
    void shouldCreateFilmLongDescription(){
        film.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur metus lectus, " +
                "congue sed felis quis, egestas feugiat nulla. In hac habitasse platea dictumst. " +
                "In eu ante a mauris tempor finibus cras.");
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Максимальная длина описания — 200 символов", thrown.getMessage());
    }
    @Test
    void shouldCreateFilmInvalidDate(){
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Дата релиза не должна быть раньше 28 декабря 1895 года", thrown.getMessage());
    }

    @Test
    void shouldCreateFilmInvalidDuration(){
        film.setDuration(-1L);
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Продолжительность фильма должна быть положительным числом", thrown.getMessage());
    }

    /** Обновление данных существующего фильма */
    @Test
    void shouldUpdateFilm() throws ValidationException {
        filmController.create(film);
        Film newFilm = new Film(film.getId(),"newFilmName", "newFilmDescription", (LocalDate.of(2003, 5, 18)), 20L);
        Film createdFilm = filmController.update(newFilm);
        assertNotNull(createdFilm.getId(), "id не совпадает");
        assertEquals("newFilmName", createdFilm.getName(), "Название не совпадает");
        assertEquals("newFilmDescription", createdFilm.getDescription(), "Описание не совпадает");
        assertEquals((LocalDate.of(2003, 5, 18)), createdFilm.getReleaseDate(), "Дата создания не совпадает");
        assertEquals(20L, createdFilm.getDuration(), "Продолжительность не совпадает");
    }
    @Test
    void shouldUpdateInvalidFilmWithoutId() throws ValidationException {
        filmController.create(film);
        Film newFilm = new Film("newFilmName", "newFilmDescription", (LocalDate.of(2003, 5, 18)), 20L);
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.update(newFilm));
        assertEquals("id должен быть указан", thrown.getMessage());
    }

    @Test
    void shouldUpdateFilmInvalidId() throws ValidationException {
        filmController.create(film);
        Film newFilm = new Film(42L,"newFilmName", "newFilmDescription", (LocalDate.of(2003, 5, 18)), 20L);
        ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.update(newFilm));
        assertEquals("Фильм с id = 42 не найден", thrown.getMessage());
    }
}