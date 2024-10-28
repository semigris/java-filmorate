package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mapper.GenreRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
public class GenreRepository extends BaseRepository<Genre> {

    public GenreRepository(JdbcTemplate jdbc, GenreRowMapper genreRowMapper) {
        super(jdbc, genreRowMapper);
    }

    public Optional<Genre> get(Long genreId) {
        return findOne("SELECT * FROM genres WHERE id = ?", genreId);
    }

    public Collection<Genre> getAllGenres() {
        return findMany("SELECT * FROM genres");
    }
}