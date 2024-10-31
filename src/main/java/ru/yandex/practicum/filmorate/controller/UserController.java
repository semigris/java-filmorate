package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.user.CreateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Получение данных о пользователях по их уникальному идентификатору
     */
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        log.info("Получение данных о пользователе " + userId);
        return userService.get(userId);
    }

    /**
     * Добавление нового пользователя
     */
    @PostMapping
    public UserDto createUser(@Valid @RequestBody CreateUserRequest newUser) {
        log.info("Добавление информации о пользователе " + newUser);
        return userService.create(newUser);
    }

    /**
     * Обновление данных существующего пользователя
     */
    @PutMapping
    @Validated({Update.class})
    public UserDto updateUser(@RequestBody UpdateUserRequest newUser) {
        log.info("Обновление информации о пользователе " + newUser.getId());
        return userService.update(newUser);
    }

    /**
     * Удаление пользователя
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Удаление пользователя " + userId);
        userService.delete(userId);
    }

    /**
     * Получение списка всех пользователей
     */
    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("Получение информации о пользователях");
        return userService.getAllUsers();
    }

    /**
     * Добавление в друзья
     */
    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Добавление пользователю " + userId + " друга " + friendId);
        userService.addFriend(userId, friendId);
    }

    /**
     * Удаление из друзей
     */
    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Удаление у пользователя " + userId + " друга " + friendId);
        userService.deleteFriend(userId, friendId);
    }

    /**
     * Возвращаем список друзей пользователя
     */
    @GetMapping("/{userId}/friends")
    public Collection<UserDto> getUserFriends(@PathVariable Long userId) {
        log.info("Получение списка друзей пользователя " + userId);
        return userService.getUserFriends(userId);
    }

    /**
     * Список друзей, общих с другим пользователем
     */
    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<UserDto> getCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        log.info("Получение списка общих друзей пользователей " + userId + " и " + otherId);
        return userService.getCommonFriends(userId, otherId);
    }
}