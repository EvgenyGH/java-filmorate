package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Slf4j
public class Film {
    @Positive
    private int id;
    //название не может быть пустым
    @NotBlank
    private String name;
    //максимальная длина описания — 200 символов
    @NotNull
    @Size(max = 200)
    private String description;
    //дата релиза — не раньше 28 декабря 1895 года
    @NotNull
    private LocalDate releaseDate;
    //продолжительность фильма должна быть положительной
    @Positive
    private int duration;

    public void setReleaseDate(LocalDate releaseDate) {
        validateReleaseDate(releaseDate);
        this.releaseDate = releaseDate;
    }

    private void validateReleaseDate(LocalDate localDate) {
        if (localDate.isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn(String.format("%-40s - %s", "Выброшено исключение"
                    , "Дата релиза должна быть не раньше 28 декабря 1895 года"));
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }
}
