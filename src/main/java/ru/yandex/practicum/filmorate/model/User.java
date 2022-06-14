package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //По условиям ТЗ id может быть отрицательным
    private long id;
    //электронная почта не может быть пустой и должна содержать символ @
    @Email
    @NotBlank
    private String email;
    //логин не может быть пустым и содержать пробелы
    @Pattern(regexp = "\\S+")
    @NotBlank
    private String login;
    //имя для отображения может быть пустым — в таком случае будет использован логин
    private String name;
    //дата рождения не может быть в будущем
    @PastOrPresent
    private LocalDate birthday;
    //список друзей (id) и статус подтверждения дружбы
    private Map<Long, String> friends = new HashMap<>();

    public void validateName() {
        //имя для отображения может быть пустым — в таком случае будет использован логин
        if (name == null || name.isBlank()) {
            log.info("Имя не может быть пустым -> Имя заменено логином");
            name = login;
        }
    }
}