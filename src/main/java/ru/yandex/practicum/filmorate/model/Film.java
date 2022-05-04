package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Slf4j
public class Film {
    private int id;
    //название не может быть пустым
    @NotBlank
    private String name;
    //максимальная длина описания — 200 символов
    @Size(max = 200)
    @NotBlank
    private String description;
    //дата релиза — не раньше 28 декабря 1895 года
    @ReleaseDate
    private LocalDate releaseDate;
    //продолжительность фильма должна быть положительной
    @Positive
    private int duration;

    public void validateId() throws ValidationException {
        if (id <= 0) {
            throwValidationException("ID должен быть > 0");
        }
    }

    private void throwValidationException(String message) throws ValidationException {
        log.warn(String.format("%-40s - ID=%5s - %s", "Выброшено исключение", id, message));
        throw new ValidationException(message);
    }
}
