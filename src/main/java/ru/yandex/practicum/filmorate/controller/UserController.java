package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.interfaces.UserService;
import ru.yandex.practicum.filmorate.model.User;

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
    @GetMapping("/{UserId}")
    public User getUser(@PathVariable Long userId) {
        log.info("Получение данных о пользователе: " + userId);
        return userService.get(userId);
    }

    /**
     * Добавление нового пользователя
     */
    @PostMapping
    public User createUser(@Valid @RequestBody User newUser) {
        log.info("Добавление информации о пользователе: " + newUser);
        return userService.create(newUser);
    }

    /**
     * Обновление данных существующего пользователя
     */
    @PutMapping
    @Validated({Update.class})
    public User updateUser(@RequestBody User newUser) throws NotFoundException {
        log.info("Обновление информации о пользователе: " + newUser.getId());
        return userService.update(newUser);
    }

    /**
     * Получение списка всех пользователей
     */
    @GetMapping
    public Collection<User> getAllUsers() {
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
    public Collection<User> getUserFriends(@PathVariable Long userId) {
        log.info("Получение списка друзей пользователя " + userId);
        return userService.getUserFriends(userId);
    }

    /**
     * Список друзей, общих с другим пользователем
     */
    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        log.info("Получение списка общих друзей пользователей " + userId + " и " + otherId);
        return userService.getCommonFriends(userId, otherId);
    }
}