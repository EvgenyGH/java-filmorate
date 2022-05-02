package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
    public Film addUser(@Valid @RequestBody Film film) throws FilmExistsException, ValidationException {
        film.setId(++filmId);
        film.validate();

        if (films.putIfAbsent(film.getId(), film) != null) {
            filmId--;
            log.warn(String.format("%-40s - %s", "Выброшено исключение"
                    , String.format("Фильм id=%s уже создан", film.getId())));
            throw new FilmExistsException(String.format("Фильм id=%s уже создан", film.getId()));
        }
        log.trace(String.format("%-40s - %s", "Добавлен фильм", film));

        return film;
    }

    //обновление информации о фильме
    @PutMapping
    public Film updateUser(@Valid @RequestBody Film film) throws FilmNotExistsException, ValidationException {
        film.validate();

        if (films.replace(film.getId(), film) == null) {
            log.warn(String.format("%-40s - %s", "Выброшено исключение"
                    , String.format("Фильма id=%s не существует", film.getId())));
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

}
