package ru.yandex.practicum.filmorate.model;

public class ErrorResponse {
    private final String error;

    private final String object;

    public ErrorResponse(String error) {
        this.error = error;
        this.object = "-";
    }

    public ErrorResponse(String error, String object) {
        this.error = error;
        this.object = object;
    }

    public String getError() {
        return error;
    }

    public String getObject() {
        return object;
    }
}
