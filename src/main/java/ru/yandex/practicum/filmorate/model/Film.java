package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

//todo
/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/
@Data
public class Film {
    public Film(int id, String name, String description, LocalDate releaseDate, Duration duration) {
        validateReleaseDate(releaseDate);
        validateDuration(duration);

        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

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
    @NotNull
    private Duration duration;

    private void validateReleaseDate(LocalDate localDate) {
        if (localDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }

    private void validateDuration(Duration duration) throws ValidationException {
        if (duration.isNegative()) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
