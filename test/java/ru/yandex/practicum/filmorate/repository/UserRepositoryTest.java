package ru.yandex.practicum.filmorate.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mapper.UserRowMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserRepository.class, UserRowMapper.class})
class UserRepositoryTest {
    private final UserRepository userRepository;
    private User user;
    @BeforeEach
    public void init() {
        user = User.builder()
                .email("user@mail.com")
                .login("Login")
                .name("User")
                .birthday(LocalDate.of(1994, 7, 20))
                .build();
    }

    @Test
    void shouldCreateUser () {
        User createdUser = userRepository.create(user);

        assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(createdUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(createdUser.getName()).isEqualTo(user.getName());
    }

    @Test
    void shouldGetUserById() {
        User createdUser = userRepository.create(user);
        Optional<User> foundUser  = userRepository.get(createdUser.getId());

        assertThat(foundUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", createdUser.getId())
                );
    }

    @Test
    void shouldUpdateUser () {
        User createdUser = userRepository.create(user);
        createdUser.setName("New name");
        User updatedUser = userRepository.update(createdUser);

        assertThat(updatedUser.getName()).isEqualTo("New name");
    }

    @Test
    void shouldDeleteUser () {
        User createdUser = userRepository.create(user);
        userRepository.delete(createdUser.getId());

        Optional<User> foundUser = userRepository.get(createdUser.getId());
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void shouldGetAllUsers() {
        userRepository.create(user);
        Collection<User> users = userRepository.getAllUsers();

        assertThat(users)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void shouldAddFriend() {
        User user1 = userRepository.create(user);
        User user2 = User.builder()
                .email("user2@mail.com")
                .login("Login 2")
                .name("User 2")
                .birthday(LocalDate.of(2004, 7, 20))
                .build();
        userRepository.create(user2);

        userRepository.addFriend(user1.getId(), user2.getId());

        Collection<User> friends = userRepository.getUserFriends(user1.getId());
        assertThat(friends).hasSize(1);
    }

    @Test
    void shouldDeleteFriend() {
        User user1 = userRepository.create(user);
        User user2 = User.builder()
                .email("user2@mail.com")
                .login("Login 2")
                .name("User 2")
                .birthday(LocalDate.of(2004, 7, 20))
                .build();
        userRepository.create(user2);

        userRepository.addFriend(user1.getId(), user2.getId());
        userRepository.deleteFriend(user1.getId(), user2.getId());

        Collection<User> friends = userRepository.getUserFriends(user1.getId());
        assertThat(friends).isEmpty();
    }

    @Test
    void shouldGetCommonFriends() {
        User user1 = userRepository.create(user);
        User user2 = User.builder()
                .email("user2@mail.com")
                .login("Login 2")
                .name("User 2")
                .birthday(LocalDate.of(2004, 7, 20))
                .build();
        User user3 = User.builder()
                .email("user3@mail.com")
                .login("Login 3")
                .name("User 3")
                .birthday(LocalDate.of(2004, 7, 20))
                .build();

        userRepository.create(user2);
        userRepository.create(user3);

        userRepository.addFriend(user1.getId(), user3.getId());
        userRepository.addFriend(user2.getId(), user3.getId());

        Collection<User> commonFriends = userRepository.getCommonFriends(user1.getId(), user2.getId());
        assertThat(commonFriends).hasSize(1);
    }
}