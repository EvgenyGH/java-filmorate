package ru.yandex.practicum.filmorate.storage.mpa;

import java.util.Map;

public interface MpaStorage {
    Map<Integer, String> getAllMpa();
    Map<Integer, String> getMpaById(int id);
}
