package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotExistsException;
import ru.yandex.practicum.filmorate.exception.SqlExceptionFilmorate;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Repository
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {

        String sqlAddFilm = "INSERT INTO films (name, description, release_date, duration, mpa_id)\n" +
                "VALUES ( ?, ?, ?, ?, " +
                "SELECT mpa_id FROM mpa WHERE mpa.mpa_name=?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlAddFilm, new String[]{"film_id"});
                ps.setString(1, film.getName());
                ps.setString(2, film.getDescription());
                ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                ps.setInt(4, film.getDuration());
                ps.setString(5, film.getMpa());
                return ps;
            }, keyHolder);

            film.setId(keyHolder.getKey().longValue());

            insertLikesAndGenres(film);

        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при добавлении фильма в БД."
                    , Map.of("object", "film", "id", String.valueOf(film.getId())));
        }

        log.trace("Добавлен фильм -> Film: {}", film);

        return film;
    }

    private void insertLikesAndGenres(Film film) {
        String sqlInsertLikes = "INSERT INTO film_likes(film_id, user_liked_id) VALUES(?, ?)";

        for (Long id : film.getFilmLikes()) {
            jdbcTemplate.update(sqlInsertLikes, film.getId(), id);
        }

        String sqlInsertGenres = "INSERT INTO film_genres (film_id, genre_id) \n" +
                "VALUES (?, SELECT genre_id FROM genres WHERE genre_name = ?)";

        for (String genre : film.getGenresList()) {
            jdbcTemplate.update(sqlInsertGenres, film.getId(), genre);
        }
    }

    @Override
    public Film updateFilm(Film film) {

        String sqlUpdateFilm = "UPDATE films " +
                "SET name=?, " +
                "description=?, " +
                "release_date=?," +
                "duration=?, " +
                "mpa_id=(SELECT mpa_id FROM mpa WHERE mpa.mpa_name=?)" +
                "WHERE film_id=?";

        try {
            if (jdbcTemplate.update(sqlUpdateFilm, film.getName(), film.getDescription(), film.getReleaseDate()
                    , film.getDuration(), film.getMpa(), film.getId()) != 1) {
                throw new FilmNotExistsException(String.format("Фильма id=%s не существует", film.getId())
                        , Map.of("object", "film", "id", String.valueOf(film.getId())));
            }

            String sqlDeleteLikes = "DELETE FROM film_likes WHERE film_id=?";
            jdbcTemplate.update(sqlDeleteLikes, film.getId());

            String sqlDeleteGenres = "DELETE FROM film_genres WHERE film_id=?";
            jdbcTemplate.update(sqlDeleteGenres, film.getId());

            insertLikesAndGenres(film);

        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при обновлении фильма в БД."
                    , Map.of("object", "film", "id", String.valueOf(film.getId())));
        }

        log.trace("Обновлен фильм -> Film: {}", film);

        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "SELECT film_id, name, description, release_date, duration, mpa_name\n" +
                "FROM films\n" +
                "LEFT JOIN mpa ON films.mpa_id = mpa.mpa_id";

        Collection<Film> films;

        try {
            films = jdbcTemplate.query(sql, this::makeFilm);
        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при выгрузке всех фильмов из БД."
                    , Map.of("object", "films", "id", "all"));
        }

        log.trace("Выгружены все Фильмы -> Всего {}", films.size());

        return films;
    }

    @Override
    public Film getFilmById(long id) {
        String sql = "SELECT film_id, name, description, release_date, duration, mpa_name\n" +
                "FROM films\n" +
                "LEFT JOIN mpa ON films.mpa_id = mpa.mpa_id\n" +
                "WHERE film_id = ?";

        Film film;

        try {
            film = jdbcTemplate.queryForObject(sql, this::makeFilm, id);
        } catch (DataAccessException exception) {
            throw new FilmNotExistsException(String.format("Фильма id=%s не существует.", id)
                    , Map.of("object", "film", "id", String.valueOf(id)));
        }

        log.trace("Выгружен Фильм id={} -> {}", id, film);

        return film;
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("film_id"));
        film.setName(rs.getString("name"));
        film.setMpa(rs.getString("mpa_name"));
        film.setDuration(rs.getInt("duration"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());

        String sqlFilmLikes = "SELECT user_liked_id " +
                "FROM film_likes " +
                "WHERE film_id=?";
        film.setFilmLikes(new HashSet<>(jdbcTemplate.query(sqlFilmLikes, this::makeFilmLikes, film.getId())));

        String sqlGenresList = "SELECT film_id, genre_name FROM film_genres\n" +
                "LEFT JOIN genres ON film_genres.genre_id = genres.genre_id\n" +
                "WHERE film_id=?";
        film.setGenresList(new HashSet<>
                (jdbcTemplate.query(sqlGenresList, this::makeGenresList, film.getId())));

        return film;
    }

    private Long makeFilmLikes(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong("user_liked_id");
    }

    private String makeGenresList(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("genre_name");
    }
}
