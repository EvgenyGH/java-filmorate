package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Data
@Slf4j
public class User {
    private int id;
    //электронная почта не может быть пустой и должна содержать символ @
    private String email;
    //логин не может быть пустым и содержать пробелы
    private String login;
    //имя для отображения может быть пустым — в таком случае будет использован логин
    private String name;
    //дата рождения не может быть в будущем
    private LocalDate birthday;

    public void validate() throws ValidationException {
        validateId();
        validateEmail();
        validateLogin();
        validateName();
        validateBirthday();
    }

    private void validateId() throws ValidationException {
        if (id <= 0) {
            throwValidationException("ID должен быть > 0");
        }
    }

    private void validateEmail() throws ValidationException {
        //электронная почта не может быть пустой и должна содержать символ @
        if (email == null || email.isBlank() || !email.contains("@")) {
            throwValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
    }

    private void validateLogin() throws ValidationException {
        //логин не может быть пустым и содержать пробелы
        if (login == null || login.isBlank() || login.contains(" ")) {
            throwValidationException("Логин не может быть пустым и содержать пробелы");
        }
    }

    private void validateName() {
        //имя для отображения может быть пустым — в таком случае будет использован логин
        if (name == null || name.isBlank()) {
            log.info(String.format("%-40s - %s", "Имя не может быть пустым", "Имя заменено логином"));
            name = login;
        }
    }

    private void validateBirthday() throws ValidationException {
        //дата рождения не может быть в будущем
        if (birthday == null || birthday.isAfter(LocalDate.now())) {
            throwValidationException("Дата рождения не может быть в будущем - " + birthday);
        }
    }

    private void throwValidationException(String message) throws ValidationException {
        log.warn(String.format("%-40s - ID=%5s - %s", "Выброшено исключение", id, message));
        throw new ValidationException(message);
    }
}
