package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    //Коллекция всех пользователей
    private final Map<Integer, User> users = new HashMap<>();

    //создание нового пользователя
    @PostMapping("/user")
    public void addUser(@Valid @RequestBody User user) throws UserExistsException {
        if (users.putIfAbsent(user.getId(), user) != null) {
            throw new UserExistsException(String.format("Пользователь id=%s уже создан", user.getId()));
        }
    }

    //обновление информации о пользователе
    @PutMapping("/user")
    public void updateUser(@Valid @RequestBody User user) throws UserNotExistsException {
        if (users.replace(user.getId(), user) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует", user.getId()));
        }
    }

    //получение списка всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }
}
