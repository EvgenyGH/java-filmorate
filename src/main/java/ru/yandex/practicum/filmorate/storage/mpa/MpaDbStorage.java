package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.GenreNotExistException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@Primary
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM mpa";
        List<Mpa> mpaList = jdbcTemplate.query(sql, this::makeMpa);

        log.trace("Все MPA выгружены -> всего: {}", mpaList.size());

        return mpaList;
    }

    @Override
    public Mpa getMpaById(int id) {
        String sql = "SELECT * FROM mpa WHERE mpa_id=?";
        Mpa mpa;
        try {
            mpa = jdbcTemplate.queryForObject(sql, this::makeMpa, id);
        } catch (DataAccessException exception) {
            throw new GenreNotExistException(String.format("MPA id=%s не существует.", id)
                    , Map.of("object", "mpa", "id", String.valueOf(id)));
        }

        log.trace("MPA id={} выгружен -> MPA: {}", id, mpa);

        return mpa;
    }

    private Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name"));
    }
}
