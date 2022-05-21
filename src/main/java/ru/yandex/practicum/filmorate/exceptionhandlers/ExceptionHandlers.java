package ru.yandex.practicum.filmorate.exceptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
@Slf4j
public class ExceptionHandlers {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ValidationException.class
            , MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationExceptionHandler(Exception exception) {
        log.warn(String.format("%-40s - %s", "Выброшено исключение", exception.getMessage()));
        return new ErrorResponse("Ошибка валидации входящих данных.");
    }

    @ExceptionHandler(value = {UserNotExistsException.class, FilmNotExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundHandler(BaseFilmAndUserException exception) {
        log.warn(String.format("%-40s - %s", "Выброшено исключение", exception.getMessage()));
        return new ErrorResponse("Объект не найден.", exception.getProperties());
    }

    @ExceptionHandler(value = {UserExistsException.class, FilmExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse alreadyExistsHandler(BaseFilmAndUserException exception) {
        log.warn(String.format("%-40s - %s", "Выброшено исключение", exception.getMessage()));
        return new ErrorResponse("Объект уже существует.", exception.getProperties());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse otherExceptionHandler(Exception exception) {
        log.warn(String.format("%-40s - %s", "Выброшено исключение", exception.getMessage()));
        return new ErrorResponse("Неизвестная ошибка.");
    }

}


