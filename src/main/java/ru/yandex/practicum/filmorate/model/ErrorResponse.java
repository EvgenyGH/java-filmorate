package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    @Getter
    private final String dateTime;
    @Getter
    private final String error;
    @Getter
    private final String object;
    @Getter
    private final String id;

    public ErrorResponse(String error) {
        this.error = error;
        this.object = "-";
        this.dateTime = LocalDateTime.now().toString();
        this.id = "-";
    }

    public ErrorResponse(String error, Map<String, String> properties) {
        this.error = error;
        this.object = properties.get("object");
        this.dateTime = LocalDateTime.now().toString();
        this.id = properties.get("id");
    }
}
