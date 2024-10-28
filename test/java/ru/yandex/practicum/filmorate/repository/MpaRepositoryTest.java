package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.mapper.MpaRowMapper;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaRepository.class, MpaRowMapper.class})
class MpaRepositoryTest {

    private final MpaRepository mpaRepository;

    @Test
    void shouldGetMpa() {
        Optional<Mpa> mpa = mpaRepository.get(1L);

        assertThat(mpa).isPresent();
        assertThat(mpa.get().getId()).isEqualTo(1L);
        assertThat(mpa.get().getName()).isEqualTo("G");
    }

    @Test
    void shouldGetAllMpas() {
        Collection<Mpa> mpas = mpaRepository.getAllMpas();

        assertThat(mpas)
                .isNotEmpty()
                .hasSize(5);
    }
}
