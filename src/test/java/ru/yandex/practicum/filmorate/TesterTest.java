package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest
public class TesterTest {
    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;

    @Test
    public void testFilmStorage() {
        //Film film = filmStorage.getFilmById(2);
        //System.out.println(film);
        //filmStorage.getFilms().forEach(System.out::println);

        /*Film film = new Film();

        film.setId(1);
        film.setDuration(120);
        film.setMpa("NC-17");
        film.setName("m");
        film.setReleaseDate(LocalDate.now());
        film.setDescription("d");
        film.setGenresList(new HashSet<>(Arrays.asList("Комедия", "Драма"
                , "Мультфильм", "Триллер", "Документальный", "Боевик")));
        film.setFilmLikes(new HashSet<>(Arrays.asList(1L,2L,3L)));

        System.out.println(filmStorage.addFilm(film));*/

        /*Film film = new Film();

        film.setId(18);
        film.setDuration(222);
        film.setMpa("G");
        film.setName("AAA");
        film.setReleaseDate(LocalDate.now());
        film.setDescription("DDD");
        film.setGenresList(new HashSet<>(Arrays.asList("Комедия", "Драма"
                , "Мультфильм")));
        film.setFilmLikes(new HashSet<>(Arrays.asList(1L, 3L)));

        System.out.println(filmStorage.updateFilm(film));*/
    }

    @Test
    public void testUserStorage() {
        //System.out.println(userStorage.getUserById(1L));
        //userStorage.getUsers().forEach(System.out::println);

        /*User user = new User();
        user.setBirthday(LocalDate.of(2020, 12, 12));
        user.setName("John");
        user.setLogin("JO");
        user.setEmail("F@f.dsd");
        user.setFriends(Map.of(1L, "неподтверждённая", 2L, "неподтверждённая"
                , 3L, "неподтверждённая"));

        userStorage.addUser(user);*/

        User user = new User();
        user.setBirthday(LocalDate.of(2020, 12, 12));
        user.setName("JohnAAA");
        user.setLogin("JOAAA");
        user.setEmail("F@f.dsdAAA");
        user.setFriends(Map.of(1L, "неподтверждённая"));
        user.setId(8L);

        userStorage.updateUser(user);

    }
}
