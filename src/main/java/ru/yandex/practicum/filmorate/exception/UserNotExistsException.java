package ru.yandex.practicum.filmorate.exception;

public class UserNotExistsException extends Exception {
    public UserNotExistsException(String message) {
        super(message);
    }
}
