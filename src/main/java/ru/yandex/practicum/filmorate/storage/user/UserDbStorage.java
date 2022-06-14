package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.SqlExceptionFilmorate;
import ru.yandex.practicum.filmorate.exception.UserNotExistsException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        user.validateName();

        String sqlAddUser = "INSERT INTO users (email, login, name, birthday) VALUES ( ?, ?, ?, ? )";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlAddUser, new String[]{"user_id"});
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getName());
                ps.setDate(4, Date.valueOf(user.getBirthday()));
                return ps;
            }, keyHolder);

            user.setId(keyHolder.getKey().longValue());

            insertUserFriends(user);

        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при добавлении пользователя в БД."
                    , Map.of("object", "user", "id", String.valueOf(user.getId())));
        }

        log.trace("Добавлен пользователь -> User: {}", user);

        return user;
    }

    private void insertUserFriends(User user) {
        String sqlInsertUserFriends = "INSERT INTO user_friends(user_id, friend_id) VALUES(?, ?)";

        for (Long id : user.getFriends().keySet()) {
            jdbcTemplate.update(sqlInsertUserFriends, user.getId(), id);
        }
    }

    @Override
    public User updateUser(User user) {
        user.validateName();

        String sqlUpdateFilm = "UPDATE users " +
                "SET email=?, " +
                "login=?, " +
                "name=?," +
                "birthday=? " +
                "WHERE user_id=?";

        try {
            if (jdbcTemplate.update(sqlUpdateFilm, user.getEmail(), user.getLogin()
                    , user.getName(), user.getBirthday(), user.getId()) != 1) {
                throw new FilmNotExistsException(String.format("Пользователя id=%s не существует"
                        , user.getId())
                        , Map.of("object", "film", "id", String.valueOf(user.getId())));
            }

            String sqlDeleteUserFriends = "DELETE FROM user_friends WHERE user_id=?";
            jdbcTemplate.update(sqlDeleteUserFriends, user.getId());

            insertUserFriends(user);

        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при обновлении пользователя в БД."
                    , Map.of("object", "user", "id", String.valueOf(user.getId())));
        }

        log.trace("Обновлен пользователь -> User: {}", user);

        return user;
    }

    @Override
    public Collection<User> getUsers() {
        String sql = "SELECT * FROM users";

        Collection<User> users;

        try {
            users = jdbcTemplate.query(sql, this::makeUser);
        } catch (DataAccessException exception) {
            throw new UserNotExistsException("Ошибка при выгрузке " +
                    "всех пользователей из БД."
                    , Map.of("object", "users", "id", "all"));
        }

        log.trace("Выгружены все Пользователи -> Всего {}", users.size());

        return users;
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT * FROM users WHERE user_id=?";

        User user;

        try {
            user = jdbcTemplate.queryForObject(sql, this::makeUser, id);
        } catch (DataAccessException exception) {
            throw new UserNotExistsException(String.format("Пользователя id=%s не существует.", id)
                    , Map.of("object", "user", "id", String.valueOf(id)));
        }

        log.trace("Выгружен Пользователь id={} -> {}", id, user);

        return user;
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());

        String sqlUserFriends = "SELECT friend_id,\n" +
                "       CASE\n" +
                "           WHEN user_id IN (SELECT friend_id FROM user_friends WHERE user_id=uf.friend_id)\n" +
                "           THEN 'подтверждённая'\n" +
                "           ELSE  'неподтверждённая'\n" +
                "           END AS status\n" +
                "FROM user_friends AS uf\n" +
                "WHERE user_id = ?";

        HashMap<Long, String> userFriends = new HashMap<>();

        jdbcTemplate.query(sqlUserFriends, this::makeUserFriends, user.getId())
                .forEach(userFriends::putAll);

        user.setFriends(userFriends);

        return user;
    }

    private Map<Long, String> makeUserFriends(ResultSet rs, int rowNum) throws SQLException {
        return Map.of(rs.getLong("friend_id"), rs.getString("status"));
    }
}