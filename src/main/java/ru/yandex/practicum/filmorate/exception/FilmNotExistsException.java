package ru.yandex.practicum.filmorate.exception;

public class FilmNotExistsException extends BaseFilmAndUserException {
    public FilmNotExistsException(String message, String object) {
        super(message, object);
    }
}
