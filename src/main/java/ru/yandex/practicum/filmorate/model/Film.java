package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    //По условиям ТЗ id может быть отрицательным
    private long id;
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
    //Лайки пользователей
    private long rate;
    //Жанр. У фильма может быть сразу несколько жанров
    private Set<Genre> genres;
    // рейтинг Ассоциации кинокомпаний (англ. Motion Picture Association, сокращённо МРА)
    private Mpa mpa;
}