package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;

import java.util.Map;

@Service
@Slf4j
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Map<Integer, String> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Map<Integer, String> getGenreById(int id) {
        return genreStorage.getGenreById(id);
    }
}
