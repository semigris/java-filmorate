# java-filmorate

### Entity Relationship Diagrams (ERD)
![Mind Map.jpg](..%2F..%2FDesktop%2FMind%20Map.jpg)

### Пояснение к схеме:
#### Таблица `mpa`:
Хранит информацию о рейтингах фильмов.
Атрибуты: `id`, `name`.

#### Таблица `films`:
Содержит данные о фильмах, включая их название, описание, продолжительность, дату выхода и ссылку на рейтинг. \
Атрибуты: `id`, `name`, `description`, `duration`, `releaseDate`, `mpa`. \
Связи: `mpa` (Foreign Key) ссылается на mpa(id).

#### Таблица `genres`:
Содержит уникальные жанры фильмов. \
Атрибуты: `id`, `name`.

#### Таблица `film_genre`:
Связывает фильмы и жанры. \
Атрибуты: `film_id`, `genre_id`. \
Связи: `film_id` (Foreign Key) ссылается на `films(id)`, `genre_id` (Foreign Key) ссылается на `genres(id)`.

#### Таблица `users`:
Хранит информацию о пользователях. \
Атрибуты: `id`, `email`, `login`, `name`, `birthday`.

#### Таблица `film_likes`:
Связывает фильмы и пользователей, показывая, какие фильмы понравились каким пользователям. \
Атрибуты: `film_id`, `user_id`. \
Связи: `film_id` (Foreign Key) ссылается на `films(id)`, `user_id` (Foreign Key) ссылается на `users(id)`.

#### Таблица `friends`:
Хранит информацию о дружеских связях между пользователями и статусе этих связей. \
Атрибуты: `user_id`, `friend_id`, `status`. \
Связи: `user_id` (Foreign Key) ссылается на `users(id)`, `friend_id` (Foreign Key) ссылается на `users(id)`.

### Примеры запросов для основных операций приложения:
#### 1. Добавление нового фильма
```
INSERT INTO films (name, description, duration, releaseDate, mpa)
VALUES ('Название фильма', 'Описание фильма', 120, '2023-10-01', 1);
```

### 2. Получение списка всех фильмов
```
SELECT f.*,
       m.id,
       m.name
FROM films AS f
LEFT JOIN mpa AS m ON f.id = m.id;
```

#### 3. Получение информации о фильме по его ID
```
SELECT f.*,
       m.name
FROM films AS f
LEFT JOIN mpa AS m ON f.id = m.id
WHERE f.id = ?;
```

#### 4. Регистрация нового пользователя
```
INSERT INTO users (email, login, name, birthday)
VALUES ('user@example.com', 'user123', 'Имя пользователя', '1990-01-01');
```

#### 5. Получение информации о пользователе по его ID
```
SELECT u.*
FROM users AS u
WHERE u.id = ?;
```

#### 6. Добавление друга
```
INSERT INTO friends (user_id, friend_id, status) 
VALUES (1, 2, 'ACCEPTED');
```

#### 7. Получение списка друзей пользователя
```
SELECT u.id, u.email, u.login, u.name, u.birthday
FROM users AS u
WHERE id IN (SELECT friend_id FROM friends WHERE user_id = ? AND status = 'ACCEPTED');
```

---

_Переменная пути_ \
— используется, когда часть пути эндпоинта может изменяться в зависимости от идентификатора ресурса, который нужно получить. \
Путь с использованием переменной пути выглядит так: `.../posts/{postId}`. \
Например: `.../posts/123` \
В Spring для обращения к переменной пути используют аннотацию `@PathVariable`. 
Ею помечают аргумент метода контроллера: `public Optional<Post> findById(@PathVariable int postId)`. \
Если имя переменной пути и аргумента метода совпадают, Spring автоматически их свяжет. В переменную postId попадёт значение соответствующей переменной пути. \

_Строка запроса_ \
— применяется, чтобы добавить к запросу дополнительные параметры. \
Аргументы указываются в формате параметр=значение и отделяются друг от друга символом `&`. \
От пути строку запроса отделяют знаком вопроса. \
Например: `/posts/search?author=Bob&date=2020-12-3`
Для получения параметра запроса в Spring используют аннотацию `@RequestParam`. \
Ею помечают аргумент метода контроллера: `public List<Post> searchPosts(@RequestParam String author)`. \
Если имя параметра запроса и аргумента метода совпадают, значение аргумента автоматически заполнится значением соответствующего параметра.

---

- @PastOrPresent   — допускает null
- @Email           — допускает null и пустую строку
- @NotEmpty        — не null, не пустое, может содержать пробелы
- @NotBlank        — не null, не пустое, без пробелов
- @NotNull         — не null, может быть пустое (обязательно должна быть передана)

---

FilmController: \
    GET /films/{filmId}: getFilm(Long) \
    POST /films: createFilm(CreateFilmRequest) \
    DELETE /films/{filmId}: deleteFilm(Long) \
    PUT /films/{filmId}/like/{userId}: addLike(Long,Long) \
    DELETE /films/{filmId}/like/{userId}: deleteLike(Long,Long) \
    PUT /films: updateFilm(UpdateFilmRequest) \
    GET /films/popular: getPopularFilms(int) \
    GET /films: getAllFilms() \
GenreController: \
    GET /genres/{genreId}: get(Long) \
    GET /genres: getAllGenres() \
MpaController: \
    GET /mpa/{mpaId}: get(Long) \
    GET /mpa: getAllMpas() \
UserController: \
    PUT /users: updateUser(UpdateUserRequest) \
    PUT /users/{userId}/friends/{friendId}: addFriend(Long,Long) \
    GET /users/{userId}: getUser(Long) \
    DELETE /users/{userId}: deleteUser(Long) \
    POST /users: createUser(CreateUserRequest) \
    DELETE /users/{userId}/friends/{friendId}: deleteFriend(Long,Long) \
    GET /users: getAllUsers() \
    GET /users/{userId}/friends: getUserFriends(Long) \
    GET /users/{userId}/friends/common/{otherId}: getCommonFriends(Long,Long) \