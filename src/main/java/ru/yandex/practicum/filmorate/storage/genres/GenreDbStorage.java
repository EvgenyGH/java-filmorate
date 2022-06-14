package ru.yandex.practicum.filmorate.storage.genres;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.GenreNotExistException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Primary
@Slf4j
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres";
        List<Genre> genres = jdbcTemplate.query(sql, this::makeGenreMap);

        log.trace("Все жанры выгружены -> всего: {}", genres.size());

        return genres;
    }

    @Override
    public Genre getGenreById(int id) {
        String sql = "SELECT * FROM genres WHERE genre_id=?";
        Genre genre;

        try {
            genre = jdbcTemplate.queryForObject(sql, this::makeGenreMap, id);
        } catch (DataAccessException exception) {
            throw new GenreNotExistException(String.format("Жанр id=%s не существует.", id)
                    , Map.of("object", "genre", "id", String.valueOf(id)));
        }

        log.trace("Жанр id={} выгружен -> Genre: {}", id, genre);

        return genre;
    }

    private Genre makeGenreMap(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }
}
