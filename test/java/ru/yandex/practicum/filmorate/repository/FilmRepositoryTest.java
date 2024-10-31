package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.repository.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.repository.mapper.UserRowMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmRepository.class, FilmRowMapper.class, UserRepository.class, UserRowMapper.class, GenreRepository.class, GenreRowMapper.class})
class FilmRepositoryTest {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private Film film;
    private User user;

    @BeforeEach
    public void init() {
        film = Film.builder()
                .name("Film")
                .description("Description of Film")
                .duration(130L)
                .releaseDate(LocalDate.of(2004, 7, 20))
                .mpa(new Mpa(1L, "PG-13"))
                .build();

        user = User.builder()
                .email("user@mail.com")
                .login("Login")
                .name("User")
                .birthday(LocalDate.of(1994, 7, 20))
                .build();

        userRepository.create(user);
    }

    @Test
    void shouldCreateFilm() {
        Film createdFilm = filmRepository.create(film);

        assertThat(createdFilm.getName()).isEqualTo(film.getName());
        assertThat(createdFilm.getDescription()).isEqualTo(film.getDescription());
    }

    @Test
    void shouldGetFilmById() {
        Film createdFilm = filmRepository.create(film);
        Optional<Film> foundFilm = filmRepository.get(createdFilm.getId());

        assertThat(foundFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", createdFilm.getId())
                );
    }

    @Test
    void shouldUpdateFilm() {
        Film createdFilm = filmRepository.create(film);
        createdFilm.setName("New name");
        Film updatedFilm = filmRepository.update(createdFilm);

        assertThat(updatedFilm.getName()).isEqualTo("New name");
    }

    @Test
    void shouldDeleteFilm() {
        Film createdFilm = filmRepository.create(film);
        filmRepository.delete(createdFilm.getId());

        Optional<Film> foundFilm = filmRepository.get(createdFilm.getId());
        assertThat(foundFilm).isNotPresent();
    }

    @Test
    void shouldGetAllFilms() {
        filmRepository.create(film);
        Collection<Film> films = filmRepository.getAllFilms();

        assertThat(films)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void shouldGetPopularFilms() {
        Film createdFilm = filmRepository.create(film);
        filmRepository.addLike(createdFilm.getId(), user.getId());

        Collection<Film> popularFilms = filmRepository.getPopularFilms(10);
        assertThat(popularFilms)
                .isNotEmpty()
                .hasSize(1);
        assertThat(popularFilms.iterator().next().getId()).isEqualTo(createdFilm.getId());
    }
}