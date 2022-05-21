package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    //Хранилище фильмов
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    //добавление нового фильма
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmStorage.addFilm(film);
    }

    //обновление информации о фильме
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmStorage.updateFilm(film);
    }

    //получение списка всех фильмов
    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    //получить фильм по id
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable @Min(1) long id) {
        return filmStorage.getFilmById(id);
    }

    //пользователь ставит лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") @Min(1) long filmId, @PathVariable @Min(1) long userId) {
        return filmService.addLike(filmId, userId);
    }

    //пользователь удаляет лайк
    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable("id") @Min(1) long filmId, @PathVariable @Min(1) long userId) {
        return filmService.removeLike(filmId, userId);
    }

    //возвращает список из первых count фильмов по количеству лайков.
    //если значение параметра count не задано, возвращает первые 10.
    @GetMapping("/popular")
    public List<Film> removeLike(@RequestParam(defaultValue = "10") @Min(1) long count) {
        return filmService.getTopFilms(count);
    }
}