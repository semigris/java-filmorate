package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    /** Получение списка всех пользователей */
    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /** Добавление нового пользователя */
    @PostMapping
    public User create(@RequestBody User newUser) throws ValidationException {
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty() || !newUser.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (newUser.getLogin() == null || newUser.getLogin().isEmpty() || newUser.getEmail().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (newUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
        }
        newUser.setId(getNextId());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    /** Обновление данных существующего пользователя */
    public User update(@RequestBody User newUser) throws ValidationException {
        if (newUser.getId() == null) {
            throw new ValidationException("id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            return oldUser;
        }
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }
}