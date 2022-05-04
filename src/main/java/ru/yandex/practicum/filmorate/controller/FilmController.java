package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    //Коллекция всех фильмов
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    //добавление нового фильма
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws FilmExistsException {
        film.setId(++filmId);

        if (films.putIfAbsent(film.getId(), film) != null) {
            filmId--;
            throw new FilmExistsException(String.format("Фильм id=%s уже создан", film.getId()));
        }
        log.trace(String.format("%-40s - %s", "Добавлен фильм", film));

        return film;
    }

    //обновление информации о фильме
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmNotExistsException, ValidationException {
        film.validateId();

        if (films.replace(film.getId(), film) == null) {
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует", film.getId()));
        }
        log.trace(String.format("%-40s - %s", "Информация о фильме обновлена", film));

        return film;
    }

    //получение списка всех фильмов
    @GetMapping
    public Collection<Film> getUsers() {
        return films.values();
    }

    @ExceptionHandler(value = {ValidationException.class,
            UserExistsException.class, UserNotExistsException.class
            , FilmExistsException.class, FilmNotExistsException.class
            , MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlerValidationExceptions(Exception exception) {
        log.warn(String.format("%-40s - %s", "Выброшено исключение", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
