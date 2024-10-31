package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.mapper.FilmRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {

    private final GenreRepository genreRepository;
    private static final String GET_QUERY = """
            SELECT f.*,
                   m.name AS mpa_name
            FROM films AS f
            LEFT JOIN mpa AS m ON m.id = f.mpa
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
                   m.name AS mpa_name
            FROM films AS f
            LEFT JOIN mpa AS m ON f.mpa = m.id
            """;
    private static final String GET_POPULAR_FILMS_QUERY = """
            SELECT f.*,
                   m.name AS mpa_name,
                   COUNT(fl.user_id) AS likes_count
            FROM films AS f
            LEFT JOIN mpa AS m ON f.mpa = m.id
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
    private static final String ADD_GENRE_QUERY = """
            INSERT INTO film_genres(film_id, genre_id)
            VALUES (?, ?)
            """;

    public FilmRepository(JdbcTemplate jdbc, FilmRowMapper filmRowMapper, GenreRepository genreRepository) {
        super(jdbc, filmRowMapper);
        this.genreRepository = genreRepository;
    }

    public Optional<Film> get(Long filmId) {
        Optional<Film> optionalFilm = findOne(GET_QUERY, filmId);
        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            film.setGenres(genreRepository.putGenresToFilm(film.getId()));
        }
        return optionalFilm;
    }

    public Film create(Film newFilm) {
        long id = insert(
                CREATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getDuration(),
                newFilm.getReleaseDate(),
                newFilm.getMpa() != null ? newFilm.getMpa().getId() : null);
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
        Collection<Film> films = findMany(GET_ALL_QUERY);
        for (Film film : films) {
            film.setGenres(genreRepository.putGenresToFilm(film.getId()));
        }
        return films;
    }


    public Collection<Film> getPopularFilms(int count) {
        Collection<Film> films = findMany(GET_POPULAR_FILMS_QUERY, count);
        for (Film film : films) {
            film.setGenres(genreRepository.putGenresToFilm(film.getId()));
        }
        return films;
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

    public void addGenre(Long filmId, Long genreId) {
        update(
                ADD_GENRE_QUERY,
                filmId,
                genreId);
    }
}
