package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    //добавление нового фильма
    @Override
    public Film addFilm(Film film) {
        film.setId(++filmId);

        if (films.putIfAbsent(film.getId(), film) != null) {
            filmId--;
            throw new FilmExistsException(String.format("Фильм id=%s уже создан", film.getId())
                    , String.format("Фильм id=%s", film.getId()));
        }
        log.trace(String.format("%-40s - %s", "Добавлен фильм", film));

        return film;
    }

    //обновление информации о фильме
    @Override
    public Film updateFilm(Film film) {
        film.validateId();

        if (films.replace(film.getId(), film) == null) {
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует", film.getId())
                    , String.format("Фильм id=%s", film.getId()));
        }
        log.trace(String.format("%-40s - %s", "Информация о фильме обновлена", film));

        return film;
    }

    //получение списка всех фильмов
    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    //поучение имени пользователя по id
    @Override
    public Film getFilmById(int id) {
        if (id <= 0) {
            throw new ValidationException("ID должен быть > 0");
        }

        Film film = films.get(id);

        if (film == null) {
            throw new UserNotExistsException(String.format("Фильма id=%s не существует", id)
                    , String.format("Фильм id=%s", id));
        }

        log.trace(String.format("%-40s - %s", "Информация о фильме отправлена", "id=" + id));

        return film;
    }
}
