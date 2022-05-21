package ru.yandex.practicum.filmorate.model;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private final String dateTime;
    private final String error;
    private final String object;
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

    public String getError() {
        return error;
    }

    public String getObject() {
        return object;
    }

    public String getDateTime() {
        return dateTime;
    }
}
