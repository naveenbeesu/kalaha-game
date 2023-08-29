package com.bol.kalahagame.service;

import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.model.Player;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@Service
public class KalahaGameSetupServiceImpl implements KalahaGameSetupService {

    private final HashMap<String, KalahaGame> listOfGames = new HashMap<>();
    /**
     * @param id game id
     * @return KalahaGame KalhaGame object
     */
    @Override
    public KalahaGame startGame(String id) {
        //checks if there is any existing game for the given id
        if(listOfGames.containsKey(id)){
            return listOfGames.get(id);
        }
        //if no existing id, starts a new game
        return startNewGame();
    }

    private KalahaGame startNewGame() {
        String id = UUID.randomUUID().toString();
        listOfGames.put(id, setupNewGameDetails(id));
        return listOfGames.get(id);
    }

    private KalahaGame setupNewGameDetails(String id) {

        int [] player1Pits = new int [Constants.NUMBER_OF_PITS];
        int [] player2Pits = new int [Constants.NUMBER_OF_PITS];

        Arrays.fill(player1Pits, Constants.NUMBER_OF_STARTING_STONES);
        Arrays.fill(player2Pits, Constants.NUMBER_OF_STARTING_STONES);

        return new KalahaGame(id, Constants.PLAYER_ONE, Constants.NO_WINNER, new Player(player1Pits, Constants.BIG_PIT_INITIAL_SIZE), new Player(player2Pits, Constants.BIG_PIT_INITIAL_SIZE));
    }
}
