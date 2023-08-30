package com.bol.kalahagame.service;

import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KalahaGameSetupServiceImplTest {
    @InjectMocks
    private KalahaGameSetupServiceImpl kalahaGameSetupService;

    @Test
    void testStartGameForNewId() {
        KalahaGame actualResponse = kalahaGameSetupService.startGame("sample-id");
        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_ONE, actualResponse.presentPlayer);
    }
}
