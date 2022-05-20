package ru.yandex.practicum.filmorate.model;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String dateTime;
    private final String error;
    private final String object;

    public ErrorResponse(String error) {
        this.error = error;
        this.object = "-";
        this.dateTime = LocalDateTime.now().toString();
    }

    public ErrorResponse(String error, String object) {
        this.error = error;
        this.object = object;
        this.dateTime = LocalDateTime.now().toString();
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
