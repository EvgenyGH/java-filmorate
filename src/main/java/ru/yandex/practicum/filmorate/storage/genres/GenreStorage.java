package ru.yandex.practicum.filmorate.storage.genres;

import java.util.Map;

public interface GenreStorage {
    Map<Integer, String> getAllGenres();

    Map<Integer, String> getGenreById(int id);
}
