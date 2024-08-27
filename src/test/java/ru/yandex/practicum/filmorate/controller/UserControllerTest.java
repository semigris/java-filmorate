package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    private final UserController userController = new UserController();
    User user = new User("useremail@mail.com", "userLogin", "User Name", (LocalDate.of(1956, 5, 18)));

    /**
     * Получение списка всех пользователей
     */
    @Test
    void shouldGetAllUsers() throws ValidationException {
        assertEquals(0, userController.getAllUsers().size(), "Список пользователей не пуст");
        userController.create(user);
        assertEquals(1, userController.getAllUsers().size(), "Список пользователей не изменился");
    }

    /**
     * Добавление нового пользователя
     */
    @Test
    void shouldCreateUser() throws ValidationException {
        User createdUser = userController.create(user);
        assertNotNull(createdUser.getId(), "id не совпадает");
        assertEquals("userLogin", createdUser.getLogin(), "Логин не совпадает");
        assertEquals("useremail@mail.com", createdUser.getEmail(), "Email не совпадает");
        assertEquals("User Name", createdUser.getName(), "Имя не совпадает");
        assertEquals((LocalDate.of(1956, 5, 18)), createdUser.getBirthday(), "Дата рождения не совпадает");
    }

    @Test
    void shouldCreateUserEmptyEmail() {
        user.setEmail("");
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", thrown.getMessage());
    }

    @Test
    void shouldCreateUserInvalidEmail() {
        user.setEmail("useremailmail.com");
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", thrown.getMessage());
    }

    @Test
    void shouldCreateUserInvalidLogin() {
        user.setLogin("");
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", thrown.getMessage());
    }

    @Test
    void shouldCreateUserInvalidBirthday() {
        user.setBirthday(LocalDate.of(2956, 5, 18));
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals("Дата рождения не может быть в будущем", thrown.getMessage());
    }

    /**
     * Обновление данных существующего пользователя
     */
    @Test
    void shouldUpdateUser() throws ValidationException {
        userController.create(user);
        User newUser = new User(user.getId(), "newuseremail@mail.com", "newUserLogin", "New User Name",
                (LocalDate.of(2010, 5, 18)));
        User createdUser = userController.update(newUser);
        assertNotNull(createdUser.getId(), "id не совпадает");
        assertEquals("newUserLogin", createdUser.getLogin(), "Логин не совпадает");
        assertEquals("newuseremail@mail.com", createdUser.getEmail(), "Email не совпадает");
        assertEquals("New User Name", createdUser.getName(), "Имя не совпадает");
        assertEquals((LocalDate.of(2010, 5, 18)), createdUser.getBirthday(), "Дата рождения не совпадает");
    }

    @Test
    void shouldUpdateInvalidUserWithoutId() throws ValidationException {
        userController.create(user);
        User newUser = new User("newuseremail@mail.com", "newUserLogin", "New User Name",
                (LocalDate.of(2010, 5, 18)));
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.update(newUser));
        assertEquals("id должен быть указан", thrown.getMessage());
    }

    @Test
    void shouldUpdateUserInvalidId() throws ValidationException {
        userController.create(user);
        User newUser = new User(42L, "newuseremail@mail.com", "newUserLogin", "New User Name",
                (LocalDate.of(2010, 5, 18)));
        ValidationException thrown = assertThrows(ValidationException.class, () -> userController.update(newUser));
        assertEquals("Пользователь с id = 42 не найден", thrown.getMessage());
    }
}