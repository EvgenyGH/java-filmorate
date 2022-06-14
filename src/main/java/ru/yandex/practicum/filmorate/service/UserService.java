package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    //добавление в друзья
    public User addFriend(long idMain, long idFriend) {
        validateUsers(idMain, idFriend);

        User userMain = userStorage.getUserById(idMain);

        userMain.getFriends().put(idFriend, "неподтверждённая");
        userStorage.updateUser(userMain);

        log.trace("Пользователь 1 добавил в друзья пользователя 2 -> User1 id={} и User2 id={}", idMain, idFriend);

        return userMain;
    }

    //удаление из друзей
    public User removeFriend(long idMain, long idFriend) {
        validateUsers(idMain, idFriend);

        User userMain = userStorage.getUserById(idMain);
        userMain.getFriends().remove(idFriend);
        userStorage.updateUser(userMain);

        log.trace("Пользователь 1 удалил пользователя 2 из друзей -> User1 id={} User2 id={}", idMain, idFriend);

        return userMain;
    }

    //вывод списка общих друзей
    public Set<User> getMutualFriends(long id1, long id2) {
        validateUsers(id1, id2);

        Map<Long, String> user1Friends = userStorage.getUserById(id1).getFriends();
        Map<Long, String> user2Friends = userStorage.getUserById(id2).getFriends();

        log.trace("Отправлены общие друзья пользователей -> User1 id={} User2 id={}", id1, id2);

        return user1Friends.keySet().stream().filter(user2Friends.keySet()::contains)
                .map(userStorage::getUserById).collect(Collectors.toSet());
    }

    //возвращает список пользователей, являющихся друзьями
    public Set<User> getFriends(long id) {
        log.trace("Отправлен список друзей пользователя -> User id={}", id);

        return userStorage.getUserById(id).getFriends().keySet().stream()
                .map(userStorage::getUserById).collect(Collectors.toSet());
    }

    //проверка на существование друзей
    private void validateUsers(long id1, long id2) {
        if (id1 == id2) {
            throw new ValidationException(String.format("Id пользователей одинаковы id=%s.", id1));
        } else if (userStorage.getUserById(id1) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", id1)
                    , Map.of("object", "user", "id", String.valueOf(id1)));
        } else if (userStorage.getUserById(id2) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", id2)
                    , Map.of("object", "user", "id", String.valueOf(id2)));
        }
    }
}

