package dev.stockman.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = ValidationServiceTest.TestConfig.class)
class ValidationServiceTest {

    @Configuration
    static class TestConfig {

        @Bean
        public Validator validator() {
            // Create a ValidatorFactory and return the Validator from it
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            return factory.getValidator();
        }

        @Bean
        public ValidationService validationService(Validator validator) {
            return new ValidationService(validator); // Create the required bean explicitly
        }
    }

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
