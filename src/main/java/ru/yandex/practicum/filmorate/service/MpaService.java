package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.mpa.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.repository.MpaRepository;
import ru.yandex.practicum.filmorate.service.mapper.MpaMapper;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaRepository mpaRepository;

    public MpaDto get(Long mpaId) {
        return mpaRepository.get(mpaId)
                .map(MpaMapper::mapToMpaDto)
                .orElseThrow(() -> new NotFoundException("Рейтинг с id " + mpaId + " не найден"));
    }

    public Collection<MpaDto> getAllMpas() {
        return mpaRepository.getAllMpas().stream()
                .map(MpaMapper::mapToMpaDto)
                .toList();
    }
}
