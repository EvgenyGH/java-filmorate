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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MpaValidator.class)
@Documented
public @interface Mpa {

    String message() default "{Mpa.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class MpaValidator implements ConstraintValidator<Mpa, String> {
    private static final HashSet<String> mpaAllowed = new HashSet<>(Arrays.asList("G", "PG", "PG-13", "R", "NC-17"));

    @Override
    public boolean isValid(String mpa, ConstraintValidatorContext context) {
        return mpaAllowed.contains(mpa);
    }
}
