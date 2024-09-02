package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.interfaces.Update;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserControllerTest {
    private static final Validator validator;
    User user = new User("useremail@mail.com", "userLogin", "userName", (LocalDate.of(1956, 5, 18)));

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldValidateUserId() {
        Set<ConstraintViolation<User>> violations = validator.validate(user, Update.class);
        assertEquals("id должен быть указан", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateInvalidEmail() {
        user.setEmail("это-неправильный?эмейл@");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Электронная почта некорректна", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateEmptyEmail() {
        user.setEmail("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Электронная почта не может быть пустой", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateEmptyLogin() {
        user.setLogin("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Логин не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateInvalidLogin() {
        user.setLogin("User Login");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Логин не может содержать пробелы", violations.iterator().next().getMessage());
    }

    @Test
    void shouldValidateInvalidBirthday() {
        user.setBirthday(LocalDate.of(2956, 5, 18));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("Дата рождения не может быть в будущем", violations.iterator().next().getMessage());
    }
}