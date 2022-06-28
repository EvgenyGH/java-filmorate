package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class SqlExceptionFilmorate extends BaseFilmAndUserException {
    public SqlExceptionFilmorate(String message, Map<String, String> properties) {
        super(message, properties);
    }
}