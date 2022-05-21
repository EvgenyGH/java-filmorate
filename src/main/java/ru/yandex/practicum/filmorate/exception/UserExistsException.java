package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class UserExistsException extends BaseFilmAndUserException {
    public UserExistsException(String message, Map<String, String> properties) {
        super(message, properties);
    }
}
