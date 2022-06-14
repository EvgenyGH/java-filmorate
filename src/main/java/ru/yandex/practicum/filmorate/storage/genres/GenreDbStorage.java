package ru.yandex.practicum.filmorate.storage.genres;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
@Primary
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, String> getAllGenres() {
        String sql = "SELECT * FROM genres";
        Map<Integer, String> genres = new HashMap<>();

        jdbcTemplate.query(sql, this::makeGenreMap).forEach(genres::putAll);

        return genres;
    }

    @Override
    public Map<Integer, String> getGenreById(int id) {
        String sql = "SELECT * FROM genres WHERE genre_id=?";

        return jdbcTemplate.queryForObject(sql, this::makeGenreMap, id);
    }

    private Map<Integer, String> makeGenreMap(ResultSet rs, int rowNum) throws SQLException {
        return Map.of(rs.getInt("genre_id"), rs.getString("genre_name"));
    }
}
