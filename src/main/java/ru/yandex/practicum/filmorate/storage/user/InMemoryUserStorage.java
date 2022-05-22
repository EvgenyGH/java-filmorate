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
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 0;

    //создание нового пользователя
    @Override
    public User addUser(User user) {
        user.setId(++userId);
        user.validateName();

        if (users.putIfAbsent(user.getId(), user) != null) {
            --userId;
            throw new UserExistsException(String.format("Пользователь id=%s уже создан", user.getId())
                    , Map.of("object", "user", "id", String.valueOf(user.getId())));
        }
        log.trace("Добавлен пользователь -> User: {}", user);

        return user;
    }

    //обновление информации о пользователе
    @Override
    public User updateUser(User user) {
        user.validateName();

        if (users.replace(user.getId(), user) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует", user.getId())
                    , Map.of("object", "user", "id", String.valueOf(user.getId())));
        }
        log.trace("Информация о пользователе обновлена -> User: {}", user);

        return user;
    }

    //получение списка всех пользователей
    @Override
    public Collection<User> getUsers() {
        log.trace("Информация о всех пользователях отправлена");
        return users.values();
    }

    //поучение имени пользователя по id
    @Override
    public User getUserById(long id) {
        User user = users.get(id);

        if (user == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует", id)
                    , Map.of("object", "user", "id", String.valueOf(id)));
        }

        log.trace("Информация о пользователе отправлена -> User id={}", id);

        return user;
    }
}
