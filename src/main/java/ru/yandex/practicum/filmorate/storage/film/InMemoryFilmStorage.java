package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private int filmId = 0;

    //добавление нового фильма
    @Override
    public Film addFilm(Film film) {
        film.setId(++filmId);

        if (films.putIfAbsent(film.getId(), film) != null) {
            filmId--;
            throw new FilmExistsException(String.format("Фильм id=%s уже создан", film.getId())
                    , Map.of("object", "film", "id", String.valueOf(film.getId())));
        }
        log.trace("Добавлен фильм -> Film: {}", film);

        return film;
    }

    //обновление информации о фильме
    @Override
    public Film updateFilm(Film film) {

        if (films.replace(film.getId(), film) == null) {
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует", film.getId())
                    , Map.of("object", "film", "id", String.valueOf(film.getId())));
        }
        log.trace("Информация о фильме обновлена -> Film: {}", film);

        return film;
    }

    //получение списка всех фильмов
    @Override
    public Collection<Film> getFilms() {
        log.trace("Информация о всех фильмах отправлена");
        return films.values();
    }

    //поучение имени пользователя по id
    @Override
    public Film getFilmById(long id) {
        Film film = films.get(id);

        if (film == null) {
            throw new UserNotExistsException(String.format("Фильма id=%s не существует", id)
                    , Map.of("object", "film", "id", String.valueOf(id)));
        }

        log.trace("Информация о фильме отправлена -> Film id={}", id);

        return film;
    }
}
