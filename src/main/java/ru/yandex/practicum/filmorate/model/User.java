package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    public User(int id, String email, @NonNull String login, String name, @NonNull LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }
    @Positive
    private int id;
    //электронная почта не может быть пустой и должна содержать символ @
    @Email
    @NotNull
    private String email;
    //логин не может быть пустым и содержать пробелы
    @NonNull
    @Pattern(regexp = "[^/s]+")
    private String login;
    //имя для отображения может быть пустым — в таком случае будет использован логин
    @NotBlank
    private String name;
    //дата рождения не может быть в будущем
    @NonNull
    @PastOrPresent
    private LocalDate birthday;
}
