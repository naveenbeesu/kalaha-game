package com.bol.kalahagame.service;

import com.bol.kalahagame.exception.InvalidInputException;
import com.bol.kalahagame.model.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidationService {
    public void validateInputRequest(Step step) {
        if (step.pitIndex > 5 || step.pitIndex < 0) {
            log.error("PitIndex Should be between 0 to 5 but provided {}", step.pitIndex);
            throw new InvalidInputException("pitIndex", "Should be between 0 to 5");
        }
        if (!(step.player.equals("player1") || step.player.equals("player2"))) {
            log.error("player Should be player1 or player2 but provided {}", step.player);
            throw new InvalidInputException("player", "Should be player1 or player2");
        }
    }
}
