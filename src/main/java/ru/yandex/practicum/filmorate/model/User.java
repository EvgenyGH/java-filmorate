package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class User {
    private int id;
    //электронная почта не может быть пустой и должна содержать символ @
    @Email
    @NotBlank
    private String email;
    //логин не может быть пустым и содержать пробелы
    @Pattern(regexp = "[\\S]+")
    @NotBlank
    private String login;
    //имя для отображения может быть пустым — в таком случае будет использован логин
    private String name;
    //дата рождения не может быть в будущем
    @PastOrPresent
    private LocalDate birthday;

    //список друзей
    private Set<User> friends = new HashSet<>();

    public void validateId() throws ValidationException {
        if (id <= 0) {
            throwValidationException("ID должен быть > 0");
        }
    }

    public void validateName() {
        //имя для отображения может быть пустым — в таком случае будет использован логин
        if (name == null || name.isBlank()) {
            log.info(String.format("%-40s - %s", "Имя не может быть пустым", "Имя заменено логином"));
            name = login;
        }
    }

    private void throwValidationException(String message) throws ValidationException {
        log.warn(String.format("%-40s - ID=%5s - %s", "Выброшено исключение", id, message));
        throw new ValidationException(message);
    }
}
