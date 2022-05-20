package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    //создание нового пользователя
    @Override
    public User addUser(User user) {
        user.setId(++userId);
        user.validateName();

        if (users.putIfAbsent(user.getId(), user) != null) {
            --userId;
            throw new UserExistsException(String.format("Пользователь id=%s уже создан", user.getId())
                    , String.format("Пользователь id=%s", user.getId()));
        }
        log.trace(String.format("%-40s - %s", "Добавлен пользователь", user));

        return user;
    }

    //обновление информации о пользователе
    @Override
    public User updateUser(User user) {
        user.validateName();
        user.validateId();

        if (users.replace(user.getId(), user) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует", user.getId())
                    , String.format("Пользователь id=%s", user.getId()));
        }
        log.trace(String.format("%-40s - %s", "Информация о пользователе обновлена", user));

        return user;
    }

    //получение списка всех пользователей
    @Override
    public Collection<User> getUsers() {
        log.trace(String.format("%-40s - %s", "Информация о всех пользователях отправлена", ""));
        return users.values();
    }

    //поучение имени пользователя по id
    @Override
    public User getUserById(int id) {
        User user = users.get(id);

        if (user == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует", id)
                    , String.format("Пользователь id=%s", id));
        }

        log.trace(String.format("%-40s - %s", "Информация о пользователе отправлена", "id=" + id));

        return user;
    }
}
