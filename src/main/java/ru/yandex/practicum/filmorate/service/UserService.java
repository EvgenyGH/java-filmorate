package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
//todo check users to add
    //добавление в друзья
    public User addFriend(int idMain, int idFriend){
        User userMain = userStorage.getUserById(idMain);
        User userFriend = userStorage.getUserById(idFriend);

        userMain.getFriends().add(userFriend);
        userFriend.getFriends().add(userMain);

        return userFriend;
    }

    //удаление из друзей
    public User removeFriend(int idMain, int idFriend){
        User userMain = userStorage.getUserById(idMain);
        User userFriend = userStorage.getUserById(idFriend);

        userMain.getFriends().remove(userFriend);
        userFriend.getFriends().remove(userMain);

        return userFriend;
    }

    //вывод списка общих друзей
    public Set<User> getMutualFriends(int id1, int id2){
        Set<User> user1Friends = userStorage.getUserById(id1).getFriends();
        Set<User> user2Friends = userStorage.getUserById(id2).getFriends();
        
        return user1Friends.stream().filter(user2Friends::contains).collect(Collectors.toSet());
    }
}
