package com.bol.kalahagame.service;

import com.bol.kalahagame.exception.InvalidInputException;
import com.bol.kalahagame.model.Step;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ValidationServiceTest {
    @InjectMocks
    private ValidationService validationService;

    @Test
    void testValidateInputRequest() {
        Step step = new Step("1", 3, "player1");
        validationService.validateInputRequest(step);
    }

    @Test
    void testValidateInputRequestWithInvalidIndex() {
        Step step = new Step("1", 6, "player1");
        assertThrows(InvalidInputException.class, () -> validationService.validateInputRequest(step));
    }

    @Test
    void testValidateInputRequestWithInvalidPlayer() {
        Step step = new Step("1", 6, "player");
        assertThrows(InvalidInputException.class, () -> validationService.validateInputRequest(step));
    }
}
