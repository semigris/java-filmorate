package ru.yandex.practicum.filmorate.service.mapper;

import ru.yandex.practicum.filmorate.dto.user.CreateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.model.User;

public class UserMapper {

    //Класс является утилитарным и не предназначен для создания объектов
    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    //Метод предназначен для преобразования объекта в объект передачи данных (DTO)
    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .friends(user.getFriends())
                .build();
    }

    //Метод используется для создания нового объекта из данных, которые передаются в качестве аргументов
    public static User mapToUser(CreateUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .login(request.getLogin())
                .name(request.getName())
                .birthday(request.getBirthday())
                .build();
    }

    //Метод используется для обновления существующего объекта из данных, которые передаются в качестве аргументов
    public static User updateUserFields(User userForUpdate, UpdateUserRequest request) {
        if (request.hasEmail()) {
            userForUpdate.setEmail(request.getEmail());
        }

        if (request.hasLogin()) {
            userForUpdate.setLogin(request.getLogin());
        }

        if (request.hasName()) {
            userForUpdate.setName(request.getName());
        }

        if (request.hasBirthday()) {
            userForUpdate.setBirthday(request.getBirthday());
        }
        return userForUpdate;
    }
}
