package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class MpaNotExistException extends BaseFilmAndUserException {
    public MpaNotExistException(String message, Map<String, String> properties) {
        super(message, properties);
    }
}