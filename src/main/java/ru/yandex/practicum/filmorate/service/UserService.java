package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    //добавление в друзья
    public User addFriend(int idMain, int idFriend) {
        User userMain = userStorage.getUserById(idMain);
        User userFriend = userStorage.getUserById(idFriend);

        validateUsers(idMain, idFriend);

        userMain.getFriends().add(userFriend);
        userFriend.getFriends().add(userMain);

        log.trace(String.format("%-40s - %s", "Пользователи стали друзьями", "User1 = "
                + userMain + " User2 = " + userFriend));

        return userMain;
    }

    //удаление из друзей
    public User removeFriend(int idMain, int idFriend) {
        User userMain = userStorage.getUserById(idMain);
        User userFriend = userStorage.getUserById(idFriend);

        validateUsers(idMain, idFriend);

        userMain.getFriends().remove(userFriend);
        userFriend.getFriends().remove(userMain);

        log.trace(String.format("%-40s - %s", "Пользователи удалились из друзей", "User1 = "
                + userMain + " User2 = " + userFriend));

        return userMain;
    }

    //вывод списка общих друзей
    public Set<User> getMutualFriends(int id1, int id2) {
        Set<User> user1Friends = userStorage.getUserById(id1).getFriends();
        Set<User> user2Friends = userStorage.getUserById(id2).getFriends();

        log.trace(String.format("%-40s - %s", "Отправленны общие друзья пользователей", "User1 id="
                + id1 + " User2 id=" + id2));

        return user1Friends.stream().filter(user2Friends::contains).collect(Collectors.toSet());
    }

    //проверка на существование друзей
    private void validateUsers(int id1, int id2) {
        if (userStorage.getUserById(id1) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", id1)
                    , String.format("Пользователь id=%s.", id1));
        } else if (userStorage.getUserById(id2) == null) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", id2)
                    , String.format("Пользователь id=%s.", id2));
        }
    }
}
