package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mapper.UserRowMapper;

import java.util.Collection;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> {
    private static final String GET_QUERY = """
            SELECT u.*
            FROM users AS u
            WHERE u.id = ?
            """;

    private static final String CREATE_QUERY = """
            INSERT INTO users(email, login, name, birthday)
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_QUERY = """
            UPDATE users
            SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?
            """;
    private static final String DELETE_QUERY = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String GET_ALL_QUERY = """
            SELECT u.*,
                   f.friend_id AS friends
            FROM users AS u
            LEFT JOIN friends f ON u.id = f.user_id
            GROUP BY u.id
            """;

    private static final String ADD_FRIEND_QUERY = """
            INSERT INTO friends(user_id, friend_id, status)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_FRIEND_QUERY = """
            DELETE FROM friends
            WHERE user_id = ? AND friend_id = ?
            """;

    private static final String GET_USER_FRIENDS_QUERY = """
            SELECT u.id, u.email, u.login, u.name, u.birthday
            FROM users AS u
            WHERE id IN (SELECT friend_id FROM friends WHERE user_id = ? AND status = 'ACCEPTED');
            """;
    private static final String GET_COMMON_FRIENDS_QUERY = """
            SELECT u.id, u.email, u.login, u.name, u.birthday
            FROM users AS u
            JOIN friends AS uf ON u.id = uf.friend_id
            JOIN friends AS of ON u.id = of.friend_id
            WHERE uf.user_id = ? AND of.user_id = ?
            AND uf.status = 'ACCEPTED' AND of.status = 'ACCEPTED';
            """;

    public UserRepository(JdbcTemplate jdbc, UserRowMapper userRowMapper) {
        super(jdbc, userRowMapper);
    }

    public Optional<User> get(Long userId) {
        return findOne(
                GET_QUERY,
                userId);
    }


    public User create(User newUser) {
        long id = insert(
                CREATE_QUERY,
                newUser.getEmail(),
                newUser.getLogin(),
                newUser.getName(),
                newUser.getBirthday()
        );
        newUser.setId(id);
        return newUser;
    }


    public User update(User newUser) {
        update(
                UPDATE_QUERY,
                newUser.getEmail(),
                newUser.getLogin(),
                newUser.getName(),
                newUser.getBirthday(),
                newUser.getId()
        );
        return newUser;
    }

    public void delete(Long userId) {
        delete(
                DELETE_QUERY,
                userId);
    }

    public Collection<User> getAllUsers() {
        return findMany(GET_ALL_QUERY);
    }


    public void addFriend(Long userId, Long friendId) {
        update(
                ADD_FRIEND_QUERY,
                userId,
                friendId,
                FriendStatus.ACCEPTED.toString());
    }


    public void deleteFriend(Long userId, Long friendId) {
        delete(
                DELETE_FRIEND_QUERY,
                userId,
                friendId);
    }


    public Collection<User> getUserFriends(Long userId) {
        return findMany(
                GET_USER_FRIENDS_QUERY,
                userId);
    }


    public Collection<User> getCommonFriends(Long userId, Long otherId) {
        return findMany(
                GET_COMMON_FRIENDS_QUERY,
                userId,
                otherId);
    }
}
