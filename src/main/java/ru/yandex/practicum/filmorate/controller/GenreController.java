package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Map<Integer, String> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Map<Integer, String> getGenreById(@PathVariable int id) {
        return  genreService.getGenreById(id);
    }
}
