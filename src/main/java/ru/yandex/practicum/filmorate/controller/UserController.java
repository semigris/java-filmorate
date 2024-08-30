package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 1;

    /**
     * Получение списка всех пользователей
     */
    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Добавление нового пользователя
     */
    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        if (newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
        }
        newUser.setId(userId++);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    /**
     * Обновление данных существующего пользователя
     */
    @PutMapping
    @Validated({Update.class})
    public User update(@RequestBody User newUser) throws NotFoundException {
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }
}