package ru.yandex.practicum.filmorate.exception;

public class UserExistsException extends BaseFilmAndUserException {
    public UserExistsException(String message, String object) {
        super(message, object);
    }
}
