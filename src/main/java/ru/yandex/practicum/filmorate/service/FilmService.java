package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    //добавить лайк
    public void addLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);

        filmStorage.getFilmById(filmId).getFilmLikes().add(userStorage.getUserById(userId));
    }

    //удалить лайк
    public void removeLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);

        filmStorage.getFilmById(filmId).getFilmLikes().remove(userStorage.getUserById(userId));
    }

    //получить топ популярных фильмов
    public Set<Film> getTopFilms(int count) {
        if (count < 0) {
            throw new ValidationException("Количество фильмов в списке должно быть > 0");
        } else if (count == 0){
            count = 10;
        }
        //todo count по умолчанию 10 и проверку id
       return filmStorage.getFilms().stream().sorted((film1, film2) -> {
           int film1Likes = film1.getFilmLikes().size();
           int film2Likes = film2.getFilmLikes().size();

           if (film1Likes < film2Likes){
               return -1;
           } else if (film1Likes > film2Likes){
               return 1;
           }
           return 0;
       })
               .limit(count).collect(Collectors.toSet());
    }

    //проверка существования фильма и юзера
    private void validateFilmAndUser(int filmId, int userId){
        if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("ID должен быть > 0");
        }

        if (filmStorage.getFilmById(filmId) == null){
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует", filmId)
                    , String.format("Фильм id=%s", filmId));
        }else if (userStorage.getUserById(userId) == null){
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", userId)
                    , String.format("Пользователь id=%s.", userId));
        }
    }
}
