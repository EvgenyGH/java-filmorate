package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql({"/schema.sql", "/fillDbForTest.sql"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TestFilmAndUserAndGenreAndMpaControllers {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1PostPutGetUserTests() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("j3000@bk.ru");
        user.setLogin("Login");
        user.setName("Name");
        user.setBirthday(LocalDate.of(1957, 12, 12));

        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        user.setId(0);
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setId(1);

        user.setEmail("   ");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setEmail(null);
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setEmail("j3000bk.ru");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setEmail("j3000@bk.ru");

        user.setLogin(null);
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setLogin("   ");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setLogin("");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setLogin("log in");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        user.setName(null);
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        user.setLogin("Login");
        user.setName(null);
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        user.setName("   ");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        user.setName("");
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        user.setName("Name");

        user.setBirthday(LocalDate.now().plusDays(1));
        mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(get("/users").content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test2PostPutGetFilmTests() throws Exception {
        Film film = new Film();
        film.setDuration(120);
        film.setName("Film Name");
        film.setId(1);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("Film Description");
        film.setMpa("G");
        film.setGenresList(new HashSet<>(Arrays.asList("Комедия", "Документальный")));

        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        film.setDuration(0);
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setDuration(-1);
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setDuration(120);

        film.setName(null);
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setName("");
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setName("      ");
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setName("Film Name");

        film.setId(0);
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setId(1);

        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setReleaseDate(LocalDate.now());

        film.setDescription("w".repeat(201));
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setDescription("Film Description");

        mockMvc.perform(get("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        film.setMpa("N");
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setMpa("G");

        film.setGenresList(new HashSet<>(Arrays.asList("Комедия", "Пародия")));
        mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put("/films").content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        film.setGenresList(new HashSet<>(Arrays.asList("Комедия", "Документальный")));
    }

    @Test
    public void test3AddRemoveGetFriendsAndLikesGetFilmsAndUsersByIdGetRates() throws Exception {
        Film[] films = new Film[5];
        User[] users = new User[5];
        User user;
        Film film;

        for (int i = 0; i < 5; i++) {
            user = new User();
            user.setId(i + 1);
            user.setEmail("j3000@bk.ru");
            user.setLogin("Login");
            user.setName("Name");
            user.setBirthday(LocalDate.of(1957, 12, 12));

            film = new Film();
            film.setDuration(120);
            film.setName("Film Name");
            film.setId(i + 1);
            film.setReleaseDate(LocalDate.now());
            film.setDescription("Film Description");
            film.setDuration(120);
            film.setMpa("G");
            film.setGenresList(new HashSet<>(Arrays.asList("Комедия", "Документальный")));

            users[i] = user;
            films[i] = film;

            mockMvc.perform(post("/films").content(mapper.writeValueAsString(film))
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
            mockMvc.perform(post("/users").content(mapper.writeValueAsString(user))
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        }

        //тест endpoint /films/{id} GET
        mockMvc.perform(get("/films/2").contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(films[1])));
        mockMvc.perform(get("/films/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/films/7").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //тест endpoint /users/{id} GET
        mockMvc.perform(get("/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(users[1])));
        mockMvc.perform(get("/users/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/users/7").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //тест endpoint /users/{id}/friends/{friendId} PUT
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends").isMap());
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 6, 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/users/{id}/friends/{friendId}", -1, -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //тест endpoint /users/{id}/friends/{friendId} DELETE
        mockMvc.perform(delete("/users/{id}/friends/{friendId}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends.size()").value(0));
        mockMvc.perform(delete("/users/{id}/friends/{friendId}", 6, 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/users/{id}/friends/{friendId}", -1, -2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //тест endpoint /users/{id}/friends GET
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends").isMap());
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends").isMap());


        mockMvc.perform(get("/users/{id}/friends", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.size()").value(2));
        mockMvc.perform(get("/users/{id}/friends", 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/users/{id}/friends", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        //тест endpoint /users/{id}/friends/common/{otherId} GET
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends").isMap());
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends").isMap());
        mockMvc.perform(put("/users/{id}/friends/{friendId}", 4, 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.friends").isMap());


        mockMvc.perform(get("/users/{id}/friends/common/{otherId}", 1, 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$..id").value(3));
        mockMvc.perform(get("/users/{id}/friends/common/{otherId}", 6, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/users/{id}/friends/common/{otherId}", 1, -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //тест endpoint /films/{id}/like/{userId} PUT
        mockMvc.perform(put("/films/{id}/like/{userId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.filmLikes.size()").value(1));
        mockMvc.perform(put("/films/{id}/like/{userId}", 6, 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/films/{id}/like/{userId}", 1, -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //тест endpoint /films/{id}/like/{userId} DELETE
        mockMvc.perform(delete("/films/{id}/like/{userId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.filmLikes.size()").value(0));
        mockMvc.perform(delete("/films/{id}/like/{userId}", 6, 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/films/{id}/like/{userId}", 1, -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        //тест endpoint /films/popular GET
        mockMvc.perform(put("/films/{id}/like/{userId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films/{id}/like/{userId}", 2, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films/{id}/like/{userId}", 2, 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films/{id}/like/{userId}", 2, 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films/{id}/like/{userId}", 3, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(put("/films/{id}/like/{userId}", 3, 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/films/popular?count={count}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.size()").value(2));

        films[1].setFilmLikes(Set.of(1L, 2L, 3L));
        films[2].setFilmLikes(Set.of(1L, 2L));
        films[0].setFilmLikes(Set.of(1L));

        mockMvc.perform(get("/films/popular", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.size()").value(5),
                        content().json(mapper.writeValueAsString(
                                List.of(films[1], films[2], films[0], films[3], films[4]))));
        mockMvc.perform(get("/films/popular?count={count}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllMpaAndMpaByIdTest() throws Exception {
        mockMvc.perform(get("/mpa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.size()").value(5),
                        content().json(mapper.writeValueAsString(
                                List.of(new Mpa(1, "G"), new Mpa(2, "PG")
                                        , new Mpa(3, "PG-13"), new Mpa(4, "R")
                                        , new Mpa(5, "NC-17")))));

        mockMvc.perform(get("/mpa/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/mpa/{id}", "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/mpa/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.name").value("G"));

    }

    @Test
    public void getAllGenresAndGenreByIdTest() throws Exception {
        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.size()").value(6),
                        content().json(mapper.writeValueAsString(
                                List.of(new Genre(1, "Комедия"), new Genre(2, "Драма")
                                        , new Genre(3, "Мультфильм"), new Genre(4, "Триллер")
                                        , new Genre(5, "Документальный"), new Genre(6, "Боевик")))));

        mockMvc.perform(get("/genres/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/genres/{id}", "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/genres/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.name").value("Мультфильм"));

    }
}