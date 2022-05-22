package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class UserNotExistsException extends BaseFilmAndUserException {
    public UserNotExistsException(String message, Map<String, String> properties) {
        super(message, properties);
    }
}
