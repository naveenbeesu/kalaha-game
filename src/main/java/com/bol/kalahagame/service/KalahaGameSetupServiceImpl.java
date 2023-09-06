package com.bol.kalahagame.service;

import com.bol.kalahagame.entity.Game;
import com.bol.kalahagame.entity.Player;
import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.model.PlayerDto;
import com.bol.kalahagame.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class KalahaGameSetupServiceImpl implements KalahaGameSetupService {
    private final GameRepository gameRepository;

    public KalahaGameSetupServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    private static Game createGame(int[] player1Pits, String id) {
        Player player1 = new Player();
        player1.setStonesInPits(Arrays.toString(player1Pits));
        player1.setStonesInBigPit(Constants.BIG_PIT_INITIAL_SIZE);
        Player player2 = new Player();
        player2.setStonesInPits(Arrays.toString(player1Pits));
        player2.setStonesInBigPit(Constants.BIG_PIT_INITIAL_SIZE);
        Game game = new Game();
        game.setGameId(id);
        game.setWinnerOfGame(Constants.NO_WINNER);
        game.setPresentPlayer(Constants.PLAYER_ONE);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        return game;
    }

    /**
     * @param id game id
     * @return KalahaGame KalhaGame object
     */
    @Override
    public KalahaGame startGame(String id) {
        KalahaGame kalahaGame = new KalahaGame();
        //checks if there is any existing game for the given id
        Optional<Game> game = gameRepository.findByGameId(id);
        if (game.isPresent()) {
            Game existingGame = game.get();
            extractKalahaGame(existingGame, kalahaGame);
            log.info("loaded existing game with id {}", id);
            return kalahaGame;
        }
        //if no existing id, starts a new game
        return startNewGame();
    }

    private void extractKalahaGame(Game existingGame, KalahaGame kalahaGame) {
        Player player1 = existingGame.getPlayer1();
        PlayerDto player1Dto = new PlayerDto();
        String player1Stones = player1.getStonesInPits();
        player1Dto.setStonesInPits(getStonesArrayOfPlayer(player1Stones));
        player1Dto.setStonesInBigPit(player1.getStonesInBigPit());

        Player player2 = existingGame.getPlayer2();
        PlayerDto player2Dto = new PlayerDto();
        String player2Stones = player2.getStonesInPits();
        player2Dto.setStonesInPits(getStonesArrayOfPlayer(player2Stones));
        player2Dto.setStonesInBigPit(player2.getStonesInBigPit());

        kalahaGame.setId(existingGame.gameId);
        kalahaGame.setWinnerOfGame(existingGame.getWinnerOfGame());
        kalahaGame.setPresentPlayer(existingGame.getPresentPlayer());
        kalahaGame.setPlayer1(player1Dto);
        kalahaGame.setPlayer2(player2Dto);
    }

    private int[] getStonesArrayOfPlayer(String player1Stones) {
        JSONArray jsonArray = new JSONArray(player1Stones);
        // Convert the JSON array to a array of integers
        int[] stonesArray = new int[6];
        for (int i = 0; i < jsonArray.length(); i++) {
            stonesArray[i] = jsonArray.getInt(i);
        }
        return stonesArray;
    }

    private KalahaGame startNewGame() {

        String id = "KALAHA" + RandomStringUtils.random(8, false, true);

        int[] playerPits = new int[Constants.NUMBER_OF_PITS];
        //fills each pit of both players with six stones
        Arrays.fill(playerPits, Constants.NUMBER_OF_STARTING_STONES);
        Game game = createGame(playerPits, id);
        Game gameCreated = gameRepository.save(game);
        log.info("created new game with id {}", id);
        KalahaGame gameResponse = new KalahaGame();
        extractKalahaGame(gameCreated, gameResponse);
        return gameResponse;
    }
}
