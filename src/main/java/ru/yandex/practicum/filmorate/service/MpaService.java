package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Map;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Map<Integer, String> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Map<Integer, String> getMpaById(@PathVariable int id) {
        return mpaStorage.getMpaById(id);
    }
}
