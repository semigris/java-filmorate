package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.UserRepository;
import ru.yandex.practicum.filmorate.interfaces.UserService;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public User get(Long userId) throws NotFoundException {
        User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        return user;
    }

    @Override
    public User create(User newUser) {
        if (newUser == null) {
            throw new NotFoundException("Информация о пользователе не может быть NULL");
        }
        return userRepository.create(newUser);
    }

    @Override
    public User update(User newUser) {
        if (userRepository.get(newUser.getId()) == null) {
            throw new NotFoundException("Пользователь с id " + newUser.getId() + " не найден");
        }
        return userRepository.update(newUser);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else if (userRepository.get(friendId) == null) {
            throw new NotFoundException("Пользователь с id " + friendId + " не найден");
        }
        userRepository.addFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else if (userRepository.get(friendId) == null) {
            throw new NotFoundException("Пользователь с id " + friendId + " не найден");
        }
        userRepository.deleteFriend(userId, friendId);
    }

    @Override
    public Collection<User> getUserFriends(Long userId) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        return userRepository.getUserFriends(userId);
    }

    @Override
    public Collection<User> getCommonFriends(Long userId, Long otherId) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else if (userRepository.get(otherId) == null) {
            throw new NotFoundException("Пользователь с id " + otherId + " не найден");
        }
        return userRepository.getCommonFriends(userId, otherId);
    }
}
