CREATE TABLE IF NOT EXISTS mpa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS films (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    description varchar(100),
    duration integer NOT NULL,
    releaseDate date NOT NULL,
    mpa integer NOT NULL,
    FOREIGN KEY (mpa) REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS genres (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id integer,
    genre_id integer,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email varchar(100) NOT NULL UNIQUE,
    login varchar(100) NOT NULL UNIQUE,
    name varchar(100) NOT NULL,
    birthday date NOT NULL
);

CREATE TABLE IF NOT EXISTS film_likes (
    film_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friends (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    friend_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    status varchar(100) NOT NULL,
    PRIMARY KEY (user_id, friend_id)
);


