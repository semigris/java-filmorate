package ru.yandex.practicum.filmorate.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    //Преобразует данные из каждой ячейки в Java-сущности
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .duration(resultSet.getLong("duration"))
                .releaseDate(resultSet.getDate("releaseDate").toLocalDate())
                .mpa(new Mpa(resultSet.getLong("mpa"), resultSet.getString("mpa_name")))
                .build();

        try {
            do {
                long id = resultSet.getLong("genre_id");
                if (id == 0) {
                    continue;
                }
                Genre genre = Genre.builder()
                        .id(id)
                        .name(resultSet.getString("genre_name"))
                        .build();
                if (film.getGenres() == null) {
                    film.setGenres(new LinkedHashSet<>());
                }
                film.getGenres().add(genre);
            } while (resultSet.next());
        } catch (SQLException e) {
            //
        }

        return film;
    }
}
