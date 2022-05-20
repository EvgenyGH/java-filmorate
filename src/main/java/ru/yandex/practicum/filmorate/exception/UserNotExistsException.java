package ru.yandex.practicum.filmorate.exception;

public class UserNotExistsException extends BaseFilmAndUserException {
    public UserNotExistsException(String message, String object) {
        super(message, object);
    }
}
