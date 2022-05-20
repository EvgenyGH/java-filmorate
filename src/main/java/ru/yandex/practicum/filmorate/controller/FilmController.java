package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
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
    public Film getFilmById(@PathVariable int id) {
        return filmStorage.getFilmById(id);
    }

    //пользователь ставит лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") int filmId, @PathVariable int userId) {
        return filmService.addLike(filmId, userId);
    }

    //пользователь удаляет лайк
    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable("id") int filmId, @PathVariable int userId) {
        return filmService.removeLike(filmId, userId);
    }

    //возвращает список из первых count фильмов по количеству лайков.
    //если значение параметра count не задано, возвращает первые 10.
    @GetMapping("/popular?count={count}")
    public Set<Film> removeLike(@PathVariable(required = false) Optional<Integer> count) {
        return filmService.getTopFilms(count.orElse(0));
    }
}
// TODO: 20.05.2022 might be move id from int to long
