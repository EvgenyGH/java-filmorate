package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestUserStorageAndFilmStorage {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Test
    public void testFilmStorage() {
        //Тест filmStorage.addFilm();
        filmStorage.addFilm(new Film(1L, "name1", "description1", LocalDate.now()
                , 100, 100
                , new HashSet<>(Arrays.asList(new Genre(1, "Комедия")
                , new Genre(2, "Драма"), new Genre(3, "Мультфильм")
                , new Genre(4, "Документальный")))
                , new Mpa(1, "G"), new HashSet<>()));

        filmStorage.addFilm(new Film(2L, "name2", "description2", LocalDate.now()
                , 90, 100
                , new HashSet<>(Arrays.asList(new Genre(1, "Комедия")
                , new Genre(2, "Драма")
                , new Genre(4, "Документальный")))
                , new Mpa(2, "PG"), new HashSet<>()));

        filmStorage.addFilm(new Film(3L, "name3", "description3", LocalDate.now()
                , 120, 100
                , new HashSet<>(Arrays.asList(new Genre(1, "Комедия")
                , new Genre(4, "Документальный")))
                , new Mpa(5, "NC-17"), new HashSet<>()));

        //Тест filmStorage.getFilms();
        assertEquals(3, filmStorage.getFilms().size());

        //Тест filmStorage.getFilmById();
        assertEquals("name2", filmStorage.getFilmById(2L).getName());
        assertEquals("description3", filmStorage.getFilmById(3L).getDescription());

        //Тест filmStorage.updateFilm();
        filmStorage.updateFilm(new Film(1L, "name1upd", "description1", LocalDate.now()
                , 100, 100
                , new HashSet<>(Arrays.asList(new Genre(1, "Комедия")
                , new Genre(2, "Драма"), new Genre(3, "Мультфильм")
                , new Genre(4, "Документальный")))
                , new Mpa(1, "G"), new HashSet<>()));
        assertEquals("name1upd", filmStorage.getFilmById(1L).getName());

        filmStorage.updateFilm(new Film(3L, "name3", "description3upd", LocalDate.now()
                , 100, 100
                , new HashSet<>(Arrays.asList(new Genre(1, "Комедия")
                , new Genre(2, "Драма"), new Genre(3, "Мультфильм")))
                , new Mpa(1, "G"), new HashSet<>()));
        assertEquals("description3upd", filmStorage.getFilmById(3L).getDescription());
    }

    @Test
    public void testUserStorage() {
        //Тест userStorage.addUser();
        userStorage.addUser(new User(1L, "e@mail1.com", "login1", "name1"
                , LocalDate.of(1900, 12, 12), Map.of()));

        userStorage.addUser(new User(2L, "e@mail2.com", "login2", "name2"
                , LocalDate.of(1990, 12, 12), Map.of(1L, "")));

        userStorage.addUser(new User(3L, "e@mail3.com", "login3", "name3"
                , LocalDate.of(1900, 12, 12), Map.of(1L, "", 2L, "")));

        //Тест userStorage.getUsers();
        assertEquals(3, userStorage.getUsers().size());

        //Тест userStorage.getUserById();
        assertEquals("login3", userStorage.getUserById(3L).getLogin());
        assertEquals(Map.of(1L, "неподтверждённая"), userStorage.getUserById(2L).getFriends());

        //Тест userStorage.updateUser();
        userStorage.updateUser(new User(1L, "e@mail1.com", "login1", "name1"
                , LocalDate.of(1900, 12, 12), Map.of(2L, "")));
        assertEquals(Map.of(2L, "подтверждённая"), userStorage.getUserById(1L).getFriends());

        userStorage.updateUser(new User(2L, "e@mail2.com", "login2upd", "name2"
                , LocalDate.of(1990, 12, 12), Map.of(1L, "")));
        assertEquals("login2upd", userStorage.getUserById(2L).getLogin());
    }
}
