package dev.stockman.validation;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    @Test
    void testValidObject() {
        record TestObject(@NotNull String name) {}
        Assertions.assertDoesNotThrow(() -> validationService.validate(new TestObject("test")));
    }

    @Test
    void testInvalidObject() {
        record TestObject(@NotNull String name) {}
        InvalidInputException ex = Assertions.assertThrows(InvalidInputException.class, () -> validationService.validate(new TestObject(null)));
        Assertions.assertNotNull(ex.getViolations());
        Assertions.assertEquals(1, ex.getViolations().size());
        Assertions.assertEquals(ex.getViolations().toArray()[0], new Violation("name", null, "must not be null"));
    }

}
