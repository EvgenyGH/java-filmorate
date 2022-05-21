package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class FilmExistsException extends BaseFilmAndUserException {
    public FilmExistsException(String message, Map<String, String> properties) {
        super(message, properties);
    }
}
