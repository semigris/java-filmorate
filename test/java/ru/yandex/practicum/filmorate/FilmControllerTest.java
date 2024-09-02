package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmControllerTest {
    private static final Validator validator;
    Film film = new Film("filmName", "filmDescription", (LocalDate.of(1956, 5, 18)), 120L);

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldValidateFilmId() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film, Update.class);
        assertEquals("id должен быть указан", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateEmptyName() {
        film.setName("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Название не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateLongDescription() {
        film.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur metus lectus, " +
                "congue sed felis quis, egestas feugiat nulla. In hac habitasse platea dictumst. " +
                "In eu ante a mauris tempor finibus cras.");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Максимальная длина описания — 200 символов", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateInvalidDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Дата релиза не должна быть раньше 28 декабря 1895 года", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateInvalidDuration() {
        film.setDuration(-1L);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Продолжительность фильма должна быть положительным числом", violations.iterator().next().getMessage());
    }
}