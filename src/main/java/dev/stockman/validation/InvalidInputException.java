package dev.stockman.validation;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class InvalidInputException extends IllegalArgumentException {
    private final Collection<Violation> violations;
    private InvalidInputException(Collection<Violation> violations) {
        super();
        this.violations = new HashSet<>(violations);
    }
    static InvalidInputException fieldErrors(Collection<FieldError> fieldErrors) {
        return new InvalidInputException(fieldErrors.stream()
                .map(fieldError -> new Violation(fieldError))
                .collect(Collectors.toSet()));
    }
    static InvalidInputException constraintViolations(Collection<ConstraintViolation<Object>> constraintViolations) {
        return new InvalidInputException(constraintViolations.stream()
                .map(constraintViolation -> new Violation(constraintViolation))
                .collect(Collectors.toSet()));
    }
    public Collection<Violation> getViolations() {
        return new HashSet<>(this.violations);
    }
}