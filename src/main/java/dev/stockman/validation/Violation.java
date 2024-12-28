package dev.stockman.validation;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;

import java.util.Optional;

public record Violation(String property, Object invalidValue, String message) {
    public Violation(ConstraintViolation<Object> constraintViolation) {
        this(
                Optional.ofNullable(constraintViolation.getPropertyPath()).map(path -> path.toString()).orElse(null),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage()
        );
    }
    public Violation(FieldError fieldError) {
        this(
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
        );
    }
}
