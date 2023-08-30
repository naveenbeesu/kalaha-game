package com.bol.kalahagame.service;

import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.model.Player;
import com.bol.kalahagame.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MoveStonesServiceImplTest {
    @InjectMocks
    private MoveStonesServiceImpl moveStonesService;
    @Mock
    private KalahaGameSetupServiceImpl kalahaGameSetupService;

    KalahaGame kalahaGame;
    int [] player1Pits;
    int [] player2Pits;

    @BeforeEach
    void setup(){
        player1Pits = new int [Constants.NUMBER_OF_PITS];
        player2Pits = new int [Constants.NUMBER_OF_PITS];

        Arrays.fill(player1Pits, Constants.NUMBER_OF_STARTING_STONES);
        Arrays.fill(player2Pits, Constants.NUMBER_OF_STARTING_STONES);
    }

    @Test
    void testMoveStonesForIndex3Player1() {
        Step step = new Step("1", 3, "player1");
        int[] expectedPlayer1Pits = new int[]{6, 6, 6, 0, 7, 7};
        int[] expectedPlayer2Pits = new int[]{7, 7, 7, 6, 6, 6};

        kalahaGame = new KalahaGame("sample-id", Constants.PLAYER_ONE, Constants.NO_WINNER, new Player(player1Pits, Constants.BIG_PIT_INITIAL_SIZE), new Player(player2Pits, Constants.BIG_PIT_INITIAL_SIZE));
        when(kalahaGameSetupService.startGame(any())).thenReturn(kalahaGame);

        KalahaGame actualResponse = moveStonesService.moveStones(step);
        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_TWO, actualResponse.presentPlayer);
        assertArrayEquals(expectedPlayer1Pits, actualResponse.player1.stonesInPits);
        assertArrayEquals(expectedPlayer2Pits, actualResponse.player2.stonesInPits);
        assertEquals(1, actualResponse.player1.stonesInBigPit);
        assertEquals(0, actualResponse.player2.stonesInBigPit);
    }

    @Test
    void testMoveStonesForIndex2Player2() {
        Step step = new Step("1", 2, "player2");
        int[] expectedPlayer1Pits = new int[]{7, 7, 6, 6, 6, 6};
        int[] expectedPlayer2Pits = new int[]{6, 6, 0, 7, 7, 7};

        kalahaGame = new KalahaGame("1", Constants.PLAYER_TWO, Constants.NO_WINNER, new Player(player1Pits, Constants.BIG_PIT_INITIAL_SIZE), new Player(player2Pits, Constants.BIG_PIT_INITIAL_SIZE));
        when(kalahaGameSetupService.startGame(any())).thenReturn(kalahaGame);

        KalahaGame actualResponse = moveStonesService.moveStones(step);
        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_ONE, actualResponse.presentPlayer);
        assertArrayEquals(expectedPlayer1Pits, actualResponse.player1.stonesInPits);
        assertArrayEquals(expectedPlayer2Pits, actualResponse.player2.stonesInPits);
        assertEquals(0, actualResponse.player1.stonesInBigPit);
        assertEquals(1, actualResponse.player2.stonesInBigPit);
    }
}
