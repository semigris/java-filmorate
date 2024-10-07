package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 1;

    @Override
    public User get(Long userId) {
        return users.get(userId);
    }

    @Override
    public User create(User newUser) {
        if (newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
        }
        newUser.setId(userId++);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User update(User newUser) {
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            return oldUser;
        }
        throw new NotFoundException("Пользователь с id " + newUser.getId() + " не найден");
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    @Override
    public Collection<User> getUserFriends(Long userId) {
        User user = users.get(userId);
        Set<Long> friendsIds = user.getFriends();
        Collection<User> userFriends = new ArrayList<>();
        for (Long id : friendsIds) {
            userFriends.add(users.get(id));
        }
        return userFriends;
    }

    @Override
    public Collection<User> getCommonFriends(Long userId, Long otherId) {
        Set<Long> userFriendsIds = users.get(userId).getFriends();
        Set<Long> otherUserFriendsIds = users.get(otherId).getFriends();
        Collection<User> commonFriends = new ArrayList<>();
        for (Long id1 : userFriendsIds) {
            for (Long id2 : otherUserFriendsIds) {
                if (Objects.equals(id1, id2)) {
                    commonFriends.add(users.get(id2));
                }
            }
        }
        return commonFriends;
    }
}
