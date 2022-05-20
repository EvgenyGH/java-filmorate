package ru.yandex.practicum.filmorate.exception;

public class FilmExistsException extends BaseFilmAndUserException {
    public FilmExistsException(String message, String object) {
        super(message, object);
    }
}
