package ru.yandex.practicum.filmorate.service.mapper;

import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

public class MpaMapper {

    //Класс является утилитарным и не предназначен для создания объектов
    private MpaMapper() {
        throw new IllegalStateException("Utility class");
    }

    //Метод предназначен для преобразования объекта в объект передачи данных (DTO)
    public static MpaDto mapToMpaDto(Mpa mpa) {
        return MpaDto.builder()
                .id(mpa.getId())
                .name(mpa.getName())
                .build();
    }

    //Метод используется для создания нового объекта из данных, которые передаются в качестве аргументов
    public static Mpa mapToMpa(Long mpaId) {
        return Mpa.builder()
                .id(mpaId)
                .build();
    }


}
