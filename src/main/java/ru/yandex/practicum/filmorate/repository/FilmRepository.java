package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.mapper.FilmRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {
    private static final String GET_QUERY = """
            SELECT f.*,
                   m.name
            FROM films AS f
            LEFT JOIN mpa AS m ON f.id = m.id
            WHERE f.id = ?
             """;

    private static final String CREATE_QUERY = """
            INSERT INTO films(name, description, duration, releaseDate, mpa)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_QUERY = """
            UPDATE films
            SET name = ?, description = ?, duration = ?, releaseDate = ?, mpa = ? WHERE id = ?
            """;
    private static final String REMOVE_QUERY = """
            DELETE FROM films
            WHERE id = ?
            """;
    private static final String GET_ALL_QUERY = """
            SELECT f.*,
                   m.id,
                   m.name
            FROM films AS f
            LEFT JOIN mpa AS m ON f.id = m.id
            """;

    private static final String GET_POPULAR_FILMS_QUERY = """
            SELECT f.*,
            COUNT(fl.user_id) AS likes_count
            FROM films AS f
            LEFT JOIN film_likes AS fl ON f.id = fl.film_id
            GROUP BY f.id
            ORDER BY likes_count DESC
            LIMIT ?
            """;
    private static final String ADD_LIKE_QUERY = """
            INSERT INTO film_likes(film_id, user_id)
            VALUES (?, ?)
            """;

    private static final String DELETE_LIKE_QUERY = """
            DELETE FROM film_likes
            WHERE film_id = ?
            AND user_id = ?
            """;

    public FilmRepository(JdbcTemplate jdbc, FilmRowMapper filmRowMapper) {
        super(jdbc, filmRowMapper);
    }

    public Optional<Film> get(Long filmId) {
        return findOne(
                GET_QUERY,
                filmId);
    }

    public Film create(Film newFilm) {
        long id = insert(
                CREATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getDuration(),
                newFilm.getReleaseDate(),
                newFilm.getMpa().getId()
        );
        newFilm.setId(id);
        return newFilm;
    }


    public Film update(Film newFilm) {
        update(
                UPDATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getDuration(),
                newFilm.getReleaseDate(),
                newFilm.getMpa().getId(),
                newFilm.getId()
        );
        return newFilm;
    }

    public void delete(Long filmId) {
        delete(
                REMOVE_QUERY,
                filmId);
    }

    public Collection<Film> getAllFilms() {
        return findMany(GET_ALL_QUERY);
    }


    public Collection<Film> getPopularFilms(int count) {
        return findMany(
                GET_POPULAR_FILMS_QUERY,
                count);
    }


    public void addLike(Long filmId, Long userId) {
        update(
                ADD_LIKE_QUERY,
                filmId,
                userId);
    }


    public void deleteLike(Long filmId, Long userId) {
        delete(
                DELETE_LIKE_QUERY,
                filmId,
                userId);
    }
}
