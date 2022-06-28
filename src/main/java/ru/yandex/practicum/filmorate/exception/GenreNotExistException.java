package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class GenreNotExistException extends BaseFilmAndUserException {
    public GenreNotExistException(String message, Map<String, String> properties) {
        super(message, properties);
    }
}

