package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Data
@Slf4j
public class Film {
    private int id;
    //название не может быть пустым
    private String name;
    //максимальная длина описания — 200 символов
    private String description;
    //дата релиза — не раньше 28 декабря 1895 года
    private LocalDate releaseDate;
    //продолжительность фильма должна быть положительной
    private int duration;

    public void validate() throws ValidationException {
        validateId();
        validateName();
        validateDescription();
        validateReleaseDate();
        validateDuration();
    }

    private void validateId() throws ValidationException {
        if (id <= 0) {
            throwValidationException("ID должен быть > 0");
        }
    }

    private void validateName() throws ValidationException {
        //название не может быть пустым
        if (name == null || name.isBlank()) {
            throwValidationException("Название не может быть пустым");
        }
    }

    private void validateDescription() throws ValidationException {
        //максимальная длина описания — 200 символов
        if (description == null || description.isBlank() || description.length() > 200) {
            throwValidationException("Максимальная длина описания — 200 символов");
        }
    }

    private void validateReleaseDate() throws ValidationException {
        //дата релиза — не раньше 28 декабря 1895 года
        if (releaseDate == null || releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throwValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года - " + releaseDate);
        }
    }

    private void validateDuration() throws ValidationException {
        //продолжительность фильма должна быть положительной
        if (duration <= 0) {
            throwValidationException("Продолжительность фильма должна быть положительной - " + duration);
        }
    }

    private void throwValidationException(String message) throws ValidationException {
        log.warn(String.format("%-40s - ID=%5s - %s", "Выброшено исключение", id, message));
        throw new ValidationException(message);
    }
}
