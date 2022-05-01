package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {
    @Test
    void contextLoads() {
    }
}
