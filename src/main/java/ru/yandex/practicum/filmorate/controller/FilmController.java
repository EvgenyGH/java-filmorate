package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    //Коллекция всех фильмов
    private final Map<Integer, Film> films = new HashMap<>();

    //добавление нового фильма
    @PostMapping("/film")
    public void addUser(@Valid @RequestBody Film film) throws FilmExistsException {
        if (films.putIfAbsent(film.getId(), film) != null) {
            throw new FilmExistsException(String.format("Фильм id=%s уже создан", film.getId()));
        }
    }

    //обновление информации о фильме
    @PutMapping("/film")
    public void updateUser(@Valid @RequestBody Film film) throws FilmNotExistsException {
        if (films.replace(film.getId(), film) == null) {
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует", film.getId()));
        }
    }

    //получение списка всех фильмов
    @GetMapping
    public Collection<Film> getUsers() {
        return films.values();
    }

}
