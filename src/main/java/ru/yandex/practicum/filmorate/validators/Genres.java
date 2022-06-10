package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = GenresValidator.class)
@Documented
public @interface Genres {

    String message() default "{Genres.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class GenresValidator implements ConstraintValidator<Genres, Set<String>> {
    private static final HashSet<String> genresAllowed = new HashSet<>(Arrays.asList("Комедия", "Драма"
            , "Мультфильм", "Триллер", "Документальный", "Боевик"));

    @Override
    public boolean isValid(Set<String> genres, ConstraintValidatorContext context) {
        return !genres.isEmpty() && genresAllowed.containsAll(genres);
    }
}
