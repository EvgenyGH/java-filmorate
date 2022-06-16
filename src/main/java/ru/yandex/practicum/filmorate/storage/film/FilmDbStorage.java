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
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Repository
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private static final String sqlAddFilm = "INSERT INTO films (name, description, release_date, duration" +
            ", mpa_id, rate) " +
            "VALUES ( ?, ?, ?, ?, ?, ?)";

    private static final String sqlInsertGenres = "INSERT INTO film_genres (film_id, genre_id) " +
            "VALUES (?, ?)";

    private static final String sqlGetGenresList = "SELECT film_id, genre_name, genres.genre_id FROM film_genres " +
            "LEFT JOIN genres ON film_genres.genre_id = genres.genre_id " +
            "WHERE film_id=? " +
            "ORDER BY genre_id";

    private static final String sqlUpdateFilm = "UPDATE films " +
            "SET name=?, " +
            "description=?, " +
            "release_date=?," +
            "duration=?, " +
            "mpa_id=?, " +
            "rate=?" +
            "WHERE film_id=?";

    private static final String sqlDeleteGenres = "DELETE FROM film_genres WHERE film_id=?";

    private static final String sqlGetFilms = "SELECT film_id, name, description, release_date, duration" +
            ", films.mpa_id, mpa_name, rate " +
            "FROM films " +
            "LEFT JOIN mpa ON films.mpa_id = mpa.mpa_id";

    private static final String sqlGetFilmById = "SELECT film_id, name, description, release_date" +
            ", duration, films.mpa_id, mpa_name, rate " +
            "FROM films " +
            "LEFT JOIN mpa ON films.mpa_id = mpa.mpa_id " +
            "WHERE film_id = ?";

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlAddFilm, new String[]{"film_id"});
                ps.setString(1, film.getName());
                ps.setString(2, film.getDescription());
                ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                ps.setInt(4, film.getDuration());
                ps.setInt(5, film.getMpa().getId());
                ps.setLong(6, film.getRate());
                return ps;
            }, keyHolder);

            film.setId(keyHolder.getKey().longValue());

            insertGenres(film);

        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при добавлении фильма в БД."
                    , Map.of("object", "film", "id", String.valueOf(film.getId())));
        }

        log.trace("Добавлен фильм -> Film: {}", film);

        return film;
    }

    private void insertGenres(Film film) {
        if (film.getGenres() == null) {
            return;
        }

        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlInsertGenres, film.getId(), genre.getId());
        }
    }

    private void updateFilmsWithGenres(Film film) {

        Set<Genre> genres = new HashSet<>(jdbcTemplate.query(sqlGetGenresList, this::makeGenresList, film.getId()));

        if (genres.size() > 0) {
            film.setGenres(genres);
        }
    }

    @Override
    public Film updateFilm(Film film) {

        try {
            if (jdbcTemplate.update(sqlUpdateFilm, film.getName(), film.getDescription(), film.getReleaseDate()
                    , film.getDuration(), film.getMpa().getId(), film.getRate()
                    , film.getId()) != 1) {
                throw new FilmNotExistsException(String.format("Фильма id=%s не существует", film.getId())
                        , Map.of("object", "film", "id", String.valueOf(film.getId())));
            }

            jdbcTemplate.update(sqlDeleteGenres, film.getId());
            insertGenres(film);
            updateFilmsWithGenres(film);

        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при обновлении фильма в БД."
                    , Map.of("object", "film", "id", String.valueOf(film.getId())));
        }

        log.trace("Обновлен фильм -> Film: {}", film);

        return film;
    }

    @Override
    public Collection<Film> getFilms() {

        Collection<Film> films;

        try {
            films = jdbcTemplate.query(sqlGetFilms, this::makeFilm);
        } catch (DataAccessException exception) {
            throw new SqlExceptionFilmorate("Ошибка при выгрузке всех фильмов из БД."
                    , Map.of("object", "films", "id", "all"));
        }

        log.trace("Выгружены все Фильмы -> Всего {}", films.size());

        return films;
    }

    @Override
    public Film getFilmById(long id) {

        Film film;

        try {
            film = jdbcTemplate.queryForObject(sqlGetFilmById, this::makeFilm, id);
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
        film.setMpa(new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")));
        film.setDuration(rs.getInt("duration"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setRate(rs.getLong("rate"));

        updateFilmsWithGenres(film);

        return film;
    }

    private Genre makeGenresList(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("genre_id")
                , rs.getString("genre_name"));
    }
}
