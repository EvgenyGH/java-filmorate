package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    //Хранилище пользователей
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    //создание нового пользователя
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userStorage.addUser(user);
    }

    //обновление информации о пользователе
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userStorage.updateUser(user);
    }

    //получение списка всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    //получить пользователя по id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return userStorage.getUserById(id);
    }

    //добавление в друзья
    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") int userId, @PathVariable int friendId){
        return userService.addFriend(userId, friendId);
    }

    //удаление из друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable("id") int userId, @PathVariable int friendId){
        return userService.removeFriend(userId, friendId);
    }

    //возвращает список пользователей, являющихся его друзьями
    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable int id){
        return userStorage.getUserById(id).getFriends();
    }

    //список друзей, общих с другим пользователем
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable("id") int user1Id, @PathVariable("otherId") int user2Id){
        return userService.getMutualFriends(user1Id, user2Id);
    }
}