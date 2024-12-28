package dev.stockman.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InvalidInputExceptionTest {

    /**
     * Test class to validate the behavior of the `fieldErrors` method in the InvalidInputException class.
     * The `fieldErrors` method creates an InvalidInputException instance from a collection of FieldError
     * objects, which are then converted into Violation instances stored within the exception.
     */

    @Test
    void fieldErrors_withEmptyFieldErrorsCollection_returnsEmptyViolations() {
        // Arrange
        Collection<FieldError> fieldErrors = List.of();

        // Act
        InvalidInputException exception = InvalidInputException.fieldErrors(fieldErrors);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(0, exception.getViolations().size(), "Expected no violations");
    }

    @Test
    void fieldErrors_withSingleFieldError_createsSingleViolation() {
        // Arrange
        FieldError fieldError = new FieldError("objectName", "fieldName", "Default error message");
        Collection<FieldError> fieldErrors = List.of(fieldError);

        // Act
        InvalidInputException exception = InvalidInputException.fieldErrors(fieldErrors);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(1, exception.getViolations().size(), "Expected one violation");
        assertEquals("fieldName", exception.getViolations().iterator().next().property(), "Field name does not match");
    }

    @Test
    void fieldErrors_withMultipleFieldErrors_createsCorrectNumberOfViolations() {
        // Arrange
        FieldError fieldError1 = new FieldError("objectName1", "fieldName1", "First error message");
        FieldError fieldError2 = new FieldError("objectName2", "fieldName2", "Second error message");
        Collection<FieldError> fieldErrors = List.of(fieldError1, fieldError2);

        // Act
        InvalidInputException exception = InvalidInputException.fieldErrors(fieldErrors);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(2, exception.getViolations().size(), "Expected two violations");
    }

    @Test
    void constraintViolations_withEmptyConstraintViolationsCollection_returnsEmptyViolations() {
        // Arrange
        Collection<ConstraintViolation<Object>> constraintViolations = List.of();

        // Act
        InvalidInputException exception = InvalidInputException.constraintViolations(constraintViolations);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(0, exception.getViolations().size(), "Expected no violations");
    }

    @Test
    void constraintViolations_withSingleConstraintViolation_createsSingleViolation() {
        // Arrange
        Path path = Mockito.mock(Path.class);
        Mockito.when(path.toString()).thenReturn("Test constraint message");
        ConstraintViolation<Object> constraintViolation = Mockito.mock(ConstraintViolation.class);
        Mockito.when(constraintViolation.getPropertyPath()).thenReturn(path);
        Mockito.when(constraintViolation.getInvalidValue()).thenReturn("invalidValue");
        Mockito.when(constraintViolation.getMessage()).thenReturn("Validation error message");
        Collection<ConstraintViolation<Object>> constraintViolations = List.of(constraintViolation);

        // Act
        InvalidInputException exception = InvalidInputException.constraintViolations(constraintViolations);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(1, exception.getViolations().size(), "Expected one violation");
        assertEquals("Test constraint message", exception.getViolations().iterator().next().property(), "Constraint message does not match");
    }

    @Test
    void constraintViolations_withMultipleConstraintViolations_createsCorrectNumberOfViolations() {
        // Arrange
        Path path1 = Mockito.mock(Path.class);
        Mockito.when(path1.toString()).thenReturn("Test constraint message1");
        Path path2 = Mockito.mock(Path.class);
        Mockito.when(path2.toString()).thenReturn("Test constraint message2");
        ConstraintViolation<Object> constraintViolation1 = Mockito.mock(ConstraintViolation.class);
        Mockito.when(constraintViolation1.getPropertyPath()).thenReturn(path1);
        Mockito.when(constraintViolation1.getInvalidValue()).thenReturn("invalidValue1");
        Mockito.when(constraintViolation1.getMessage()).thenReturn("Validation error message1");
        ConstraintViolation<Object> constraintViolation2 = Mockito.mock(ConstraintViolation.class);
        Mockito.when(constraintViolation2.getPropertyPath()).thenReturn(path2);
        Mockito.when(constraintViolation2.getInvalidValue()).thenReturn("invalidValue2");
        Mockito.when(constraintViolation2.getMessage()).thenReturn("Validation error message2");
        Collection<ConstraintViolation<Object>> constraintViolations = List.of(constraintViolation1, constraintViolation2);

        // Act
        InvalidInputException exception = InvalidInputException.constraintViolations(constraintViolations);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(2, exception.getViolations().size(), "Expected two violations");
    }

    @Test
    void violations_withEmptyViolationsCollection_returnsEmptyViolations() {
        // Arrange
        Collection<Violation> violations = List.of();

        // Act
        InvalidInputException exception = InvalidInputException.violations(violations);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(0, exception.getViolations().size(), "Expected no violations");
    }

    @Test
    void violations_withSingleViolation_createsSingleViolation() {
        // Arrange
        Violation violation = new Violation("fieldName", "invalidValue", "Message");
        Collection<Violation> violations = List.of(violation);

        // Act
        InvalidInputException exception = InvalidInputException.violations(violations);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(1, exception.getViolations().size(), "Expected one violation");
        assertEquals("fieldName", exception.getViolations().iterator().next().property(), "Field name does not match");
    }

    @Test
    void violations_withMultipleViolations_createsCorrectNumberOfViolations() {
        // Arrange
        Violation violation1 = new Violation("fieldName1", "invalidValue1", "Message1");
        Violation violation2 = new Violation("fieldName2", "invalidValue2", "Message2");
        Collection<Violation> violations = List.of(violation1, violation2);

        // Act
        InvalidInputException exception = InvalidInputException.violations(violations);

        // Assert
        assertNotNull(exception);
        assertNotNull(exception.getViolations());
        assertEquals(2, exception.getViolations().size(), "Expected two violations");
    }
}