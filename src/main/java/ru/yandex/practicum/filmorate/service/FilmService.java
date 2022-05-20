package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    //добавить лайк
    public Film addLike(long filmId, long userId) {
        validateFilmAndUser(filmId, userId);

        filmStorage.getFilmById(filmId).getFilmLikes().add(userId);

        log.trace(String.format("%-40s - %s", "Добавлен лайк к фильму", "Film id="
                + filmId + " User id=" + userId));

        return filmStorage.getFilmById(filmId);
    }

    //удалить лайк
    public Film removeLike(long filmId, long userId) {
        validateFilmAndUser(filmId, userId);

        filmStorage.getFilmById(filmId).getFilmLikes().remove(userId);

        log.trace(String.format("%-40s - %s", "Удален лайк к фильму", "Film id="
                + filmId + " User id=" + userId));

        return filmStorage.getFilmById(filmId);
    }

    //получить топ популярных фильмов
    public List<Film> getTopFilms(long count) {
        log.trace(String.format("%-40s - %s", "Отправлен топ популярных фильмов"
                , "Количество фильмов - " + count));
        return filmStorage.getFilms().stream().sorted((film1, film2) -> {
                    long film1Likes = film1.getFilmLikes().size();
                    long film2Likes = film2.getFilmLikes().size();

                    if (film1Likes < film2Likes) {
                        return 1;
                    } else if (film1Likes > film2Likes) {
                        return -1;
                    }
                    return 0;
                })
                .limit(count).collect(Collectors.toList());
    }

    //проверка существования фильма и юзера
    private void validateFilmAndUser(long filmId, long userId) {
        if (filmStorage.getFilmById(filmId) == null) {
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует", filmId)
                    , String.format("Фильм id=%s", filmId));
        } else if (userStorage.getUserById(userId) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", userId)
                    , String.format("Пользователь id=%s.", userId));
        }
    }
}
