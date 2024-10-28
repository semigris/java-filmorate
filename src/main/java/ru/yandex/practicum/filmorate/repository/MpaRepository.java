package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.mapper.MpaRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<Mpa> {

    public MpaRepository(JdbcTemplate jdbc, MpaRowMapper mpaRowMapper) {
        super(jdbc, mpaRowMapper);
    }

    public Optional<Mpa> get(Long mpaId) {
        return findOne("SELECT * FROM mpa WHERE id = ?", mpaId);
    }

    public Collection<Mpa> getAllMpas() {
        return findMany("SELECT * FROM mpa");
    }
}
