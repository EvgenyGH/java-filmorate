package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class FilmNotExistsException extends BaseFilmAndUserException {
    public FilmNotExistsException(String message, Map<String, String> properties) {
        super(message, properties);
    }
}
