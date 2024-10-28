package ru.yandex.practicum.filmorate.model;

public enum FriendStatus {
    SENT("Заявка отправлена"),
    ACCEPTED("Заявка принята");

    private final String description;

    FriendStatus(String description) {
        this.description = description;
    }
}
