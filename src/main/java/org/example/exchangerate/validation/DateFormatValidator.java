package org.example.exchangerate.validation;

import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.validation.ConstraintValidator;
import java.time.format.DateTimeParseException;
import org.example.exchangerate.exception.InvalidDateFormatException;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    private String pattern;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
            return true;
        }
         catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Please use correct date format: 'yyyy-MM-dd'");
        }
    }
}
