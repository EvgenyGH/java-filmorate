package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmModelValidationTest {
    @Test
    public void validationDurationNameIdReleaseDateDescriptionTest() {
        Film film = new Film();
        film.setDuration(120);
        film.setName("Film Name");
        film.setId(1);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("Film Description");

        assertDoesNotThrow(film::validate);

        film.setDuration(0);
        assertThrows(ValidationException.class, film::validate);
        film.setDuration(-1);
        assertThrows(ValidationException.class, film::validate);
        film.setDuration(120);

        film.setName(null);
        assertThrows(ValidationException.class, film::validate);
        film.setName("");
        assertThrows(ValidationException.class, film::validate);
        film.setName("      ");
        assertThrows(ValidationException.class, film::validate);
        film.setName("Film Name");

        film.setId(0);
        assertThrows(ValidationException.class, film::validate);
        film.setId(1);

        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, film::validate);
        film.setReleaseDate(LocalDate.now());

        film.setDescription("w".repeat(201));
        assertThrows(ValidationException.class, film::validate);
        film.setDescription("Film Description");
    }
}
