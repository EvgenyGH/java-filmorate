package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class BaseFilmAndUserException extends RuntimeException {
    private final Map<String, String> properties;

    public BaseFilmAndUserException(String message, Map<String, String> properties) {
        super(message);
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
