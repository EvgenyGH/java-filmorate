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
        User userFriend = userStorage.getUserById(idFriend);

        userMain.getFriends().add(idFriend);
        userFriend.getFriends().add(idMain);

        log.trace(String.format("%-40s - %s", "Пользователи стали друзьями", "User1 id=' "
                + userMain + " User2 = " + userFriend));

        return userMain;
    }

    //удаление из друзей
    public User removeFriend(long idMain, long idFriend) {
        validateUsers(idMain, idFriend);

        User userMain = userStorage.getUserById(idMain);
        User userFriend = userStorage.getUserById(idFriend);

        userMain.getFriends().remove(idFriend);
        userFriend.getFriends().remove(idMain);

        log.trace(String.format("%-40s - %s", "Пользователи удалились из друзей", "User1 = "
                + userMain + " User2 = " + userFriend));

        return userMain;
    }

    //вывод списка общих друзей
    public Set<User> getMutualFriends(long id1, long id2) {
        validateUsers(id1, id2);

        Set<Long> user1Friends = userStorage.getUserById(id1).getFriends();
        Set<Long> user2Friends = userStorage.getUserById(id2).getFriends();

        log.trace(String.format("%-40s - %s", "Отправлены общие друзья пользователей", "User1 id="
                + id1 + " User2 id=" + id2));

        return user1Friends.stream().filter(user2Friends::contains)
                .map(userStorage::getUserById).collect(Collectors.toSet());
    }

    //возвращает список пользователей, являющихся друзьями
    public Set<User> getFriends(long id) {
        return userStorage.getUserById(id).getFriends().stream()
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

