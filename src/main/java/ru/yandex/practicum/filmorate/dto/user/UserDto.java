package ru.yandex.practicum.filmorate.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserDto {
    Long id;
    String email;
    String login;
    String name;
    LocalDate birthday;
    Set<Long> friends;
}
