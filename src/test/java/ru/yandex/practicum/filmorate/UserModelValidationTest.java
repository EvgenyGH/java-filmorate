package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserModelValidationTest {
    @Test
    public void validationIdEmailLoginNameBirthdayTest() {
        User user = new User();
        user.setId(1);
        user.setEmail("j3000@bk.ru");
        user.setLogin("Login");
        user.setName("Name");
        user.setBirthday(LocalDate.of(1957, 12, 12));

        assertDoesNotThrow(user::validate);

        user.setId(0);
        assertThrows(ValidationException.class, user::validate);
        user.setId(1);

        user.setEmail("   ");
        assertThrows(ValidationException.class, user::validate);
        user.setEmail(null);
        assertThrows(ValidationException.class, user::validate);
        user.setEmail("j3000bk.ru");
        assertThrows(ValidationException.class, user::validate);
        user.setEmail("j3000@bk.ru");

        user.setLogin(null);
        assertThrows(ValidationException.class, user::validate);
        user.setLogin("   ");
        assertThrows(ValidationException.class, user::validate);
        user.setLogin("");
        assertThrows(ValidationException.class, user::validate);
        user.setLogin("Login");

        user.setName(null);
        assertDoesNotThrow(user::validate);
        user.setName("   ");
        assertDoesNotThrow(user::validate);
        user.setName("");
        assertDoesNotThrow(user::validate);
        user.setName("Name");

        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, user::validate);
    }


}
