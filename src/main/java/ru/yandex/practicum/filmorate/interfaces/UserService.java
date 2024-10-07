package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {

    User get(Long userId);

    User create(User newUser);

    User update(User newUser);

    Collection<User> getAllUsers();

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    Collection<User> getUserFriends(Long userId);

    Collection<User> getCommonFriends(Long userId, Long otherId);
}
