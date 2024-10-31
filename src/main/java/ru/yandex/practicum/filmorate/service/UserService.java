package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.user.CreateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.mapper.UserMapper;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto get(Long userId) throws NotFoundException {
        return userRepository.get(userId)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
    }

    public UserDto create(CreateUserRequest newUser) {
        Optional.ofNullable(newUser)
                .orElseThrow(() -> new NotFoundException("Информация о пользователе не может быть NULL"));
        User user = UserMapper.mapToUser(newUser);
        userRepository.create(user);
        return UserMapper.mapToUserDto(user);
    }

    public UserDto update(UpdateUserRequest newUser) {
        log.info("Проверка " + newUser.getId());
        User userForUpdate = userRepository.get(newUser.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + newUser.getId() + " не найден"));
        User updatedUser = UserMapper.updateUserFields(userForUpdate, newUser);
        log.info("Обновление информации о пользователе " + newUser.getId());
        userRepository.update(updatedUser);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public void delete(Long userId) {
        userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        userRepository.delete(userId);
    }

    public Collection<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    public void addFriend(Long userId, Long friendId) {
        userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        userRepository.get(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + friendId + " не найден"));
        userRepository.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        userRepository.get(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + friendId + " не найден"));
        userRepository.deleteFriend(userId, friendId);
    }

    public Collection<UserDto> getUserFriends(Long userId) {
        userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        return userRepository.getUserFriends(userId).stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    public Collection<UserDto> getCommonFriends(Long userId, Long otherId) {
        userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        userRepository.get(otherId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + otherId + " не найден"));
        return userRepository.getCommonFriends(userId, otherId).stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }
}
