package com.bol.kalahagame.service;

import com.bol.kalahagame.entity.Game;
import com.bol.kalahagame.entity.Player;
import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class KalahaGameSetupServiceImplTest {
    @Mock
    GameRepository gameRepository;
    @InjectMocks
    private KalahaGameSetupServiceImpl kalahaGameSetupService;

    private static Game getGame(int[] expectedPlayer1Pits, int[] expectedPlayer2Pits) {
        String id = "KALAHA12345678";
        Player player1 = new Player();
        player1.setStonesInPits(Arrays.toString(expectedPlayer1Pits));
        player1.setStonesInBigPit(1);
        Player player2 = new Player();
        player2.setStonesInPits(Arrays.toString(expectedPlayer2Pits));
        player2.setStonesInBigPit(0);
        Game game = new Game();
        game.setGameId(id);
        game.setWinnerOfGame(Constants.NO_WINNER);
        game.setPresentPlayer(Constants.PLAYER_TWO);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        return game;
    }

    @Test
    void testStartGameForNewId() {
        int[] playerPits = new int[Constants.NUMBER_OF_PITS];
        //fills each pit of both players with six stones
        Arrays.fill(playerPits, Constants.NUMBER_OF_STARTING_STONES);
        Game game = getGame(playerPits, playerPits);
        game.setPresentPlayer(Constants.PLAYER_ONE);
        when(gameRepository.findByGameId(anyString())).thenReturn(Optional.of(game));
        KalahaGame actualResponse = kalahaGameSetupService.startGame("KALAHA12345678");
        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_ONE, actualResponse.presentPlayer);
    }

    @Test
    void testStartGameForExistingId() {
        int[] expectedPlayer1Pits = new int[]{6, 6, 6, 0, 7, 7};
        int[] expectedPlayer2Pits = new int[]{7, 7, 7, 6, 6, 6};
        Game game = getGame(expectedPlayer1Pits, expectedPlayer2Pits);
        when(gameRepository.findByGameId(anyString())).thenReturn(Optional.of(game));
        KalahaGame actualResponse = kalahaGameSetupService.startGame("KALAHA12345678");
        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_TWO, actualResponse.presentPlayer);
        assertArrayEquals(expectedPlayer1Pits, actualResponse.player1.stonesInPits);
        assertArrayEquals(expectedPlayer2Pits, actualResponse.player2.stonesInPits);
        assertEquals(1, actualResponse.player1.stonesInBigPit);
        assertEquals(0, actualResponse.player2.stonesInBigPit);
    }
}
