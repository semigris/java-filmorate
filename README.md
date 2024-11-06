# java-filmorate

### Entity Relationship Diagrams (ERD)
![ERD.jpg](src%2Fmain%2Fresources%2FERD.jpg)

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

#### Таблица `film_genres`:
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
```sql
INSERT INTO films (name, description, duration, releaseDate, mpa)
VALUES ('Название фильма', 'Описание фильма', 120, '2023-10-01', 1);
```

### 2. Получение списка всех фильмов
```sql
SELECT f.*,
       m.id,
       m.name
FROM films AS f
LEFT JOIN mpa AS m ON f.id = m.id;
```

#### 3. Получение информации о фильме по его ID
```sql
SELECT f.*,
       m.name
FROM films AS f
LEFT JOIN mpa AS m ON f.id = m.id
WHERE f.id = ?;
```

#### 4. Регистрация нового пользователя
```sql
INSERT INTO users (email, login, name, birthday)
VALUES ('user@example.com', 'user123', 'Имя пользователя', '1990-01-01');
```

#### 5. Получение информации о пользователе по его ID
```sql
SELECT u.*
FROM users AS u
WHERE u.id = ?;
```

#### 6. Добавление друга
```sql
INSERT INTO friends (user_id, friend_id, status) 
VALUES (1, 2, 'ACCEPTED');
```

#### 7. Получение списка друзей пользователя
```sql
SELECT u.id, u.email, u.login, u.name, u.birthday
FROM users AS u
WHERE id IN (SELECT friend_id FROM friends WHERE user_id = ? AND status = 'ACCEPTED');
```
