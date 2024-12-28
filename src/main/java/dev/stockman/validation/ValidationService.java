package dev.stockman.validation;

import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public void validate(Object object) throws InvalidInputException {
        var violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw InvalidInputException.constraintViolations(violations);
        }
    }
}

