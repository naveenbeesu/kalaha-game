package com.bol.kalahagame.service;

import com.bol.kalahagame.exception.InvalidInputException;
import com.bol.kalahagame.model.Step;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateInputRequest(Step step) {
        if (step.pitIndex > 5 || step.pitIndex < 0) {
            throw new InvalidInputException("pitIndex", "Should be between 0 to 5");
        }
        if (!(step.player.equals("player1") || step.player.equals("player2"))) {
            throw new InvalidInputException("player", "Should be player1 or player2");
        }
    }
}
