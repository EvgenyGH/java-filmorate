package ru.yandex.practicum.filmorate.exception;

public class BaseFilmAndUserException extends RuntimeException{
    private String object;

    public BaseFilmAndUserException(String message, String object) {
        super(message);
        this.object = object;
    }

    public String getObject() {
        return object;
    }
}
