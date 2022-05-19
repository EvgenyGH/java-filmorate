package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    //добавление в друзья
    public User addFriend(User userMain, User userFriend){
        return userFriend;
    }

    //удаление из друзей
    public User removeFriend(User userMain, User userFriend){
        return userMain;
    }

    //вывод списка общих друзей
    public List<User> getMutualFriends(User user1, User user2){
        return new ArrayList();
    }
}
