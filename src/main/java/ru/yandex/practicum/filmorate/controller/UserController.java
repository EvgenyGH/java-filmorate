package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    //Коллекция всех пользователей
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    //создание нового пользователя
    @PostMapping
    public User addUser(@Valid @RequestBody User user) throws UserExistsException, ValidationException {
        user.setId(++userId);
        user.validate();

        if (users.putIfAbsent(user.getId(), user) != null) {
            --userId;
            log.warn(String.format("%-40s - %s", "Выброшено исключение"
                    , String.format("Пользователь id=%s уже создан", user.getId())));
            throw new UserExistsException(String.format("Пользователь id=%s уже создан", user.getId()));
        }
        log.trace(String.format("%-40s - %s", "Добавлен пользователь", user));

        return user;
    }

    //обновление информации о пользователе
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws UserNotExistsException, ValidationException {
        user.validate();

        if (users.replace(user.getId(), user) == null) {
            log.warn(String.format("%-40s - %s", "Выброшено исключение"
                    , String.format("Пользователя id=%s не существует", user.getId())));
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует", user.getId()));
        }
        log.trace(String.format("%-40s - %s", "Информация о пользователе обновлена", user));

        return user;
    }

    //получение списка всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }
}
